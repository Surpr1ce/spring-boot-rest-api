package sk.ukf.SpringApiProject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.ukf.SpringApiProject.entity.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    Car findTopByOrderByPriceAsc(); //first cheapest
    List<Car> findAllByOrderByDrivenKmAsc(); //lowest driven km
    List<Car> findAllByYearOfProdContains(String yearOfProd); //find by year
}
