package de.dragoncoder.dragonbot;

import de.dragoncoder.dragonbot.hibernate.HibernateUtils;
import de.dragoncoder.dragonbot.structures.SecuredData;
import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    //Static Initializer
    static {
        //Set path for LogBack HTML-File output before anything other happens
        String os = System.getProperty("os.name");

        if (os.equals("Windows 10")) {
            System.setProperty("log.path", ".\\Logs\\");
        }
        else {
            System.setProperty("log.path", "/var/www/html/Logs/");
        }
    }

    @Getter
    private static SecuredData securedData;
    @Getter
    private static Bot dragonBot;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) {
        //Get connection-data like passwords and connection-links
        securedData = importSecuredData();

        //Start Hibernate + Create Missing Tables
        HibernateUtils.getBotSessionFactory();

        //Start the bot
        dragonBot = new Bot();
    }

    private static SecuredData importSecuredData() {
        JSONParser jsonParser = new JSONParser();
        File json = new File("DONOTOPEN.json");

        if(json.exists()) {
            try (FileReader reader = new FileReader(json)) {
                Object object = jsonParser.parse(reader);
                JSONArray jsonArray = (JSONArray) object;
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                return SecuredData.builder()
                        .token(jsonObject.get("token").toString())
                        .inviteLink(jsonObject.get("link").toString())
                        .mysqlLink(jsonObject.get("mysqlLink").toString())
                        .mysqlUser(jsonObject.get("mysqlUser").toString())
                        .mysqlPassword(jsonObject.get("mysqlPswd").toString())
                    .build();
            } catch (ParseException | IOException e) {
                logger.error("Error whilst parsing SecuredData", e);
                return null;
            }
        } else {
            logger.error("No SecuredData found in \"" + json.getAbsolutePath() + "\"");
            return null;
        }
    }

}
