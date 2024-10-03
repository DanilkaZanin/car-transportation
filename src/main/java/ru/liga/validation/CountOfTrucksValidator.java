package ru.liga.validation;

import lombok.experimental.UtilityClass;

/**
 * Класс, проверяющий корректность количества машин.
 */
@UtilityClass
public class CountOfTrucksValidator {

    /**
     * Проверяет, валидно ли количество машин.
     *
     * @param count количество машин
     * @return true, если количество машин валидно, иначе false
     */
    public boolean isValid(int count) {
        return count >= 1;
    }
}