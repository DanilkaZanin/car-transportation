package ru.liga.algorithm.unpacking;

import ru.liga.truck.model.Truck;

import java.util.List;

public interface UnpackingAlgorithm {
    void unpackParcels(List<Truck> trucks);
}