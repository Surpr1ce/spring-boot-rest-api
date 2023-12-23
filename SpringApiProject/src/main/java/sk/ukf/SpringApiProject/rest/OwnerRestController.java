package sk.ukf.SpringApiProject.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import sk.ukf.SpringApiProject.entity.Owner;
import sk.ukf.SpringApiProject.entity.ResponseStatus;
import sk.ukf.SpringApiProject.service.OwnerService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OwnerRestController {
    @Autowired
    private OwnerService ownerService;

    @GetMapping("/owners")
    public List<Owner> findAll() {
        return ownerService.findAll();
    }

    @GetMapping("/owners/{id}")
    public ResponseEntity<Object> getOwner(@PathVariable int id) {
        Owner owner;
        try{
            owner = ownerService.findById(id);
        } catch (Exception e) {
            ResponseStatus errorResponse = new ResponseStatus(HttpStatus.NOT_FOUND.value(),
                    "Owner id (" + id + ") not found", System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(owner, HttpStatus.OK);
    }

    @PostMapping("/owners")
    public ResponseEntity<Object> addOwner(@Valid @RequestBody Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            ResponseStatus errorResponse = new ResponseStatus(HttpStatus.BAD_REQUEST.value(), getErrorMessages(result), System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        owner.setId(0);
        ownerService.save(owner);
        ResponseStatus successResponse = new ResponseStatus(HttpStatus.OK.value(),
                "Owner added successfully", System.currentTimeMillis());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PutMapping("/owners/{id}")
    public ResponseEntity<Object> updateOwner(@PathVariable int id, @Valid @RequestBody Owner owner, BindingResult result) {
        try{
            ownerService.findById(id);
        } catch (Exception e) {
            ResponseStatus errorResponse = new ResponseStatus(HttpStatus.NOT_FOUND.value(),
                    "Owner id (" + id + ") not found", System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        if (result.hasErrors()) {
            ResponseStatus errorResponse = new ResponseStatus(HttpStatus.BAD_REQUEST.value(), getErrorMessages(result), System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        owner.setId(id);
        ownerService.save(owner);
        ResponseStatus successResponse = new ResponseStatus(HttpStatus.OK.value(),
                "Owner updated successfully", System.currentTimeMillis());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/owners/{id}")
    public ResponseEntity<Object> deleteOwner(@PathVariable int id) {
        try{
            ownerService.findById(id);
        } catch (Exception e) {
            ResponseStatus errorResponse = new ResponseStatus(HttpStatus.NOT_FOUND.value(),
                    "Owner id (" + id + ") not found", System.currentTimeMillis());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        ownerService.deleteById(id);
        ResponseStatus successResponse = new ResponseStatus(HttpStatus.OK.value(),
                "Deleted owner id - " + id, System.currentTimeMillis());
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
