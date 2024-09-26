package ru.liga.truck.write;

import ru.liga.truck.model.Truck;

import java.io.File;
import java.util.List;

public interface TruckWriter {
    List<Truck> write(List<Truck> trucks, File file);
}