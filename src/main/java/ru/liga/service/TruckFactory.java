package ru.liga.service;

import ru.liga.model.Truck;

import java.util.List;

/**
 * Фабричный интерфейс для создания объектов {@link Truck}.
 * Предоставляет методы для создания одного грузовика или списка грузовиков.
 */
public interface TruckFactory {

    /**
     * Создает список объектов {@link Truck} в количестве, указанном в параметре.
     *
     * @param count количество создаваемых объектов {@link Truck}.
     * @return список созданных объектов {@link Truck}.
     */
    List<Truck> createTrucks(int count);
}