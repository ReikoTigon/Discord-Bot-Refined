package de.dragoncoder.dragonbot.hibernate.dao;

import de.dragoncoder.dragonbot.hibernate.DAO;
import de.dragoncoder.dragonbot.hibernate.HibernateUtils;
import de.dragoncoder.dragonbot.hibernate.entity.GuildSettings;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class GuildSettingsDAO implements DAO<GuildSettings> {
    @Override
    public void save(GuildSettings guildSettings) {
        Transaction transaction;
        try (Session session = HibernateUtils.getBotSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(guildSettings);

            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    @Override
    public void update(GuildSettings guildSettings) {
        Transaction transaction;
        try (Session session = HibernateUtils.getBotSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.update(guildSettings);

            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    @Override
    public GuildSettings getByID(long ID) {
        Transaction transaction;
        GuildSettings guildSettings = null;
        try (Session session = HibernateUtils.getBotSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            guildSettings = session.get(GuildSettings.class, ID);

            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return guildSettings;
    }
    @Override
    public List<GuildSettings> getAll() {
        Transaction transaction;
        List<GuildSettings> guildSettings = null;
        try (Session session = HibernateUtils.getBotSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            guildSettings = session.createQuery("FROM GuildSettings", GuildSettings.class)
                    .list();

            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return guildSettings;
    }
    @Override
    public void delete(GuildSettings guildSettings) {
        Transaction transaction;
        try (Session session = HibernateUtils.getBotSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            if (guildSettings != null) {
                session.delete(guildSettings);
            }

            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    @Override
    public void deleteList(List<GuildSettings> t) {
        Transaction transaction;
        try (Session session = HibernateUtils.getBotSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            if (t != null && !t.isEmpty()) {
                t.forEach(session::delete);
            }

            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
