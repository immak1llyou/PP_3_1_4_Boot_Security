package ru.kata.spring.boot_security.demo.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(min = 2, max = 100, message = "Никнейм должен быть от 2 до 100 символов длинной")
    @Column(name = "username", unique = true)
    private String userName;

    @NotEmpty
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длинной")
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Size(min = 2, max = 100, message = "Фамилия должна быть от 2 до 100 символов длинной")
    @Column(name = "surname")
    private String surname;

    @NotEmpty
    @Email
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Min(value = 1900, message = "Год рождения должен быть больше чем 1900")
    @Max(value = 2024, message = "Год рождения должен быть меньше чем 2024")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> role;

    public User() {
    }

    public User(String userName, int yearOfBirth, List<Role> role, String password, String name, String surname, String email) {
        this.userName = userName;
        this.yearOfBirth = yearOfBirth;
        this.role = role;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", yearOfBirth=" + yearOfBirth +
                ", role=" + role +
                '}';
    }
}

