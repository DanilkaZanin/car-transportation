package ru.liga.truck.factory;

import ru.liga.truck.model.Truck;

import java.util.List;

/**
 * Фабричный интерфейс для создания объектов {@link Truck}.
 * Предоставляет методы для создания одного грузовика или списка грузовиков.
 */
public interface TruckFactory {

    /**
     * Создает новый объект {@link Truck}.
     *
     * @return созданный объект {@link Truck}.
     */
    Truck createTruck();

    /**
     * Создает список объектов {@link Truck} в количестве, указанном в параметре.
     *
     * @param count количество создаваемых объектов {@link Truck}.
     * @return список созданных объектов {@link Truck}.
     */
    List<Truck> createTrucks(int count);
}