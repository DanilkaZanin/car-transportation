package ru.liga.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.model.Parcel;
import ru.liga.util.ParcelPatterns;

import java.util.Arrays;

/**
 * Класс для валидации посылок.
 * Проверяет корректность посылки, сравнивая её форму с предопределёнными шаблонами.
 */
@Slf4j
@RequiredArgsConstructor
public class ParcelsValidator {

    /**
     * Проверяет валидность посылки, сравнивая её форму с известными шаблонами.
     *
     * @param parcel объект {@link Parcel}, который нужно проверить.
     * @return true, если посылка валидна, иначе false.
     */
    public boolean isValid(Parcel parcel) {
        log.trace("validating parcel {}", parcel);
        return ParcelPatterns.getShape(parcel.getDigit())
                .map(shape -> Arrays.deepEquals(shape, parcel.getShape()))
                .orElse(false);
    }
}