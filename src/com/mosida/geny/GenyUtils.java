package com.mosida.geny;

import com.mosida.accountMoniter.Utils.ShellUtils;

/**
 * Created by mosida on 4/24/17.
 */
public class GenyUtils {

    public static final String GMTOOL = "/home/mosida/Documents/Geny/genymotion/gmtool";

    public static void cloneGeny(String originDevice, String targetDevice){
        // gmtool admin clone GoogleNexus4-zh-TW-dev GoogleNexus4-zh_TW-dev
        String[] cloneCommands = new String[3];
        cloneCommands[0] = "sleep 1";
        cloneCommands[1] = GMTOOL + " admin clone "+originDevice+" "+targetDevice;
        cloneCommands[2] = "sleep 15";
        ShellUtils.CommandResult cloneResult = ShellUtils.execCommand(cloneCommands, false, true);
        StringBuilder sb = new StringBuilder();
        sb.append("cloneResult.successMsg : "+cloneResult.successMsg)
                .append("\n errorMsg : "+cloneResult.errorMsg)
                .append("\n result : "+cloneResult.result);
    }

    public static void startGeny(String targetDevice){
        String[] startCommands = new String[3];
        startCommands[0] = "sleep 1";
        startCommands[1] = GMTOOL + " admin start "+targetDevice;
        ShellUtils.CommandResult startResult = ShellUtils.execCommand(startCommands, false, true);
        StringBuilder sb = new StringBuilder();
        sb.append("stratResult.successMsg : "+startResult.successMsg)
                .append("\n errorMsg : "+startResult.errorMsg)
                .append("\n result : "+startResult.result);
    }

    public static void stopGeny(String targetDevice){
        String[] stopCommands = new String[3];
        stopCommands[0] = "sleep 1";
        stopCommands[1] = GMTOOL + " admin stop "+targetDevice;
        stopCommands[2] = "sleep 5";
        ShellUtils.CommandResult stopResult = ShellUtils.execCommand(stopCommands, false, true);
        StringBuilder sb = new StringBuilder();
        sb.append("stopResult.successMsg : "+stopResult.successMsg)
                .append("\n errorMsg : "+stopResult.errorMsg)
                .append("\n result : "+stopResult.result);
    }

    public static void deleteGeny(String targetDevice){
        String[] deleteCommands = new String[3];
        deleteCommands[0] = "sleep 1";
        deleteCommands[1] = GMTOOL + " admin delete "+targetDevice;
        deleteCommands[2] = "sleep 5";
        ShellUtils.CommandResult deleteResult = ShellUtils.execCommand(deleteCommands, false, true);
        StringBuilder sb = new StringBuilder();
        sb.append("deleteResult.successMsg : "+deleteResult.successMsg)
                .append("\n errorMsg : "+deleteResult.errorMsg)
                .append("\n result : "+deleteResult.result);
    }

//    public static void pushFileGeny(String targetDevice){
//        String[] stopCommands = new String[3];
//        stopCommands[0] = "sleep 1";
//        stopCommands[1] = GMTOOL + " admin stop "+targetDevice;
//        stopCommands[2] = "sleep 5";
//        ShellUtils.CommandResult stopResult = ShellUtils.execCommand(stopCommands, false, true);
//        StringBuilder sb = new StringBuilder();
//        sb.append("stopResult.successMsg : "+stopResult.successMsg)
//                .append("\n errorMsg : "+stopResult.errorMsg)
//                .append("\n result : "+stopResult.result);
//    }

    public static void pushFile(String originPath, String targetPath) {
        String[] pushCommands = new String[3];
        pushCommands[0] = "sleep 1";
        pushCommands[1] = GMTOOL + " device push "+originPath+" "+targetPath;
        pushCommands[2] = "sleep 1";
        ShellUtils.CommandResult pushResult = ShellUtils.execCommand(pushCommands, false, true);
        StringBuilder sb = new StringBuilder();
        sb.append("pushResult.successMsg : " + pushResult.successMsg)
                .append("\n errorMsg : " + pushResult.errorMsg)
                .append("\n result : " + pushResult.result);
    }
}
