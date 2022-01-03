package com.softserve.itacademy.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "users")
public class            User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email
    @Column(nullable = false)
    String email;

    @NotBlank(message = "The firstName cannot be empty")
    @Column(name = "first_name", nullable = false)
    @Pattern(regexp = "[A-Z][a-z]+-[A-Z][a-z]+")
    String firstName;

    @NotBlank(message = "The last_name cannot be empty")
    @Column(name = "last_name", nullable = false)
    @Pattern(regexp = "[A-Z][a-z]+-[A-Z][a-z]+")
    String lastName;

    @NotBlank(message = "The password cannot be empty")
    @Column(nullable = false)
    String password;

    @NotNull
    @ManyToOne
    private Role role;

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
