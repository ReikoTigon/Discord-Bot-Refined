package de.dragoncoder.dragonbot;

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

    @Getter
    private static SecuredData securedData;
    private static Logger logger;
    @Getter
    private static Bot dragonBot;

    public static void main(String[] args) {
        setLoggingPath();
        logger = LoggerFactory.getLogger(Main.class);

        securedData = importSecuredData();

        dragonBot = new Bot();
    }

    private static void setLoggingPath () {
        String os = System.getProperty("os.name");

        if (os.equals("Windows 10")) {
            System.setProperty("log.path", ".\\Logs\\");
        }
        else {
            System.setProperty("log.path", "/var/www/html/Logs/");
        }
    }

    private static SecuredData importSecuredData() {
        JSONParser jsonParser = new JSONParser();
        File json = new File("DragonBot(Git) - New\\DONOTOPEN.json");

        if(json.exists()) {
            try (FileReader reader = new FileReader(json)) {
                Object object = jsonParser.parse(reader);
                JSONArray jsonArray = (JSONArray) object;
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                return SecuredData.builder()
                        .token(jsonObject.get("token").toString())
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
