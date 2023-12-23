package sk.ukf.SpringApiProject.service;


import sk.ukf.SpringApiProject.entity.Owner;

import java.util.List;

public interface OwnerService {
    List<Owner> findAll();

    Owner findById(int id);

    void save(Owner owner);

    void deleteById(int id);
}
