package sk.ukf.SpringApiProject.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name="auto")
public class Car {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @NotBlank(message = "Vehicle registration number cannot be blank")
    @Size(min = 7, max = 7, message = "Vehicle registration number must have 7 characters")
    @Column(name="ECV")
    private String ecv;
    @NotBlank(message = "Car brand cannot be blank")
    @Size(min = 2, max = 20, message = "Car brand must be between 2 and 20 characters")
    @Column(name="znacka")
    private String carBrand;
    @NotNull(message = "Driven km cannot be null")
    @Positive(message = "Driven km must be positive number")
    @Column(name = "najazdene_km")
    private int drivenKm;
    @NotBlank(message = "Year of production cannot be blank")
    @Column(name = "rok_vyroby")
    private String yearOfProd;
    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive number")
    @Column(name = "cena")
    private int price;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "majitelia_has_auto",
            joinColumns = @JoinColumn(name = "Auto_id"),
            inverseJoinColumns = @JoinColumn(name = "Majitelia_id"))
    @JsonIgnoreProperties("cars")
    private List<Owner> owners;

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEcv() {
        return ecv;
    }

    public void setEcv(String ecv) {
        this.ecv = ecv;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public int getDrivenKm() {
        return drivenKm;
    }

    public void setDrivenKm(int drivenKm) {
        this.drivenKm = drivenKm;
    }

    public String getYearOfProd() {
        return yearOfProd;
    }

    public void setYearOfProd(String yearOfProd) {
        this.yearOfProd = yearOfProd;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
