package com.mosida.accountMoniter;

import com.google.network.*;
import com.mosida.accountMoniter.account.RegisterAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.util.HashMap;

/**
 * Created by mosida on 4/23/17.
 */
public class LoginTestNew {

    //https://accounts.google.com/signin/v2/identifier?hl=en&passive=true&continue=https%3A%2F%2Fwww.google.com%2F%3Fhl%3Den&flowName=GlifWebSignIn&flowEntry=Identifier
    public  static Logger logger = LogManager.getLogger(Main.class);


    public static void main(String[] args) throws Exception {
        String info = "LoginTest";
        System.setProperty("log4j2.saveFile", info);
        org.apache.logging.log4j.core.Logger rootLogger = (org.apache.logging.log4j.core.Logger) LogManager
                .getRootLogger();
        LoggerContext context = rootLogger.getContext();
        context.reconfigure();

        addAccount();
    }

    public  static String  addAccount(){
        try {

            // 请求参数
            String baseUrl = "http://35.188.39.166/addAccount.php?";
            String reqUrl = baseUrl
                    + "accountName=" + "mosidahuang@gmail.com" + "@gmail.com" + "&"
                    + "password=" + "33338888" + "&"
                    + "phone=" + "15521312103" + "&"
                    + "vcode=" + "123456" + "&"
                    + "gender=" + (true?"1":"0") + "&"
                    + "recEmail=" + "takkyhuang@hotmail.com";
            logger.info(reqUrl);
            RequestEntity<Void> entity = new RequestEntity<Void>(reqUrl, Request.Method.GET, Void.class, new HashMap(), null);
            ProtoRequest<Void> request = new ProtoRequest<>(entity);
            HttpStack httpStack = new HurlStack();
            ((HurlStack) httpStack).setUseProxy(false);
            BasicNetwork network = new BasicNetwork(httpStack);
            NetworkResponse response = network.performRequest(request);
            String result = new String(response.data);
            logger.info("result:"+result);

        }catch (Exception e){
            logger.error(e);
        }
        return null;
    }

    public  static String  addAccount(RegisterAccount registerAccount){
        try {

            // 请求参数
            String baseUrl = "http://35.188.39.166/addAccount.php?";
            String reqUrl = baseUrl
                    + "accountName=" + registerAccount.accountName + "@gmail.com" + "&"
                    + "password=" + registerAccount.password + "&"
                    + "phone=" + registerAccount.phone + "&"
                    + "vcode=" + registerAccount.code + "&"
                    + "gender=" + (registerAccount.male?"1":"0") + "&"
                    + "recEmail" + registerAccount.email;

            RequestEntity<Void> entity = new RequestEntity<Void>(reqUrl, Request.Method.GET, Void.class, new HashMap(), null);
            ProtoRequest<Void> request = new ProtoRequest<>(entity);
            HttpStack httpStack = new HurlStack();
            ((HurlStack) httpStack).setUseProxy(false);
            BasicNetwork network = new BasicNetwork(httpStack);
            NetworkResponse response = network.performRequest(request);
            String result = new String(response.data);
            logger.info("result:"+result);

        }catch (Exception e){
            logger.error(e);
        }
        return null;
    }

}


