package com.mosida.accountMoniter;

import com.google.network.*;
import org.apache.http.util.TextUtils;

import java.util.HashMap;

/**
 * Created by mosida on 4/22/17.
 */
public class EmaPhoneService {

    // 登录并获取 token
    public static String  getEMALoginToken(){
        String token = null;
        String url = "http://api.ema666.com/Api/userLogin?uName=mosida&pWord=jianqiao098&Developer=oWN%2fsx%2b%2fFF370FKOaZakpg%3d%3d&Code=UTF8";
        RequestEntity<Void> entity = new RequestEntity<Void>(url, Request.Method.GET,Void.class,new HashMap(),null);
        ProtoRequest<Void> request = new ProtoRequest<>(entity);
        HttpStack httpStack = new HurlStack();
        ((HurlStack)httpStack).setUseProxy(false);
        BasicNetwork network = new BasicNetwork(httpStack);
        try {
            NetworkResponse response = network.performRequest(request);
            String result  = new String(response.data);
            if(!TextUtils.isEmpty(result)){
                String[] array = result.split("&");
                token = array[0];
            }
            Main.logger.info("登录ema令牌："+token);
        }catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    // 获取电话号码
    public static String getEMAPhoneNumber(String token){
        String phoneNumber = null;
        if(TextUtils.isEmpty(token)){
            Main.logger.info("获取token失败");
        }
        String url = "http://api.ema666.com/Api/userGetPhone?ItemId=133&token=%s&PhoneType=0&Code=UTF8";
        url = String.format(url, token);
        RequestEntity<Void> entity = new RequestEntity<Void>(url,Request.Method.GET,Void.class,new HashMap(),null);
        ProtoRequest<Void> request = new ProtoRequest<>(entity);
        HttpStack httpStack = new HurlStack();
        ((HurlStack)httpStack).setUseProxy(false);
        BasicNetwork network = new BasicNetwork(httpStack);
        try {
            NetworkResponse response = network.performRequest(request);
            phoneNumber = new String(response.data);
            Main.logger.info("获取电话是："+phoneNumber);
        }catch (Exception e){
            e.printStackTrace();
        }
        return phoneNumber;
    }

    // 获取验证码
    public static String  getCode(String token){
        String result = null;
        String url = "http://api.ema666.com/Api/userGetMessage?token=%s&Code=UTF8";
        url = String.format(url, token);
        RequestEntity<Void> entity = new RequestEntity<Void>(url,Request.Method.GET,Void.class,new HashMap(),null);
        ProtoRequest<Void> request = new ProtoRequest<>(entity);
        HttpStack httpStack = new HurlStack();
        ((HurlStack)httpStack).setUseProxy(false);
        BasicNetwork network = new BasicNetwork(httpStack);
        try {
            NetworkResponse response = network.performRequest(request);
            result = new String(response.data);
            if(!TextUtils.isEmpty(result) && result.equals("Null")){
                result = null;
            }
            Main.logger.info("获取验证码是"+result);
            //MSG&133&13075765945&【Google】“G-179452”是您的 Google 验证码。[End]
//            result.matches("([0-9]{6})");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    // 释放所有电话
    public static void releaseAllPhoneNumber(String token){
        String url = "http://api.ema666.com/Api/userReleaseAllPhone?token=%s";
        url = String.format(url,token);
        RequestEntity<Void> entity = new RequestEntity<Void>(url,Request.Method.GET,Void.class,new HashMap(),null);
        ProtoRequest<Void> request = new ProtoRequest<>(entity);
        HttpStack httpStack = new HurlStack();
        ((HurlStack)httpStack).setUseProxy(false);
        BasicNetwork network = new BasicNetwork(httpStack);
        try {
            NetworkResponse response = network.performRequest(request);
            String result = new String(response.data);
            if(!TextUtils.isEmpty(result) && result.equals("Null")){
                result = null;
            }
            Main.logger.info("释放所有电话号码："+result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 释放电话
    public static void releasePhoneNumber(String token,String phone){
        String url = "http://api.ema666.com/Api/userReleasePhone?token=%s&phoneList=%s-133&Code=UTF8";
        url = String.format(url, token,phone);
        RequestEntity<Void> entity = new RequestEntity<Void>(url,Request.Method.GET,Void.class,new HashMap(),null);
        ProtoRequest<Void> request = new ProtoRequest<>(entity);
        HttpStack httpStack = new HurlStack();
        ((HurlStack)httpStack).setUseProxy(false);
        BasicNetwork network = new BasicNetwork(httpStack);
        try {
            NetworkResponse response = network.performRequest(request);
            String result = new String(response.data);
            if(!TextUtils.isEmpty(result) && result.equals("Null")){
                result = null;
            }
            Main.logger.info("解除电话"+result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 加入黑名单
    public static void balckListPhoneNumber(String token,String phone){
        String url = "http://api.ema666.com/Api/userAddBlack?token=%s&phoneList=%s-133&Code=UTF8";
        url = String.format(url, token,phone);
        RequestEntity<Void> entity = new RequestEntity<Void>(url,Request.Method.GET,Void.class,new HashMap(),null);
        ProtoRequest<Void> request = new ProtoRequest<>(entity);
        HttpStack httpStack = new HurlStack();
        ((HurlStack)httpStack).setUseProxy(false);
        BasicNetwork network = new BasicNetwork(httpStack);
        try {
            NetworkResponse response = network.performRequest(request);
            String result = new String(response.data);
            if(!TextUtils.isEmpty(result) && result.equals("Null")){
                result = null;
            }
            Main.logger.info("电话加黑名单"+result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
