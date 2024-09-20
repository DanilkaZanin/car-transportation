package ru.liga.parcel.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcel.model.Parcel;
import ru.liga.parcel.model.ParcelPatterns;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class ParcelsValidator {
    public boolean isValid(Parcel parcel) {
        log.trace("validating parcel {}", parcel);
        return ParcelPatterns.getShape(parcel.getDigit())
                .map(shape -> Arrays.deepEquals(shape, parcel.getShape()))
                .orElse(false);
    }
}