package sk.ukf.SpringApiProject.service;

import sk.ukf.SpringApiProject.entity.Address;

import java.util.List;

public interface AddressService {
    List<Address> findAll();

    Address findById(int id);

    void save(Address address);

    void deleteById(int id);
}
