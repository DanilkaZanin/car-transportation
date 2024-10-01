package ru.liga.truck.validation;

/**
 * Класс, проверяющий корректность количества машин.
 */
public class CountOfTrucksValidator {
    /**
     * Проверяет, валидно ли количество машин.
     *
     * @param count количество машин
     * @return true, если количество машин валидно, иначе false
     */
    public static boolean isValid(int count) {
        return count >= 1;
    }
}