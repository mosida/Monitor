package com.mosida.accountMoniter.account;

import com.mosida.accountMoniter.Main;
import com.mosida.accountMoniter.Utils.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mosida on 5/14/17.
 */
public class GmailAccounts {
    public static Logger logger = LogManager.getLogger(GmailAccounts.class);

    public List<GmailInfo> loadAccounts(String dataSource) {
        List<GmailInfo> list = new ArrayList<>();
        InputStream is = null;
        BufferedReader reader = null;
        try {
            is = GmailAccounts.class.getResourceAsStream(dataSource);
//            is = new FileInputStream(new File(dataSource));
            reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            int index = 0;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                try {
                    String[] account = line.split(",");
                    GmailInfo gmailInfo = new GmailInfo();
                    gmailInfo.gid = account[0];
                    gmailInfo.email = account[1];
                    gmailInfo.password = account[2];
                    gmailInfo.recMail = account[3];
                    list.add(gmailInfo);

                    logger.info("email:"+gmailInfo.email+" password:"+gmailInfo.password+" gid:"+gmailInfo.gid+" recmail:"+gmailInfo.recMail);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void writeReviewByAccount(String file, String data){
        FileUtils.writeFile(file, data, true);
    }

}
