package repository;

import domain.Utilizator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class RepoUtilizatori implements IRepoUtilizatori {

    private SessionFactory sessionFactory;

    public RepoUtilizatori(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Boolean checkUsernameAndPassword(Utilizator utilizator) {
        List<Utilizator> utilizatori = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                utilizatori = session.createQuery("from Utilizator U where U.numeUtilizator = ?1 and U.parola = ?2", Utilizator.class)
                        .setParameter(1, utilizator.getNumeUtilizator())
                        .setParameter(2, utilizator.getParola())
                        .list();
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        if(utilizatori.isEmpty())
            return false;
        return true;
    }

    @Override
    public void save(Utilizator utilizator) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Integer integer, Utilizator otherE) {

    }

    @Override
    public Iterable<Utilizator> getAll() {
        return null;
    }

    @Override
    public Utilizator getOne(Integer integer) {
        return null;
    }
}
