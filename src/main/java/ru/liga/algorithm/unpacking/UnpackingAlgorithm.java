package ru.liga.algorithm.unpacking;

import ru.liga.truck.model.Truck;

import java.util.List;

/**
 * Определяет интерфейс для алгоритмов распаковки посылок из машин.
 */
public interface UnpackingAlgorithm {
    /**
     * Распаковывает посылки из списка машин.
     *
     * @param trucks список машин для распаковки посылок
     */
    void unpackParcels(List<Truck> trucks);
}