package sk.ukf.SpringApiProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.ukf.SpringApiProject.entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
}
