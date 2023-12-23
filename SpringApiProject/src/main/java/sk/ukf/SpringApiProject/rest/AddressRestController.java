package sk.ukf.SpringApiProject.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import sk.ukf.SpringApiProject.entity.Address;
import sk.ukf.SpringApiProject.entity.ResponseStatus;
import sk.ukf.SpringApiProject.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressRestController {
    @Autowired
    private AddressService addressService;

    @GetMapping("/addresses")
    public List<Address> findAll() {
        return addressService.findAll();
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<Object> getAddress(@PathVariable int id) {
        Address address;
        try{
            address = addressService.findById(id);
        } catch (Exception e) {
            ResponseStatus errorResponse = new ResponseStatus(HttpStatus.NOT_FOUND.value(),
                    "Address id (" + id + ") not found", System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PostMapping("/addresses")
    public ResponseEntity<Object> addAddress(@Valid @RequestBody Address address, BindingResult result) {
        if (result.hasErrors()) {
            ResponseStatus errorResponse = new ResponseStatus(HttpStatus.BAD_REQUEST.value(), getErrorMessages(result), System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        address.setId(0);
        addressService.save(address);
        ResponseStatus successResponse = new ResponseStatus(HttpStatus.OK.value(),
                "Address added successfully", System.currentTimeMillis());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PutMapping("/addresses/{id}")
    public ResponseEntity<Object> updateAddress(@PathVariable int id, @Valid @RequestBody Address address, BindingResult result) {
        try{
            addressService.findById(id);
        } catch (Exception e) {
            ResponseStatus errorResponse = new ResponseStatus(HttpStatus.NOT_FOUND.value(),
                    "Address id (" + id + ") not found", System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        if (result.hasErrors()) {
            ResponseStatus errorResponse = new ResponseStatus(HttpStatus.BAD_REQUEST.value(), getErrorMessages(result), System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        address.setId(id);
        addressService.save(address);
        ResponseStatus successResponse = new ResponseStatus(HttpStatus.OK.value(),
                "Address updated successfully", System.currentTimeMillis());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Object> deleteAddress(@PathVariable int id) {
        try{
            addressService.findById(id);
        } catch (Exception e) {
            ResponseStatus errorResponse = new ResponseStatus(HttpStatus.NOT_FOUND.value(),
                    "Address id (" + id + ") not found", System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        addressService.deleteById(id);
        ResponseStatus successResponse = new ResponseStatus(HttpStatus.OK.value(),
                "Deleted address id - " + id, System.currentTimeMillis());
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
