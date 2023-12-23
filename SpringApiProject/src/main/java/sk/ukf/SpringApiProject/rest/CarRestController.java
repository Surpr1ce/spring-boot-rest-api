package sk.ukf.SpringApiProject.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import sk.ukf.SpringApiProject.entity.Car;
import sk.ukf.SpringApiProject.entity.ResponseStatus;
import sk.ukf.SpringApiProject.service.CarService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarRestController {
    @Autowired
    private CarService carService;

    @GetMapping("/cars")
    public List<Car> findAll() {
        return carService.findAll();
    }

    @GetMapping("/cars/cheapest")
    public Car findTopByOrderByPriceAsc() {
        return carService.findTopByOrderByPriceAsc();
    }

    @GetMapping("/cars/lowestKm")
    public List<Car> findAllByOrderByDrivenKmAsc() {
        return carService.findAllByOrderByDrivenKmAsc();
    }

    @GetMapping("/cars/{year}/sorted")
    public ResponseEntity<Object> findAllByYearOfProdContains(@PathVariable String year) {
        List<Car> cars = carService.findAllByYearOfProdContains(year);
        if (!cars.isEmpty()) {
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        ResponseStatus errorResponse = new ResponseStatus(HttpStatus.NOT_FOUND.value(),
                "Year of production (" + year + ") not found", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Object> getCar(@PathVariable int id) {
        Car car;
        try{
            car = carService.findById(id);
        } catch (Exception e) {
            ResponseStatus errorResponse = new ResponseStatus(HttpStatus.NOT_FOUND.value(),
                    "Car id (" + id + ") not found", System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @PostMapping("/cars")
    public ResponseEntity<Object> addCar(@Valid @RequestBody Car car, BindingResult result) {
        if (result.hasErrors()) {
            ResponseStatus errorResponse = new ResponseStatus(HttpStatus.BAD_REQUEST.value(), getErrorMessages(result), System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        car.setId(0);
        carService.save(car);
        ResponseStatus successResponse = new ResponseStatus(HttpStatus.OK.value(),
                "Car added successfully", System.currentTimeMillis());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<Object> updateCar(@PathVariable int id, @Valid @RequestBody Car car, BindingResult result) {
        try{
            carService.findById(id);
        } catch (Exception e) {
            ResponseStatus errorResponse = new ResponseStatus(HttpStatus.NOT_FOUND.value(),
                    "Car id (" + id + ") not found", System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        if (result.hasErrors()) {
            ResponseStatus errorResponse = new ResponseStatus(HttpStatus.BAD_REQUEST.value(), getErrorMessages(result), System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        car.setId(id);
        carService.save(car);
        ResponseStatus successResponse = new ResponseStatus(HttpStatus.OK.value(),
                "Car updated successfully", System.currentTimeMillis());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<Object> deleteCar(@PathVariable int id) {
        try{
            carService.findById(id);
        } catch (Exception e) {
            ResponseStatus errorResponse = new ResponseStatus(HttpStatus.NOT_FOUND.value(),
                    "Car id (" + id + ") not found", System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        carService.deleteById(id);
        ResponseStatus successResponse = new ResponseStatus(HttpStatus.OK.value(),
                "Deleted car id - " + id, System.currentTimeMillis());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PostMapping("/cars/{carId}/owners/{ownerId}")
    public ResponseEntity<Object> addCarToOwner(@PathVariable int carId, @PathVariable int ownerId) {
        try{
            carService.addCarToOwner(carId, ownerId);
        } catch (Exception e) {
            ResponseStatus errorResponse = new ResponseStatus(HttpStatus.NOT_FOUND.value(),
                    "Car id (" + carId + ") not found" + " or Owner id (" + ownerId + ") not found", System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        ResponseStatus successResponse = new ResponseStatus(HttpStatus.OK.value(),
                "Car added successfully to owner", System.currentTimeMillis());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/cars/{carId}/owners/{ownerId}")
    public ResponseEntity<Object> removeCarFromOwner(@PathVariable int carId, @PathVariable int ownerId) {
        try{
            carService.removeCarFromOwner(carId, ownerId);
        } catch (Exception e) {
            ResponseStatus errorResponse = new ResponseStatus(HttpStatus.NOT_FOUND.value(),
                    "Car id (" + carId + ") not found" + " or Owner id (" + ownerId + ") not found", System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        ResponseStatus successResponse = new ResponseStatus(HttpStatus.OK.value(),
                "Car removed successfully from owner", System.currentTimeMillis());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    private String getErrorMessages(BindingResult result) {
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : result.getAllErrors()) {
            sb.append(error.getDefaultMessage()).append("\n");
        }
        return sb.toString();
    }
}
