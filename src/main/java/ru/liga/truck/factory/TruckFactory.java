package ru.liga.truck.factory;

import ru.liga.truck.model.Truck;

import java.util.List;

public interface TruckFactory {
    Truck createTruck();

    List<Truck> createTrucks(int count);
}