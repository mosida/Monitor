package com.mosida.accountMoniter.vpn;

import com.google.gson.Gson;
import com.google.network.*;
import com.mosida.accountMoniter.Main;
import com.mosida.accountMoniter.Utils.FileUtils;
import com.mosida.accountMoniter.Utils.ShellUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by apple on 16/10/27.
 */
public class IpUtils {

//    public static String getIpCity(String ip){
//        String resultCity = null;
//        try {
//            String ipUrl = "http://127.0.0.1:18009/geoip?ip=%s&brief=1";
//            ipUrl = String.format(ipUrl, ip);
//            RequestEntity<Void> requestEntity = new RequestEntity<Void>(ipUrl, HurlStack.Method.GET, Void.class, new HashMap<>(), null);
//            ProtoRequest<Void> ipRequest = new ProtoRequest<>(requestEntity);
//            HurlStack ipStack = new HurlStack();
//            ipStack.setUseProxy(false);
//            BasicNetwork ipNetwork = new BasicNetwork(ipStack);
//            NetworkResponse ipResponse = ipNetwork.performRequest(ipRequest);
//            String ipResult = new String(ipResponse.data);
//            Gson ipGson = new Gson();
//            Main.IpJson ipJson = (Main.IpJson) ipGson.fromJson(ipResult, Main.IpJson.class);
//            Main.logger.info("地址:"+ipJson.data.City);
//            resultCity = ipJson.data.City;
//        }catch(Exception e){
//            Main.logger.error(e);
//        }
//        return  resultCity;
//    }

    public static String getIp(){
        String url = "https://api.ipify.org";
        String ip = null;
        try {
            RequestEntity<Void> entity = new RequestEntity<Void>(url, Request.Method.GET, Void.class, new HashMap(), null);
            ProtoRequest<Void> request = new ProtoRequest<Void>(entity);
            HttpStack httpStack = new HurlStack();
            if(Main.requestUseProxy) {
                ((HurlStack) httpStack).setUseProxy(true);
            }else{
                ((HurlStack) httpStack).setUseProxy(false);
            }
            if(Main.socks5Proxy) {
                ((HurlStack) httpStack).setSocksProxy(true);
            }else{
                ((HurlStack) httpStack).setSocksProxy(false);
            }
            BasicNetwork basicNetwork = new BasicNetwork(httpStack);
            NetworkResponse networkResponse = basicNetwork.performRequest(request);
            ip = new String(networkResponse.data);
        }catch (Exception e){
            logError(e);
        }
        return ip;
    }

    public static void logError(Exception e){
        String fullStackTrace = org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(e);
        Main.logger.error(    fullStackTrace);
    }

//    public static void logLoginIp(String ip){
//        FileUtils.writeFile(Main.ipCountryFilePath,ip+"\n",true);
//    }

    public  static String  getIpCountry(String ip){
        try {
            String URL = "http://ip.taobao.com/service/getIpInfo.php?ip=%s";
            URL = String.format(URL, ip);
            RequestEntity<Void> entity = new RequestEntity<Void>(URL, Request.Method.GET, Void.class, new HashMap(), null);
            ProtoRequest<Void> request = new ProtoRequest<>(entity);
            HttpStack httpStack = new HurlStack();
            ((HurlStack) httpStack).setUseProxy(false);
            BasicNetwork network = new BasicNetwork(httpStack);
            NetworkResponse response = network.performRequest(request);
            String result = new String(response.data);
            Gson gson = new Gson();
            IpCountryJson json = gson.fromJson(result,IpCountryJson.class);
            if(json != null && json.code == 0){
                if(json.data != null){
                    return json.data.country;
                }
            }
        }catch (Exception e){
            logError(e);
        }
        return null;
    }

    class IpCountry{
        public String country;
        public String country_id;
        public String area;
        public String area_id;
        public String region;
        public String region_id;
        public String city;
        public String city_id;
        public String county;
        public String county_id;
        public String isp;
        public String isp_id;
        public String ip;
    }
    class IpCountryJson{
        public int code;
        public IpCountry data;
    }

//    public static boolean isIpRepeated(String ip){
//        String result = FileUtils.readFromFile(Main.ipCountryFilePath);
//        if(result != null && result.contains(ip)){
//            return true;
//        }
//        return false;
//    }

//    public synchronized static void logIpResult(GpData gpData){
//        try {
//            Main.logger.info("logIp线程id:"+Thread.currentThread().getId());
//            String ip = getIp();
//            int port = HurlStack.port;
//            boolean isRepeated = isIpRepeated(ip);
//            String country = getIpCountry(ip);
//            StringBuilder sb = new StringBuilder();
//            sb.append(ip).append(",")
//                    .append(country).append(",")
//                    .append(port).append(",")
//                    .append(gpData.email).append(",")
//                    .append(GpData.ActionResult.startTime).append(",  ")
//                    .append(GpData.ActionResult.useTime).append(",")
//                    .append(GpData.ActionResult.isSuccess ? "success" : "fail")
//                    .append(isRepeated ? ",repeated" : "");
//            FileUtils.writeFile(Main.ipCountryFilePath, sb.toString() + "\n", true);
//        }catch (Exception e){
//            ExceptionUtils.printErrorStack(e);
//        }
//
//    }

