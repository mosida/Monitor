package com.mosida.accountMoniter.Utils;

import java.io.File;

/**
 * Created by mosida on 5/15/17.
 */
public class PhoneUtils {

    public static final String ADB = "/home/mosida/Android/Sdk/platform-tools/adb";

    public static void startMissionService() {


        String[] stopCommands = new String[3];
        stopCommands[0] = "sleep 1";
        stopCommands[1] = ADB + " shell am startservice -n com.mosida.autome/com.mosida.autome.MissionService";
        stopCommands[2] = "sleep 60";
        ShellUtils.CommandResult startResult = ShellUtils.execCommand(stopCommands, false, true);
        StringBuilder sb = new StringBuilder();
        sb.append("startResult.successMsg : " + startResult.successMsg)
                .append("\n errorMsg : " + startResult.errorMsg)
                .append("\n result : " + startResult.result);
    }

    public static void startBackupService() {
        String[] stopCommands = new String[3];
        stopCommands[0] = "sleep 1";
        stopCommands[1] = ADB + " shell am startservice -n com.mosida.autome/com.mosida.autome.BackupService";
        stopCommands[2] = "sleep 60";
        ShellUtils.CommandResult startResult = ShellUtils.execCommand(stopCommands, false, true);
        StringBuilder sb = new StringBuilder();
        sb.append("startResult.successMsg : " + startResult.successMsg)
                .append("\n errorMsg : " + startResult.errorMsg)
                .append("\n result : " + startResult.result);
    }

    public static void copyBackupData(String fileName) {
        File file = new File(fileName);
        if (file.exists()){
            System.out.println("file is exists!");
            String[] deleteCommands = new String[1];
            deleteCommands[0] = "rm -rf "+fileName;
            ShellUtils.CommandResult deleteResult = ShellUtils.execCommand(deleteCommands, false, true);
        }else{
            System.out.println("file is no exists!");
        }
        String[] stopCommands = new String[1];
        stopCommands[0] = ADB + " pull /sdcard/TitaniumBackup "+fileName;
        ShellUtils.CommandResult startResult = ShellUtils.execCommand(stopCommands, false, true);
        StringBuilder sb = new StringBuilder();
        sb.append("startResult.successMsg : " + startResult.successMsg)
                .append("\n errorMsg : " + startResult.errorMsg)
                .append("\n result : " + startResult.result);
    }
}
