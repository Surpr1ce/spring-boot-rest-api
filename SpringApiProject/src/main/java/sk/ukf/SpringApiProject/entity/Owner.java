package sk.ukf.SpringApiProject.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name="majitelia")
public class Owner {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 64, message = "First name must be between 2 and 64 characters")
    @Column(name="meno")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 64, message = "Last name must be between 2 and 64 characters")
    @Column(name="priezvisko")
    private String lastName;
    @NotBlank(message = "Email cannot be blank")
    @Size(max = 255, message = "The email must have a maximum of 255 characters")
    @Email(message = "Invalid email address")
    @Column(name="email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "adresa_id")
    private Address address;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "majitelia_has_auto",
            joinColumns = @JoinColumn(name = "Majitelia_id"),
            inverseJoinColumns = @JoinColumn(name = "Auto_id"))
    @JsonIgnoreProperties("owners")
    private List<Car> cars;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
