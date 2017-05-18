package com.mosida.accountMoniter;

import com.mosida.accountMoniter.Utils.PhoneUtils;
import com.mosida.accountMoniter.account.LoginByWeb;

/**
 * Created by mosida on 5/15/17.
 */
public class Test {
    public static String tempDirectory = "/home/mosida/Documents/reviews-projects/Temp/";
    public static String tempFile = "myaccount";
    public static String targetFile = "sdcard/myaccount";

    public static void main(String[] args) throws Exception {

//        LoginByWeb.loginByChrome();
//        PhoneUtils.pushFile(tempDirectory+tempFile, targetFile);
        PhoneUtils.startMissionService();
    }
}
