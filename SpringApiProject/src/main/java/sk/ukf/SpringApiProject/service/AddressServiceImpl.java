package sk.ukf.SpringApiProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.ukf.SpringApiProject.dao.AddressRepository;
import sk.ukf.SpringApiProject.entity.Address;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    AddressRepository addressRepository;

    @Override
    @Transactional
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    @Transactional
    public Address findById(int id) {
        return addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address not found with id: " + id));
    }

    @Override
    @Transactional
    public void save(Address address) {
        addressRepository.save(address);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        addressRepository.deleteById(id);
    }
}
