package com.mosida.accountMoniter.Utils;

/**
 * Created by mosida on 5/15/17.
 */
public class PhoneUtils {

    public static final String ADB = "/home/mosida/Android/Sdk/platform-tools/adb";

    public static void startMissionService() {


        String[] stopCommands = new String[3];
        stopCommands[0] = "sleep 1";
        stopCommands[1] = ADB + " shell am startservice -n com.mosida.autome/com.mosida.autome.MissionService";
        stopCommands[2] = "sleep 120";
        ShellUtils.CommandResult startResult = ShellUtils.execCommand(stopCommands, false, true);
        StringBuilder sb = new StringBuilder();
        sb.append("startResult.successMsg : " + startResult.successMsg)
                .append("\n errorMsg : " + startResult.errorMsg)
                .append("\n result : " + startResult.result);
    }

}
