package ru.kata.spring.boot_security.demo.util;

public class PersonNotDeleteException extends RuntimeException {
    public PersonNotDeleteException(String message) {
        super(message);
    }
}
