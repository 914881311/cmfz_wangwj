package com.baizhi;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 *
 * @author Administrator
 */
public class Send {

    //发送短信验证码   参数：发送短信需要的参数,发送短信调用的url
    public static String SMS(String postData, String postUrl) {
        try {
            //发送POST请求
            URL url = new URL(postUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return "";
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return "";
    }


    //生成随机数   生成随机数的位数
    public static String  getRandom(int n){
        char[] code =  "0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(code[new Random().nextInt(code.length)]);
        }
        return sb.toString();
    }

    public String testSend(String code,String phone){

        //发送短信需要的参数
        String postData="account=zhangcn&password=nanans&mobile="+phone+"&content=您的验证码是："+code+"。请不要把验证码泄露给其他人。如非本人操作，可不用理会！";

        //发送短信调用的url
        String postUrl="http://sms.106jiekou.com/utf8/sms.aspx";

        //发送短信验证码   参数：发送短信需要的参数,发送短信调用的url
        String message = SMS(postData, postUrl);
        return message;
    }

    @Test
    public void testSendMsg(){
            String code = getRandom(6);

            String phone = "15204221952";

            String msg = testSend(code, phone);

            System.out.println(msg);
        }
}
