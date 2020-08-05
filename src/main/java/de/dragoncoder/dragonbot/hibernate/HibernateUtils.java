package de.dragoncoder.dragonbot.hibernate;



import de.dragoncoder.dragonbot.Main;
import de.dragoncoder.dragonbot.hibernate.entity.BotGuild;
import de.dragoncoder.dragonbot.hibernate.entity.GuildSettings;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.Properties;

@SuppressWarnings("rawtypes")
public class HibernateUtils {
    private static final String url_Attributes = "?autoReconnect=true"
                                                    .concat("&useUnicode=true")
                                                    .concat("&characterEncoding=UTF-8")
                                                    .concat("&useJDBCCompliantTimezoneShift=true")
                                                    .concat("&useLegacyDatetimeCode=false")
                                                    .concat("&serverTimezone=UTC")
                                                    .concat("&useSSL=FALSE");

    private static SessionFactory botSessionFactory;

    public static SessionFactory getBotSessionFactory() {
        if (botSessionFactory == null) {
            try {
                ArrayList<Class> mappedClasses = new ArrayList<>();
                mappedClasses.add(BotGuild.class);
                mappedClasses.add(GuildSettings.class);

                botSessionFactory = createSessionFactory(mappedClasses);
                return botSessionFactory;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return botSessionFactory;
    }

    private static SessionFactory createSessionFactory(ArrayList<Class> mappedClasses) {
        Configuration configuration = new Configuration();

        // Hibernate settings equivalent to hibernate.cfg.xml
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(Environment.URL, Main.getSecuredData().getMysqlLink() + url_Attributes);
        settings.put(Environment.USER, Main.getSecuredData().getMysqlUser());
        settings.put(Environment.PASS, Main.getSecuredData().getMysqlPassword());
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");

        settings.put(Environment.ENABLE_LAZY_LOAD_NO_TRANS, true);

        settings.put(Environment.SHOW_SQL, "false");

        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

        settings.put(Environment.HBM2DDL_AUTO, "update");

        configuration.setProperties(settings);

        //Mapping
        for (Class c  : mappedClasses) {
            configuration.addAnnotatedClass(c);
        }

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static void shutdown() {
        if (botSessionFactory != null) {
            botSessionFactory.close();
            botSessionFactory = null;
        }
    }
}
