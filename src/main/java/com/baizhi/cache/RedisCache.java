package com.baizhi.cache;

import com.baizhi.annotation.AddCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Set;

@Configuration
@Aspect
public class RedisCache {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //添加缓存
    //@Around("execution(* com.baizhi.service.*.query*(..) )")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        System.out.println("==环绕通知==");

        /*序列化解决乱码*/
        StringRedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);

        //获取类上的注解
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        //获取方法
        Method method = signature.getMethod();

        //判断该类上是否有AddCache注解
        boolean annotationPresent = method.isAnnotationPresent(AddCache.class);
        //判断
        if(annotationPresent){
            //有添加缓存的注解

            //获取可变长字符串
            StringBuilder sb = new StringBuilder();

            //获取类的全限定名   包名.类名
            String clazzName = proceedingJoinPoint.getTarget().getClass().getName();
            sb.append(clazzName);

            //获取方法名
            String methodName = proceedingJoinPoint.getSignature().getName();
            sb.append(methodName);

            //获取实参
            Object[] args = proceedingJoinPoint.getArgs();
            for (Object arg : args) {
                sb.append(arg);
            }

            //设置一个 key
            //拼接好的key   类的全限定名+方法名+实参
            String key = sb.toString();

            //操作String 类型
            ValueOperations valueOperations = redisTemplate.opsForValue();

            //判断key在缓存中是否存在
            Boolean aBoolean = redisTemplate.hasKey(key);

            //声明结果
            Object result =null;

            //判断
            if(aBoolean){
                //存在  从缓存中取数据  并返回结果
                result = valueOperations.get(key);
            }else{

                result = proceedingJoinPoint.proceed();

                //不存在   将数据加入缓存 并返回结果
                valueOperations.set(key,result);
            }
            return result;
        }else{
            //没有添加缓存的注解
            Object result = proceedingJoinPoint.proceed();
            return result;
        }
    }
    
    //增删改清除缓存
    //@After("execution(* com.baizhi.service.*.*(..)) && !execution(* com.baizhi.service.*.query*(..))")
    public void after(JoinPoint joinPoint){

        System.out.println("===清除缓存===");

        //清空切到的这个类中所有的缓存
        String clazzName = joinPoint.getTarget().getClass().getName();

        //获取所有的Key
        Set<String> keys = stringRedisTemplate.keys("*");
        //遍历key
        for (String key : keys) {

            //判断是否是以该类的全限定名为前缀key
            boolean flag = key.startsWith(clazzName);

            //判断
            if(flag){
                //删除缓存
                stringRedisTemplate.delete(key);
            }
        }

    }
    
}
