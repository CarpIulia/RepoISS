package repository;

import domain.Bug;

public interface IRepoBuguri extends IRepository<Integer, Bug> {
    public Iterable<Bug> getBuguriProiect(Integer projectID);
}
