package ru.liga.algorithm.packing.types;

import lombok.extern.slf4j.Slf4j;
import ru.liga.algorithm.packing.model.PackingAlgorithmImpl;
import ru.liga.parcel.model.Parcel;
import ru.liga.truck.model.Truck;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class SimplePackingAlgorithm extends PackingAlgorithmImpl {
    private static final int TRUCK_MAX_HEIGHT_INDEX = Truck.MAX_HEIGHT - 1;
    private static final int TRUCK_MAX_WIDTH_INDEX = Truck.MAX_WIDTH - 1;
    private static final int TRUCK_START_WIDTH_INDEX = 0;

    @Override
    public List<Truck> packParcelsIntoTrucks(Map<Parcel, Integer> parcels, List<Truck> trucks) {
        log.info("Packing parcels");
        if (countOfTrucksLessThenCountOfParcels(parcels, trucks)) {
            log.warn("Not enough trucks to simple packing algorithm");
            throw new RuntimeException("Не удастся упаковать все посылки");
        }

        for (Truck truck : trucks) {
            Optional<Parcel> parcel = getParcel(TRUCK_MAX_HEIGHT_INDEX, TRUCK_MAX_WIDTH_INDEX, parcels);
            parcel.ifPresent(p -> setParcelIntoGrid(
                    truck.getGrid(),
                    p,
                    TRUCK_MAX_HEIGHT_INDEX,
                    TRUCK_START_WIDTH_INDEX));
        }
        return trucks;
    }

    private boolean countOfTrucksLessThenCountOfParcels(Map<Parcel, Integer> parcels, List<Truck> trucks) {
        return trucks.size() < getCountOfParcels(parcels);
    }

    private int getCountOfParcels(Map<Parcel, Integer> parcels) {
        return parcels.values().stream().mapToInt(Integer::intValue).sum();
    }
}