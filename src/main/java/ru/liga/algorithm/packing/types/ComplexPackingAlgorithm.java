package ru.liga.algorithm.packing.types;

import lombok.extern.slf4j.Slf4j;
import ru.liga.algorithm.packing.model.PackingAlgorithmImpl;
import ru.liga.parcel.model.Parcel;
import ru.liga.truck.model.Truck;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Реализация сложного алгоритма упаковки.
 */
@Slf4j
public class ComplexPackingAlgorithm extends PackingAlgorithmImpl {
    private static final int CONVERSION_INDEX = 1;
    private static final int FREE_ZONE_VALUE = 0;

    /**
     * Упаковывает предоставленные посылки в список машин.
     *
     * @param parcels карта, где ключ — посылка, а значение — количество таких посылок
     * @param trucks список машин для упаковки посылок
     * @return список машин с упакованными посылками
     */
    @Override
    public List<Truck> packParcelsIntoTrucks(Map<Parcel, Integer> parcels, List<Truck> trucks) {
        log.info("Packing parcels");
        for (Truck truck : trucks) {
            int[][] grid = truck.getGrid();
            for (int currentHeight = grid.length - CONVERSION_INDEX; currentHeight >= 0; currentHeight--) {
                int currentWidth = 0;
                for (; currentWidth < grid.length; currentWidth++) {
                    if (grid[currentHeight][currentWidth] == FREE_ZONE_VALUE) {
                        Map.Entry<Integer, Integer> emptyPlace = findEmptyPlace(grid, currentHeight, currentWidth);
                        Optional<Parcel> parcel = getParcel(emptyPlace.getKey(), emptyPlace.getValue(), parcels);
                        if (parcel.isPresent()) {
                            setParcelIntoGrid(grid, parcel.get(), currentHeight, currentWidth);
                            currentWidth += parcel.get().getWidth() - CONVERSION_INDEX;
                            if (currentWidth >= Truck.MAX_HEIGHT - CONVERSION_INDEX) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
            if (parcels.isEmpty()) {
                break;
            }
        }
        return trucks;
    }
}