package ru.liga.repository;

import ru.liga.model.Truck;

import java.io.File;
import java.util.List;

/**
 * Интерфейс для записи данных о грузовиках в файл.
 * Предоставляет метод для записи списка грузовиков в файл.
 */
public interface TruckWriter {

    /**
     * Записывает список грузовиков в указанный файл.
     *
     * @param trucks список объектов {@link Truck} для записи.
     * @param file   файл, в который будет записана информация.
     * @return список записанных объектов {@link Truck}.
     */
    List<Truck> write(List<Truck> trucks, File file);
}