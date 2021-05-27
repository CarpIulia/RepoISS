package repository;

import domain.Proiect;

public interface IRepoProiecte extends IRepository<Integer, Proiect> {
    Proiect getProiectByName(String nume);
}
