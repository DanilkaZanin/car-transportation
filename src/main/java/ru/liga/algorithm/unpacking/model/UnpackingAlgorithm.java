package ru.liga.algorithm.unpacking.model;

import ru.liga.truck.model.Truck;

import java.util.List;

public interface UnpackingAlgorithm {
    void unpackParcels(List<Truck> trucks);
}