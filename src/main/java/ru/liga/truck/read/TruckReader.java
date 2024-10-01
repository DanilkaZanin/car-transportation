package ru.liga.truck.read;

import ru.liga.truck.model.Truck;

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
    List<Truck> read();
}