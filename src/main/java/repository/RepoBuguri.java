package repository;

import domain.Bug;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class RepoBuguri implements IRepoBuguri{

    private SessionFactory sessionFactory;

    public RepoBuguri(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Bug bug) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(bug);
                tx.commit();
            } catch (RuntimeException ex) {
                if(tx != null)
                    tx.rollback();
                System.out.println(ex);
            }
        }
    }

    @Override
    public void delete(Integer id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Bug bug = (Bug)session.get(Bug.class, id);
                session.delete(bug);
                tx.commit();
            } catch (RuntimeException ex) {
                if(tx != null)
                    tx.rollback();
                System.out.println(ex);
            }
        }

    }

    @Override
    public void update(Integer integer, Bug otherE) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Bug bug = session.load(Bug.class, integer);
                bug.setNumeProgramator(otherE.getNumeProgramator());
                bug.setStatus(otherE.getStatus());
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public Iterable<Bug> getAll() {
        return null;
    }

    @Override
    public Bug getOne(Integer integer) {
        return null;
    }

    @Override
    public Iterable<Bug> getBuguriProiect(Integer projectID) {
        List<Bug> buguri = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                buguri = session.createQuery("from Bug B where B.proiect.id = ?1", Bug.class)
                        .setParameter(1, projectID)
                        .list();
                tx.commit();
            } catch (RuntimeException ex) {
                if(tx != null)
                    tx.rollback();
                System.out.println(ex);
            }
        }
        return  buguri;
    }
}
