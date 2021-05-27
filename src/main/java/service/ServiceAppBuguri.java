package service;

import domain.*;
import repository.IRepoBuguri;
import repository.IRepoProiecte;
import repository.IRepoUtilizatori;

public class ServiceAppBuguri implements IService{
    private IRepoBuguri repoBuguri;
    private IRepoProiecte repoProiecte;
    private IRepoUtilizatori repoUtilizatori;

    public ServiceAppBuguri(IRepoBuguri repoBuguri, IRepoProiecte repoProiecte, IRepoUtilizatori repoUtilizatori) {
        this.repoBuguri = repoBuguri;
        this.repoProiecte = repoProiecte;
        this.repoUtilizatori = repoUtilizatori;
    }

    @Override
    public boolean checkUsernameAndPassword(String numeUtilizator, String parola) {
        Utilizator utilizator = new Utilizator(numeUtilizator, parola);
        return repoUtilizatori.checkUsernameAndPassword(utilizator);
    }

    @Override
    public Iterable<Proiect> getAllProiecte() {
        return repoProiecte.getAll();
    }

    @Override
    public void addBug(String descriere, Status status, GradUrgenta gradUrgenta, Proiect proiect) {
        Bug bug = new Bug(descriere, status, gradUrgenta, proiect);
        repoBuguri.save(bug);
    }

    @Override
    public Iterable<Bug> getBuguriProiect(Integer proiectID) {
        return repoBuguri.getBuguriProiect(proiectID);
    }

    @Override
    public void deleteBug(Integer id) {
        repoBuguri.delete(id);
    }

    @Override
    public Proiect getProiectByName(String nume) {
        return repoProiecte.getProiectByName(nume);
    }

    @Override
    public void updateBug(Bug bug) {
        repoBuguri.update(bug.getId(), bug);
    }
}
