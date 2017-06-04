package com.mosida.accountMoniter;

import com.google.network.*;
import com.mosida.accountMoniter.Utils.FileUtils;
import com.mosida.accountMoniter.Utils.PhoneUtils;
import com.mosida.accountMoniter.account.Comment;
import com.mosida.accountMoniter.account.GmailAccounts;
import com.mosida.accountMoniter.account.GmailInfo;
import com.mosida.accountMoniter.account.LoginByWeb;
import com.mosida.accountMoniter.vpn.IpUtils;
import com.mosida.geny.GenyUtils;
import org.apache.http.util.TextUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mosida on 5/14/17.
 */
public class MainReAuth {

    public static int num = 150;

    //    public static String vpnCountry = "US";

    public static GmailAccounts gmailAccounts;
    public static String accountSource = "accountcool";
    public static Logger logger = LogManager.getLogger(MainReAuth.class);
    public static List<GmailInfo> gmails;
    public static List<String> country;
    public static String packageName = "club.fromfactory";

    public static void main(String[] args) throws Exception {
        // log 配置
        String info = "GP-ReviewsMission";
        System.setProperty("log4j2.saveFile",info);
        org.apache.logging.log4j.core.Logger rootLogger = (org.apache.logging.log4j.core.Logger) LogManager
                .getRootLogger();
        LoggerContext context = rootLogger.getContext();
        context.reconfigure();

        // 加载帐号信息
        gmailAccounts = new GmailAccounts();
        gmails = gmailAccounts.loadAccounts(accountSource);
        if (gmails==null){
            logger.info("获取帐号时地址错误");
            return;
        }else if(gmails.size()<=0){
            logger.info("获取帐号时为空 "+ gmails.size());
            return;
        }

        // country
        country = new ArrayList<>();
        country.add("tw1");
        country.add("tw3");

        for (int i=0; i<num; i++){
            if (checkEmailReviewed(gmails.get(i).email, packageName)){
                continue;
            }
            // 更换ip
//            country = VpnParseUtils.getVpnSupportCountry(vpnCountry);
            IpUtils.changeVpn(country);
            // 登录帐号
            if (LoginByWeb.loginByChrome(gmails.get(i))){
                logger.info("帐号登录成功");
            }else{
                logger.info("帐号登录失败");
                return;
            }


        }

        IpUtils.exitVpn();

    }

    public static String getTimeString(){
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=format.format(date);
        return time;
    }

    // 释放电话
    public static boolean checkEmailReviewed (String email, String apk){
        String url = "http://35.188.39.166/getReview.php?apk="+apk+"&email="+email;
        RequestEntity<Void> entity = new RequestEntity<Void>(url, Request.Method.GET,Void.class,new HashMap(),null);
        ProtoRequest<Void> request = new ProtoRequest<>(entity);
        HttpStack httpStack = new HurlStack();
        ((HurlStack)httpStack).setUseProxy(false);
        BasicNetwork network = new BasicNetwork(httpStack);
        try {
            NetworkResponse response = network.performRequest(request);
            String result = new String(response.data);
            if(!TextUtils.isEmpty(result) && result.equals("Null")){
                result = null;
            }else{
                if (result!=null && result.contains("reviewed")){
                    Main.logger.info("已经打过评论 "+result);
                    return true;
                }else{
                    Main.logger.info("还没打过评论 "+result);
                    return false;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
