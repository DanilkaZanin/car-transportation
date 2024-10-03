package ru.liga.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.exception.PackingParcelsException;
import ru.liga.model.Parcel;
import ru.liga.model.Truck;

import java.util.List;
import java.util.Map;

/**
 * Реализация простого алгоритма упаковки.
 */
@Slf4j
public class SimplePackingAlgorithm extends PackingAlgorithmImpl {
    private static final int TRUCK_MAX_HEIGHT_INDEX = Truck.MAX_HEIGHT - 1;
    private static final int TRUCK_MAX_WIDTH_INDEX = Truck.MAX_WIDTH - 1;
    private static final int TRUCK_START_WIDTH_INDEX = 0;

    /**
     * Упаковывает предоставленные посылки в список машин.
     *
     * @param parcels карта, где ключ — посылка, а значение — количество таких посылок
     * @param trucks  список машин для упаковки посылок
     * @return список машин с упакованными посылками
     */
    @Override
    public List<Truck> packParcelsIntoTrucks(Map<Parcel, Integer> parcels, List<Truck> trucks) {
        log.info("Packing parcels");
        if (countOfTrucksLessThenCountOfParcels(parcels, trucks)) {
            log.warn("Not enough trucks to simple packing algorithm");
            throw new PackingParcelsException("Unable to pack all parcels.");
        }

        for (Truck truck : trucks) {
            getParcel(TRUCK_MAX_HEIGHT_INDEX, TRUCK_MAX_WIDTH_INDEX, parcels)
                    .ifPresent(parcel -> setParcelIntoGrid(
                            truck.getGrid(),
                            parcel,
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