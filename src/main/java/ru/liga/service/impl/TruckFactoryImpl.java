package ru.liga.service.impl;

import ru.liga.model.Truck;
import ru.liga.service.TruckFactory;

import java.util.List;
import java.util.stream.Stream;

/**
 * Класс, представляющий фабрику для создания машин.
 */
public class TruckFactoryImpl implements TruckFactory {

    /**
     * Создает список машин.
     *
     * @param count количество машин для создания
     * @return список машин
     */
    @Override
    public List<Truck> createTrucks(int count) {
        return Stream.generate(Truck::new)
                .limit(count)
                .toList();
    }
}