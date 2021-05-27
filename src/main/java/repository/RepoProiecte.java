package repository;

import domain.Proiect;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class RepoProiecte implements IRepoProiecte{
    private SessionFactory sessionFactory;

    public RepoProiecte(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Proiect proiect) {
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Proiect otherE) {

    }

    @Override
    public Iterable<Proiect> getAll() {
        List<Proiect> proiecte = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                proiecte = session.createQuery("from Proiect", Proiect.class).list();
                tx.commit();
            } catch (RuntimeException ex) {
                if(tx != null)
                    tx.rollback();
                System.out.println(ex);
                return null;
            }
        }
        return proiecte;
    }

    @Override
    public Proiect getOne(Integer integer) {
        return null;
    }

    @Override
    public Proiect getProiectByName(String nume) {
        List<Proiect> proiecte = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                proiecte = session.createQuery("from Proiect P where P.denumire = ?1", Proiect.class)
                        .setParameter(1, nume)
                        .list();
                tx.commit();
            } catch (RuntimeException ex) {
                if(tx != null)
                    tx.rollback();
                System.out.println(ex);
                return null;
            }
        }
        return proiecte.get(0);
    }
}
