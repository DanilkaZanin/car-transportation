package ru.liga.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.model.Parcel;
import ru.liga.model.Truck;

import java.util.List;
import java.util.Map;

/**
 * Реализация сложного алгоритма упаковки.
 * Алгоритм будет пытаться задействовать минимальное количество грузовиков для упаковки посылок
 */
@Slf4j
public class ComplexPackingAlgorithm extends PackingAlgorithmImpl {
    private static final int CONVERSION_INDEX = 1;
    private static final int FREE_ZONE_VALUE = 0;

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
        for (Truck truck : trucks) {
            packParcelsInTruck(parcels, truck);
            if (parcels.isEmpty()) {
                break;
            }
        }
        return trucks;
    }

    private void packParcelsInTruck(Map<Parcel, Integer> parcels, Truck truck) {
        int[][] grid = truck.getGrid();
        for (int currentHeight = grid.length - CONVERSION_INDEX; currentHeight >= 0; currentHeight--) {
            packParcelsInRow(parcels, grid, currentHeight);
            if (parcels.isEmpty()) {
                break;
            }
        }
    }

    private void packParcelsInRow(Map<Parcel, Integer> parcels, int[][] grid, int currentHeight) {
        for (int currentWidth = 0; currentWidth < grid.length; currentWidth++) {
            if (grid[currentHeight][currentWidth] == FREE_ZONE_VALUE) {
                placeParcelIfPossible(parcels, grid, currentHeight, currentWidth);
            }
        }
    }

    private void placeParcelIfPossible(Map<Parcel, Integer> parcels, int[][] grid, int currentHeight, int currentWidth) {
        Map.Entry<Integer, Integer> emptyPlace = findEmptyPlace(grid, currentHeight, currentWidth);
        getParcel(emptyPlace.getKey(), emptyPlace.getValue(), parcels)
                .ifPresent(parcel -> setParcelIntoGrid(grid, parcel, currentHeight, currentWidth));
    }
}