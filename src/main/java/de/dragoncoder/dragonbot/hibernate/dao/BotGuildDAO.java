package de.dragoncoder.dragonbot.hibernate.dao;

import de.dragoncoder.dragonbot.hibernate.DAO;
import de.dragoncoder.dragonbot.hibernate.HibernateUtils;
import de.dragoncoder.dragonbot.hibernate.entity.BotGuild;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import java.util.List;

public class BotGuildDAO implements DAO<BotGuild> {
    @Override
    public void save(BotGuild botGuild) {
        Transaction transaction;
        try (Session session = HibernateUtils.getBotSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.save(botGuild);

            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    @Override
    public void update(BotGuild botGuild) {
        Transaction transaction;
        try (Session session = HibernateUtils.getBotSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.update(botGuild);

            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    @Override
    public BotGuild getByID(long ID) {
        Transaction transaction;
        BotGuild botGuild = null;
        try (Session session = HibernateUtils.getBotSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            botGuild = session.get(BotGuild.class, ID);

            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return botGuild;
    }
    @Override
    public List<BotGuild> getAll() {
        Transaction transaction;
        List<BotGuild> botGuilds = null;
        try (Session session = HibernateUtils.getBotSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            botGuilds = session.createQuery("FROM BotGuild", BotGuild.class)
                            .list();

            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return botGuilds;
    }
    @Override
    public void delete(BotGuild botGuild) {
        Transaction transaction;
        try (Session session = HibernateUtils.getBotSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            if (botGuild != null) {
                session.delete(botGuild);
            }

            transaction.commit();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    @Override
    public void deleteList(List<BotGuild> t) {
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

    public BotGuild getByGuildID(long guild_ID) {
        Transaction transaction;
        BotGuild botGuild = null;
        try (Session session = HibernateUtils.getBotSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            botGuild = session.createQuery("FROM BotGuild WHERE guild_ID = :guild_ID", BotGuild.class)
                            .setParameter("guild_ID", guild_ID)
                            .setMaxResults(1)
                            .getSingleResult();

            transaction.commit();
        } catch (NoResultException e) {
            logger.warn(e.getMessage() + " -> No Guild in DB yet.");
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return botGuild;
    }
}
