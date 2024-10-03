package ru.liga.exception;

public class InvalidTrucksNumberException extends RuntimeException {
    public InvalidTrucksNumberException(String message) {
        super(message);
    }
}