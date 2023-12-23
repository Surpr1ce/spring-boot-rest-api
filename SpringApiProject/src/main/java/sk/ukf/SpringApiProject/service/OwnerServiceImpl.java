package sk.ukf.SpringApiProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.ukf.SpringApiProject.dao.OwnerRepository;
import sk.ukf.SpringApiProject.entity.Owner;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService{
    @Autowired
    OwnerRepository ownerRepository;

    @Override
    @Transactional
    public List<Owner> findAll() {
        return ownerRepository.findAll();
    }

    @Override
    @Transactional
    public Owner findById(int id) {
        return ownerRepository.findById(id).orElseThrow(() -> new RuntimeException("Owner not found with id: " + id));
    }

    @Override
    @Transactional
    public void save(Owner owner) {
        ownerRepository.save(owner);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        ownerRepository.deleteById(id);
    }
}
