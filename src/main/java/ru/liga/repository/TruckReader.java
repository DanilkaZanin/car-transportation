package ru.liga.repository;

import ru.liga.model.Truck;

import java.io.File;
import java.util.List;

/**
 * Интерфейс для чтения данных о грузовиках.
 * Предоставляет метод для получения списка грузовиков.
 */
public interface TruckReader {

    /**
     * Читает данные о грузовиках и возвращает список объектов {@link Truck}.
     *
     * @return список объектов {@link Truck}.
     */
    List<Truck> read(File file);
}