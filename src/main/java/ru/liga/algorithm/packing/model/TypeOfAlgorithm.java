package ru.liga.algorithm.packing.model;

import ru.liga.algorithm.packing.types.ComplexPackingAlgorithm;
import ru.liga.algorithm.packing.types.SimplePackingAlgorithm;
import ru.liga.parcel.model.Parcel;
import ru.liga.truck.model.Truck;

import java.util.List;
import java.util.Map;

public enum TypeOfAlgorithm {
    SIMPLE {
        @Override
        public PackingAlgorithm createAlgorithm(Map<Parcel, Integer> parcels, List<Truck> trucks) {
            return new SimplePackingAlgorithm(parcels, trucks);
        }
    },
    COMPLEX {
        @Override
        public PackingAlgorithm createAlgorithm(Map<Parcel, Integer> parcels, List<Truck> trucks) {
            return new ComplexPackingAlgorithm(parcels, trucks);
        }
    },
    EVENLY {
        @Override
        public PackingAlgorithm createAlgorithm(Map<Parcel, Integer> parcels, List<Truck> trucks) {
            return null;
        }
    };

    public abstract PackingAlgorithm createAlgorithm(Map<Parcel, Integer> parcels, List<Truck> trucks);
}