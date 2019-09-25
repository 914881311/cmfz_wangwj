package com.baizhi;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
/*
这是我第二次的修改
*/
public class TestNewPhone {
    public static void main(String[] args) {
        //写自己的Ak信息
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4Fu3peQNBprw7RQcsife", "7KIhKw2GqMIj0CzpecLCzuFnuzVW3T");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "13715224482");  //手机号
        request.putQueryParameter("SignName", "小蛋黄");   //签名
        request.putQueryParameter("TemplateCode", "SMS_172008044");   //模板Id
        request.putQueryParameter("TemplateParam", "{\"code\":\"888888\"}");  //验证码  json类型的的参数
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());


        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
