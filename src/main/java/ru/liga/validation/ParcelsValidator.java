package ru.liga.validation;

import lombok.RequiredArgsConstructor;
import ru.liga.model.Parcel;

import java.util.Map;

@RequiredArgsConstructor
public class ParcelsValidator {
    private final Map<Integer, Parcel> possibleParcels;

    public boolean isValid(Parcel parcel) {
        if (possibleParcels.containsKey(parcel.getDigit())) {
            if (possibleParcels.get(parcel.getDigit()).equals(parcel)) {
                return true;
            }
        }
        System.err.println(parcel);
        return false;
    }
}