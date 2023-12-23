package sk.ukf.SpringApiProject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="adresa")
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @NotBlank(message = "City name cannot be blank")
    @Size(min = 2, max = 45, message = "City name must be between 2 and 45 characters")
    @Column(name="mesto")
    private String city;
    @NotBlank(message = "Street name cannot be blank")
    @Size(min = 2, max = 45, message = "Street name must be between 2 and 45 characters")
    @Column(name="ulica")
    private String street;
    @NotNull(message = "House number cannot be null")
    @Positive(message = "House number must be positive number")
    @Column(name="cislo_domu")
    private int houseNumber;
    @NotNull(message = "Postal code cannot be null")
    @Positive(message = "Postal code must be positive number")
    @Column(name="psc")
    private int postalCode;

    @JsonIgnore
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<Owner> courses = new ArrayList<>();

    public List<Owner> getCourses() {
        return courses;
    }

    public void setCourses(List<Owner> courses) {
        this.courses = courses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
}
