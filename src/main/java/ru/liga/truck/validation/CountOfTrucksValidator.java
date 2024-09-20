package ru.liga.truck.validation;

public class CountOfTrucksValidator {
    public static boolean isValid(int count) {
        return count >= 1;
    }
}