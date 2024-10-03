package ru.liga.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.exception.ReadingFileException;
import ru.liga.model.Parcel;
import ru.liga.repository.ParcelReader;
import ru.liga.validation.ParcelsValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для чтения CSV-файлов с посылками.
 * Содержит методы для чтения файла, валидации данных и возврата посылок с их количеством.
 */
@Slf4j
@RequiredArgsConstructor
public class ParcelCsvReader implements ParcelReader {
    private final ParcelsValidator parcelsValidator;

    /**
     * Читает CSV-файл с посылками, валидирует данные и возвращает их в виде карты,
     * где ключом является объект {@link Parcel}, а значением — количество таких посылок.
     *
     * @param filePathForParcels путь к файлу CSV с посылками.
     * @return карта с посылками и их количеством.
     */
    @Override
    public Map<Parcel, Integer> readCsv(String filePathForParcels) {
        log.info("Start reading CSV file");
        return getParcels(filePathForParcels);
    }

    private Map<Parcel, Integer> getParcels(String filePath) {
        log.debug("Reading csv file with parcels{}", filePath);
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
            throw new ReadingFileException("Failed to read file " + filePath);
        }
        return parcels;
    }
}