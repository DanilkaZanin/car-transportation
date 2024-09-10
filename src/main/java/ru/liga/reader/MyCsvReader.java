package ru.liga.reader;

import lombok.NoArgsConstructor;
import ru.liga.model.Parcel;
import ru.liga.validation.ParcelsValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@NoArgsConstructor
public class MyCsvReader {
    private ParcelsValidator parcelsValidator;

    public Map<Parcel, Integer> readCSV(String filePathForPossibleParcels, String filePathForParcels) {
        parcelsValidator = new ParcelsValidator(getPossibleParcels(filePathForPossibleParcels));
        return getParcels(filePathForParcels);
    }

    private Map<Parcel, Integer> getParcels(String filePath) {
        Map<Parcel, Integer> parcels = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String nextLine;
            List<String> shape = new ArrayList<>();

            while ((nextLine = br.readLine()) != null) {
                nextLine = nextLine.trim();
                if (!nextLine.isEmpty()) {
                    shape.add(nextLine);
                } else if (!shape.isEmpty()) {
                    Parcel parcel = new Parcel(shape);

                    if (parcelsValidator.isValid(parcel)) {
                        parcels.merge(parcel, 1, Integer::sum);
                    }
                    shape.clear();
                }
            }
            if (!shape.isEmpty()) {
                Parcel parcel = new Parcel(shape);
                if (parcelsValidator.isValid(parcel)) {
                    parcels.merge(parcel, 1, Integer::sum);
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return parcels;
    }

    private Map<Integer, Parcel> getPossibleParcels(String filePath) {
        Map<Integer, Parcel> parcels = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            Parcel parcel;
            List<String> shape = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    shape.add(line);
                } else if (!shape.isEmpty()) {
                    parcel = new Parcel(shape);
                    parcels.put(parcel.getDigit(), parcel);
                    shape.clear();
                }
            }
            if (!shape.isEmpty()) {
                parcel = new Parcel(shape);
                parcels.put(parcel.getDigit(), parcel);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return parcels;
    }
}