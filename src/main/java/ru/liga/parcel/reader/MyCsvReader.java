package ru.liga.parcel.reader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcel.model.Parcel;
import ru.liga.parcel.validation.ParcelsValidator;

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
public class MyCsvReader {
    private final ParcelsValidator parcelsValidator;

    /**
     * Читает CSV-файл с посылками, валидирует данные и возвращает их в виде карты,
     * где ключом является объект {@link Parcel}, а значением — количество таких посылок.
     *
     * @param filePathForParcels путь к файлу CSV с посылками.
     * @return карта с посылками и их количеством.
     */
    public Map<Parcel, Integer> readCSV(String filePathForParcels) {
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
            throw new RuntimeException(ex);
        }
        return parcels;
    }
}