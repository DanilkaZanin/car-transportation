package ru.liga.algorithm.packing.model;

import ru.liga.parcel.model.Parcel;
import ru.liga.truck.model.Truck;

import java.util.List;
import java.util.Map;

/**
 * Определяет интерфейс для алгоритмов упаковки посылок в машины.
 */
public interface PackingAlgorithm {
    /**
     * Упаковывает предоставленные посылки в список машин.
     *
     * @param parcels карта, где ключ — посылка, а значение — количество таких посылок
     * @param trucks список машин для упаковки посылок
     * @return список машин с упакованными посылками
     */
    List<Truck> packParcelsIntoTrucks(Map<Parcel, Integer> parcels, List<Truck> trucks);
}