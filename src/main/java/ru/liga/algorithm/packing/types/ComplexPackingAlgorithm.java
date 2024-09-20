package ru.liga.algorithm.packing.types;

import lombok.extern.slf4j.Slf4j;
import ru.liga.algorithm.packing.model.PackingAlgorithmImpl;
import ru.liga.parcel.model.Parcel;
import ru.liga.truck.model.Truck;

import java.util.*;

@Slf4j
public class ComplexPackingAlgorithm extends PackingAlgorithmImpl {
    public ComplexPackingAlgorithm(Map<Parcel, Integer> parcels, List<Truck> trucks) {
        super(parcels, trucks);
        log.info("Using COMPLEX packing algorithm");
    }

    @Override
    public List<Truck> packageParcels() {
        log.info("Packing parcels");
        for (Truck truck : trucks) {
            int[][] grid = truck.getGrid();
            for (int i = grid.length - 1; i >= 0; i--) {
                int j = 0;
                for (; j < grid.length; j++) {
                    if (grid[i][j] == 0) {
                        Map.Entry<Integer, Integer> emptyPlace = findEmptyPlace(grid, i, j);
                        Optional<Parcel> parcel = getParcel(emptyPlace.getKey(), emptyPlace.getValue());
                        if (parcel.isPresent()) {
                            setParcelIntoGrid(grid, parcel.get(), i, j);
                            j += parcel.get().getWidth() - 1;
                            if (j >= 5) {
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