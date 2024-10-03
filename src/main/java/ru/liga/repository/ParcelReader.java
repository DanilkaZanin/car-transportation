package ru.liga.repository;

import ru.liga.model.Parcel;

import java.util.Map;

public interface ParcelReader {
    /**
     * Читает данные о посылках из CSV-файла и возвращает их в виде карты.
     *
     * @param filePathForParcels путь к CSV-файлу, содержащему данные о посылках.
     *                           Файл должен содержать информацию о каждой посылке, такую как её размеры, количество и другие параметры.
     * @return карта, где ключом является объект {@link Parcel}, представляющий посылку,
     * а значением — количество таких посылок.
     */
    Map<Parcel, Integer> readCsv(String filePathForParcels);
}