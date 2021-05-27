package service;

import domain.Bug;
import domain.GradUrgenta;
import domain.Proiect;
import domain.Status;

public interface IService {
    boolean checkUsernameAndPassword(String numeUtilizator, String parola);

    Iterable<Proiect> getAllProiecte();

    void addBug(String descriere, Status status, GradUrgenta gradUrgenta, Proiect proiect);

    Iterable<Bug> getBuguriProiect(Integer proiectID);

    void deleteBug(Integer id);

    Proiect getProiectByName(String nume);

    void updateBug(Bug bug);
}
