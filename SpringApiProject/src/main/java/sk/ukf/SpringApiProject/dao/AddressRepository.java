package sk.ukf.SpringApiProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.ukf.SpringApiProject.entity.Address;


@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
