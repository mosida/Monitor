package com.mosida.accountMoniter.account;

import com.mosida.accountMoniter.Utils.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mosida on 5/15/17.
 */
public class Comment {
    public static Logger logger = LogManager.getLogger(Comment.class);

    public static List<String> loadComments(String file){
        List<String> list = new ArrayList<>();
        InputStream is = null;
        BufferedReader reader = null;
        try{
            is = Comment.class.getResourceAsStream(file);
            reader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            int index = 0;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                    list.add(line);
                    logger.info(index+" : "+line);
                    index++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static int loadLastComment(String file){
        String line = null;
        InputStream is = null;
        BufferedReader reader = null;
        try{
            is = Comment.class.getResourceAsStream(file);
            reader = new BufferedReader(new InputStreamReader(is));
            line = reader.readLine();
        }catch (Exception e){
            e.printStackTrace();
        }
        return Integer.valueOf(line);
    }

    public static void writeLastComment(String filePath, String num){
        try {
            File f = new File("./src/com/mosida/accountMoniter/account/"+filePath);
            if (!f.exists()) {
                f.createNewFile();
            }
            System.out.println(f.getPath());

            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f));
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(num);
            writer.flush();
            write.close();
            writer.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
