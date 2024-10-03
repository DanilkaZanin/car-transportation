package ru.liga.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.exception.PackingParcelsException;
import ru.liga.model.Parcel;
import ru.liga.model.Truck;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Реализация алгоритма упаковки с равномерным распределением.
 */
@Slf4j
public class EvenlyPackingAlgorithm extends PackingAlgorithmImpl {
    private static final int FREE_ZONE_VALUE = 0;
    private static final int CONVERSION_INDEX = 1;

    /**
     * Упаковывает предоставленные посылки в список машин.
     *
     * @param parcels карта, где ключ — посылка, а значение — количество таких посылок
     * @param trucks  список машин для упаковки посылок
     * @return список машин с упакованными посылками
     */
    @Override
    public List<Truck> packParcelsIntoTrucks(Map<Parcel, Integer> parcels, List<Truck> trucks) {
        log.info("Packing Parcels");
        while (!parcels.isEmpty()) {
            Truck truck = takeLessLoadedTruck(trucks);
            if (!placeParcelInTruck(truck, parcels)) {
                log.warn("Cannot place any more parcels in the truck {}", truck.hashCode());
                throw new PackingParcelsException("Failed to pack all parcels");
            }
        }
        return trucks;
    }

    private boolean placeParcelInTruck(Truck truck, Map<Parcel, Integer> parcels) {
        int[][] grid = truck.getGrid();
        for (int currentHeight = Truck.MAX_HEIGHT - CONVERSION_INDEX; currentHeight >= 0; currentHeight--) {
            if (placeParcelInRow(truck, grid, currentHeight, parcels)) {
                return true;
            }
        }
        return false;
    }

    private boolean placeParcelInRow(Truck truck, int[][] grid, int currentHeight, Map<Parcel, Integer> parcels) {
        for (int currentWidth = 0; currentWidth < Truck.MAX_WIDTH; currentWidth++) {
            if (grid[currentHeight][currentWidth] == FREE_ZONE_VALUE) {
                Map.Entry<Integer, Integer> emptyPlace = findEmptyPlace(grid, currentHeight, currentWidth);
                if (tryPlaceParcel(truck, currentHeight, currentWidth, emptyPlace, parcels)) {
                    return true;
                }
            }
        }
        return false;
    }


    private boolean tryPlaceParcel(Truck truck, int currentHeight,
                                   int currentWidth,
                                   Map.Entry<Integer, Integer> emptyPlace,
                                   Map<Parcel, Integer> parcels) {
        return getParcel(emptyPlace.getKey(), emptyPlace.getValue(), parcels)
                .map(parcel -> {
                    setParcelIntoGrid(truck.getGrid(), parcel, currentHeight, currentWidth);
                    truck.getCountOfParcels().plusOne();
                    return true;
                })
                .orElse(false);
    }

    private Truck takeLessLoadedTruck(List<Truck> trucks) {
        return trucks.stream()
                .min(Comparator.comparing(truck -> truck.getCountOfParcels().getCount()))
                .orElseThrow(NullPointerException::new);
    }
}