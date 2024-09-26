package ru.liga.truck.factory;

import ru.liga.truck.model.Truck;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TruckFactoryImpl implements TruckFactory {
    @Override
    public Truck createTruck() {
        return new Truck();
    }

    @Override
    public List<Truck> createTrucks(int count) {
        return Stream.generate(Truck::new)
                .limit(count)
                .collect(Collectors.toList());
    }
}