    public static void changePPTP(){
        Main.logger.info("先停掉pppd");
        String[] disconnectCommands = new String[3];
        disconnectCommands[0] = "sleep 10";
        disconnectCommands[1] = "pkill pppd";
        disconnectCommands[2] = "sleep 20";
        ShellUtils.CommandResult disResult = ShellUtils.execCommand(disconnectCommands,false,true);

        logShellResult(disResult,"关掉pppd");

//        String targetCountry = Main.specficCountry != null?Main.specficCountry:vpnList.get(0);
        String[] setupCommands = new String[2];
        setupCommands[0] = "sleep 20";
        setupCommands[1] = "pptpsetup --create migo2 --server m2.mypptp.com  --username amigo  --password amigoamigo  --start";
        ShellUtils.CommandResult setupResult = ShellUtils.execCommand(setupCommands,false,true);

        logShellResult(setupResult,"设置pptp");

        String[] callCommands = new String[3];
        callCommands[0] = "sleep 20";
        callCommands[1] = "route add -net 0.0.0.0 ppp1";
        callCommands[2] = "sleep 30";

        ShellUtils.CommandResult callResult = ShellUtils.execCommand(callCommands, false, true);

        logShellResult(callResult,"打开pptp");

        String[] commands = new String[1];
        commands[0] = "curl http://cip.cc";
        ShellUtils.CommandResult commandResult = ShellUtils.execCommand(commands, false, true);
        Main.logger.info("\n\n");

        String ip = IpUtils.getIp();
        Main.logger.info(ip);
        FileUtils.writeFile("data/pptpResult","\n当前ip:"+ip+"\n",true);
//        reportIp(ip);
    }



    public static void logShellResult(ShellUtils.CommandResult commandResult,String tag){
        if(commandResult != null){
            StringBuilder sb = new StringBuilder();
            sb.append("\n"+tag+":success"+commandResult.successMsg+"\n")
                    .append("error:"+commandResult.errorMsg+"\n")
                    .append("result:"+commandResult.result+"\n");
            Main.logger.info(sb.toString());
            FileUtils.writeFile("data/pptpIpResult",sb.toString()+"\n",true);
            if(tag.equals("关掉pppd")){
                FileUtils.writeFile("data/pptpIpResult","================================================"+"\n",true);
            }
        }
    }

    public static void exitVpn() {

        Main.logger.info("先停掉vpn");
        String[] disconnectCommands = new String[3];
        disconnectCommands[0] = "sleep 1";
        disconnectCommands[1] = "expressvpn disconnect";
        disconnectCommands[2] = "sleep 5";
        ShellUtils.CommandResult disResult = ShellUtils.execCommand(disconnectCommands,false,true);
        StringBuilder sb = new StringBuilder();
        sb.append("disResult.successMsg:"+disResult.successMsg)
                .append("\n errorMsg:"+disResult.errorMsg)
                .append("\n result:"+disResult.result);
        Main.logger.info(sb.toString());
    }

    public static void changeVpn(List<String> vpnList){

        Main.logger.info("先停掉vpn");
        String[] disconnectCommands = new String[3];
        disconnectCommands[0] = "sleep 1";
        disconnectCommands[1] = "expressvpn disconnect";
        disconnectCommands[2] = "sleep 5";

        ShellUtils.CommandResult disResult = ShellUtils.execCommand(disconnectCommands,false,true);

        StringBuilder sb = new StringBuilder();
        sb.append("disResult.successMsg:"+disResult.successMsg)
                .append("\n errorMsg:"+disResult.errorMsg)
                .append("\n result:"+disResult.result);
        Main.logger.info(sb.toString());

        Main.logger.info("再打开vpn");
        Collections.shuffle(vpnList);
        String targetCountry = Main.specficCountry != null?Main.specficCountry:vpnList.get(0);
        String[] configCommands = new String[3];
        configCommands[0] = "sleep 1";
        configCommands[1] = "expressvpn connect "+targetCountry;
        configCommands[2] = "sleep 15";

        ShellUtils.CommandResult commandResult = ShellUtils.execCommand(configCommands, false, true);
//        http://amigov.com/sip/?ip=111.111.111.11&country=HK
        StringBuilder sb1 = new StringBuilder();
        sb1.append("commandResult.successMsg:"+commandResult.successMsg)
                .append("\n errorMsg:"+commandResult.errorMsg)
                .append("\n result:"+commandResult.result);
        Main.logger.info(sb1.toString());

//        StringBuilder configSb = new StringBuilder();
        String ip = IpUtils.getIp();
//        reportIp(ip);
        Main.logger.info(ip);

//        configSb.append(ip);
//        boolean isContain = false;
//        List<String> ipList = FileUtils.readDataFromFile("data/testVpn");
//        if(ipList != null){
//            for(int i=0;i<ipList.size();i++){
//                if(ipList.get(i).contains(ip)){
//                    isContain = true;
//                    break;
//                }
//            }
//        }
//        if(!isContain) {
//            String country = IpUtils.getIpCountry(ip);
//            configSb.append("  "+country);
//            FileUtils.writeFile("data/testVpn", configSb.toString() + "\n", true);
//        }
    }

//    public static void reportIp(String ip){
//        if(TextUtils.isEmpty(ip)) return;
//        try {
//            String country = IpUtils.getIpCountry(ip);
////        http://amigov.com/sip/?ip=111.111.111.11&country=HK
//            String url = "http://amigov.com/sip/?ip=" + ip;
//            if (country != null) {
//                url = url + "&country=" + country;
//            }
//            RequestEntity<Void> entity = new RequestEntity<Void>(url, HurlStack.Method.GET, Void.class, new HashMap<>(), null);
//            ProtoRequest<Void> protoRequest = new ProtoRequest<>(entity);
//            HurlStack httpStack = new HurlStack();
//            BasicNetwork network = new BasicNetwork(httpStack);
//            NetworkResponse response = network.performRequest(protoRequest);
//            Main.logger.info("上报vpnIp结果:"+new String(response.data));
//        }catch (Exception e){
//            Main.logger.error(e);
//        }
//    }
}
