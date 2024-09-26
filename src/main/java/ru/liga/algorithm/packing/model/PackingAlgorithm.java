package ru.liga.algorithm.packing.model;

import ru.liga.parcel.model.Parcel;
import ru.liga.truck.model.Truck;

import java.util.List;
import java.util.Map;

public interface PackingAlgorithm {
    List<Truck> packParcelsIntoTrucks(Map<Parcel, Integer> parcels, List<Truck> trucks);
}