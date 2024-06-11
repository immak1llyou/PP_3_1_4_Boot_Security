package ru.kata.spring.boot_security.demo.util;

public class PersonNotUpdatedException extends RuntimeException {
    public PersonNotUpdatedException(String message) {
        super(message);
    }
}
