package ru.liga.truck.factory;

import ru.liga.truck.model.Truck;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс, представляющий фабрику для создания машин.
 */
public class TruckFactoryImpl implements TruckFactory {
    /**
     * Создает новую машину.
     *
     * @return новая машина
     */
    @Override
    public Truck createTruck() {
        return new Truck();
    }

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
                .collect(Collectors.toList());
    }
}