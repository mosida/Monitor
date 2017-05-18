package com.mosida.accountMoniter.vpn;

import com.mosida.accountMoniter.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 17/3/15.
 * vpn解析工具
 */
public class VpnParseUtils {

    public static List<String> getVpnSupportCountry(String vpnCountry){
        List<String> countryList = getAllCountryList();
        List<String> targetList = new ArrayList<>();
        if(vpnCountry != null){
            Main.logger.info(vpnCountry);
            if(!vpnCountry.equals("all")){
                String[] supportCountry = vpnCountry.split("\\.");
                for(int i=0;i<supportCountry.length;i++){
                    Main.logger.info("支持的地区是:"+supportCountry[i]);
                }
                for(int i=0;i<supportCountry.length;i++){
                    String country = supportCountry[i].toLowerCase();
                    for(int j=0;j<countryList.size();j++){
                        if(countryList.get(j).startsWith(country)){
                            Main.logger.info("添加:"+countryList.get(j));
                            targetList.add(countryList.get(j));
                        }
                    }
                }
            }else{
                targetList = countryList;
            }
        }
        Main.logger.info("最后满足要求的地区有:"+targetList.size());
        return targetList;
    }

    public static  List<String> getAllCountryList(){
        List<String> list = new ArrayList<>();
        list.add("usny");
        list.add("ussf");
        list.add("usch");
        list.add("uswd");
        list.add("usda");
        list.add("usla2");
        list.add("usmi");
        list.add("usse");
        list.add("usny2");
        list.add("usla");
        list.add("usla1");
        list.add("uswd2");
        list.add("usho");
        list.add("ussj");
        list.add("usnj1");
        list.add("usnj2");
        list.add("usnj3");
        list.add("usde");
        list.add("usda2");
        list.add("usat");
        list.add("usta1");
        list.add("uslv");
        list.add("usla3");
        list.add("cato");
        list.add("cato2");
        list.add("camo2");
        list.add("cava");
        list.add("ukbe");
        list.add("ukel");
        list.add("uklo");
        list.add("ukbe2");
        list.add("ukke");
        list.add("ukch");
        list.add("ukma");
        list.add("nlam");
        list.add("nlro");
        list.add("nlam2");
        list.add("ch1");
        list.add("frpa1");
        list.add("frpa2");
        list.add("hk2");
        list.add("hk1");
        list.add("hk3");
        list.add("jpto1");
        list.add("jpto2");
        list.add("mx1");
        list.add("defr1");
        list.add("defr2");
        list.add("dedu");
        list.add("deda");
        list.add("itmi");
        list.add("itco");
        list.add("se1");
        list.add("ie1");
        list.add("is1");
        list.add("no1");
        list.add("dk1");
        list.add("be1");
        list.add("fi1");
        list.add("at1");
        list.add("es1");
        list.add("ad1");
        list.add("pt1");
        list.add("me1");
        list.add("ba1");
        list.add("tr1");
        list.add("inmu1");
        list.add("inch");
        list.add("in1");
        list.add("il1");
        list.add("gr1");
        list.add("am1");
        list.add("pl1");
        list.add("by1");
        list.add("lt1");
        list.add("lv1");
        list.add("ee1");
        list.add("cz1");
        list.add("lu1");
        list.add("tw1");
        list.add("tw3");
        list.add("sgcb");
        list.add("sgmb");
        list.add("sgju");
        list.add("my1");
        list.add("id1");
        list.add("ru1");
        list.add("hu1");
        list.add("ro1");
        list.add("bg1");
        list.add("ua1");
        list.add("ke1");
        list.add("za1");
        list.add("eg1");
        list.add("lk1");
        list.add("th1");
        list.add("bt1");
        list.add("bnbr");
        list.add("lala");
        list.add("mm1");
        list.add("np1");
        list.add("bd1");
        list.add("krsk");
        list.add("aume");
        list.add("ausy2");
        list.add("ausy");
        list.add("nz1");
        list.add("br1");
        list.add("br2");
        list.add("ar1");
        list.add("im1");
        list.add("mt1");
        list.add("li1");
        list.add("cy1");
        list.add("al1");
        list.add("hr1");
        list.add("si1");
        list.add("sk1");
        list.add("mc1");
        list.add("je1");
        list.add("mk1");
        list.add("mdmo");
        list.add("rs1");
        list.add("ge1");
        list.add("az1");
        list.add("kz1");
        list.add("uz1");
        list.add("kg1");
        list.add("pk1");
        list.add("dz1");
        list.add("kh1");
        list.add("mo1");
        list.add("vn1");
        list.add("ph1");
        list.add("mn1");
        list.add("bs1");
        list.add("pa1");
        list.add("cr1");
        list.add("co1");
        list.add("ve1");
        list.add("ec1");
        list.add("gt1");
        list.add("uy1");
        list.add("cl1");
        return list;
    }
}
