package com.mosida.accountMoniter.account;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mosida on 4/23/17.
 */
public class AccountInfoBuilder {

    static Logger logger = LogManager.getLogger(AccountInfoBuilder.class);
    private static final String[] EMAILS = new String[] {"@yahoo.com", "@hotmail.com", "@163.com", "@qq.com", "@yeah.net", "@gmail.com", "@outlook.com"};

    // female / male first & last name
    private static List<String> ffns_en = new ArrayList<String>();
    private static List<String> mfns_en = new ArrayList<String>();
    private static List<String> lns_en = new ArrayList<String>();


    private AccountInfoBuilder(){

    }

    private static void loadInfos() throws Exception{
        ffns_en.clear();
        mfns_en.clear();
        lns_en.clear();

        InputStream is = null;
        is = AccountInfoBuilder.class.getResourceAsStream("ffn_en");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = "";
        while((line = br.readLine()) != null)
        {
            ffns_en.add(line);
        }
        logger.info("ffn 数量:"+ffns_en.size());
        br.close();

        is = AccountInfoBuilder.class.getResourceAsStream("mfn_en");
        br = new BufferedReader(new InputStreamReader(is));
        while((line = br.readLine()) != null)
        {
            mfns_en.add(line);
        }
        logger.info("mfn 数量:"+mfns_en.size());
        br.close();

        is = AccountInfoBuilder.class.getResourceAsStream("ln_en");
        br = new BufferedReader(new InputStreamReader(is));
        while((line = br.readLine()) != null)
        {
            lns_en.add(line);
        }
        logger.info("ln 数量:"+lns_en.size());
        br.close();

        is.close();

    }



    public static RegisterAccount getRegisterAccount(boolean male){
        if(ffns_en.isEmpty() || mfns_en.isEmpty() || lns_en.isEmpty())
        {
            try
            {
                loadInfos();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                logger.info("vcode loadInfos:"+e.toString());
                return null;
            }
        }

        String fn = "";

        if (!male){
            fn = ffns_en.get((int)(Math.random() * ffns_en.size()));
        } else {
            fn = mfns_en.get((int)(Math.random() * mfns_en.size()));
        }
        String ln = lns_en.get((int)(Math.random() * lns_en.size()));

        Haikunator haikunator = new HaikunatorBuilder().setTokenHex(true).setDelimiter("").build();
        String password = haikunator.haikunate();
        String email = haikunator.haikunate() +  EMAILS[(int)(Math.random() * EMAILS.length)];
        return new RegisterAccount(fn, ln, password, email, male);
    }


}
