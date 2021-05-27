package repository;

import domain.Utilizator;

public interface IRepoUtilizatori extends IRepository<Integer, Utilizator> {
    Boolean checkUsernameAndPassword(Utilizator utilizator);
}
