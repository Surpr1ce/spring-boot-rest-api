package sk.ukf.SpringApiProject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.ukf.SpringApiProject.dao.CarRepository;
import sk.ukf.SpringApiProject.dao.OwnerRepository;
import sk.ukf.SpringApiProject.entity.Car;
import sk.ukf.SpringApiProject.entity.Owner;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl implements CarService{
    @Autowired
    CarRepository carRepository;
    @Autowired
    OwnerRepository ownerRepository;

    @Override
    @Transactional
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    @Transactional
    public Car findById(int id) {
        return carRepository.findById(id).orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
    }

    @Override
    @Transactional
    public void save(Car car) {
        carRepository.save(car);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        carRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addCarToOwner(int carId, int ownerId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found with id " + carId));
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found with id " + ownerId));
        List<Car> cars = owner.getCars();
        if (cars == null) {
            cars = new ArrayList<>();
        }
        if (!cars.contains(car)) {
            cars.add(car);
            owner.setCars(cars);
            ownerRepository.save(owner);
        }
    }

    //something wrong maybe?
    @Override
    @Transactional
    public void removeCarFromOwner(int carId, int ownerId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found with id " + carId));
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found with id " + ownerId));
        List<Car> cars = owner.getCars();
        if (cars == null) {
            return;
        }
        cars.remove(car);
        owner.setCars(cars);
        ownerRepository.save(owner);
    }

    @Override
    @Transactional
    public Car findTopByOrderByPriceAsc() {
        return carRepository.findTopByOrderByPriceAsc();
    }

    @Override
    @Transactional
    public List<Car> findAllByOrderByDrivenKmAsc() {
        return carRepository.findAllByOrderByDrivenKmAsc();
    }

    @Override
    @Transactional
    public List<Car> findAllByYearOfProdContains(String yearOfProd) {
        return carRepository.findAllByYearOfProdContains(yearOfProd);
    }
}
