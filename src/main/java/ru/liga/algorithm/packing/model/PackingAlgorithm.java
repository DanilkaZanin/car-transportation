package ru.liga.algorithm.packing.model;

import ru.liga.truck.model.Truck;

import java.util.List;

public interface PackingAlgorithm {
    List<Truck> packageParcels();
}