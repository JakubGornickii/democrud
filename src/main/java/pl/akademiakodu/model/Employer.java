package pl.akademiakodu.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Employer {
    @Id
    @GeneratedValue
    @Column
    private int id;
    @NotEmpty(message = "*Proszę wpisać imię")
    @Column
    private String name;
    @NotEmpty(message = "*Proszę wpisać nazwisko")
    @Column
    private String surName;
    @Email(message = "*Proszę wpisać poprawny email")
    @NotEmpty(message = "*Proszę wpisać email")
    @Column
    private String email;
    @Length(min = 11, message = "*Za mało znaków")
    @NotEmpty(message = "*Proszę wpisać pesel")
    @Column
    private String pesel;
    @NotEmpty(message = "*Proszę wpisać stanowisko")
    @Column
    private String position;
    @NotEmpty(message = "*Proszę wpisać wypłatę")
    @Column
    private String salary;

    public Employer() {
    }

    ;

    public Employer(String name, String surName, String email, String pesel, String position, String salary) {
        this.name = name;
        this.surName = surName;
        this.email = email;
        this.pesel = pesel;
        this.position = position;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surname) {
        surName = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
