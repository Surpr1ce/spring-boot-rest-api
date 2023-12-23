package sk.ukf.SpringApiProject.service;

import sk.ukf.SpringApiProject.entity.Car;

import java.util.List;

public interface CarService {
    List<Car> findAll();

    Car findById(int id);

    void save(Car car);

    void deleteById(int id);

    void addCarToOwner(int carId, int ownerId);

    void removeCarFromOwner(int carId, int ownerId);

    Car findTopByOrderByPriceAsc();

    List<Car> findAllByOrderByDrivenKmAsc();

    List<Car> findAllByYearOfProdContains(String yearOfProd);
}
