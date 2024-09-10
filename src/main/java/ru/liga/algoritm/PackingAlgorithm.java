package ru.liga.algoritm;

import lombok.Getter;
import ru.liga.model.Parcel;
import ru.liga.model.Truck;
import ru.liga.model.TypeOfAlgorithm;

import java.util.*;

public class PackingAlgorithm {
    private Map<Parcel, Integer> parcels;
    @Getter
    private List<Truck> trucks;

    public PackingAlgorithm(Map<Parcel, Integer> parcels, TypeOfAlgorithm type) {
        this.parcels = parcels;
        this.trucks = new ArrayList<>();
        switch (type) {
            case SIMPLE -> simpleAlgorithm();
            case COMPLEX -> complexAlgorithm();
        }
    }

    private void simpleAlgorithm() {
        Truck truck = new Truck();
        int[][] grid = truck.getGrid();
        do {
            Parcel parcel = getParcel(Truck.MAX_HEIGHT - 1, Truck.MAX_WIDTH - 1);
            setParcelIntoGreed(grid, parcel, Truck.MAX_HEIGHT - 1, 0);
            trucks.add(truck);
            truck = new Truck();
            grid = truck.getGrid();
        } while (!parcels.isEmpty());
    }

    private void complexAlgorithm() {
        Truck truck = new Truck();
        trucks.add(truck);
        int[][] grid = truck.getGrid();
        while (true) {
            for (int i = grid.length - 1; i >= 0; i--) {
                int j = 0;
                for (; j < grid.length; j++) {
                    if (grid[i][j] == 0) {
                        Map.Entry<Integer, Integer> emptyPlace = findEmptyPlace(grid, i, j);
                        Parcel parcel = getParcel(emptyPlace.getKey(), emptyPlace.getValue());
                        if (parcel != null) {
                            setParcelIntoGreed(grid, parcel, i, j);
                            j += parcel.getWidth() - 1;
                            if (j >= 5) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
                if (i == 0 && j == 5) {
                    truck = new Truck();
                    trucks.add(truck);
                    grid = truck.getGrid();
                }
            }
            if (parcels.isEmpty()) {
                break;
            }
        }
    }

    private Map.Entry<Integer, Integer> findEmptyPlace(int[][] grid, int height, int width) {
        int maxEmptyHeight = 0;
        int maxEmptyWidth = 0;
        for (int i = 0; i <= height; i++) {
            int emptyWidth = 0;
            for (int j = 0; j < grid.length - width; j++) {
                if (height - i >= 0 && width + j <= grid.length) {
                    if (grid[height - i][width + j] == 0) {
                        emptyWidth++;
                    }
                }
            }
            if (emptyWidth > maxEmptyWidth) {
                maxEmptyWidth = emptyWidth;
            }
            maxEmptyHeight++;
        }
        return Map.entry(maxEmptyHeight, maxEmptyWidth);
    }

    private void setParcelIntoGreed(int[][] grid, Parcel parcel, int startHeight, int startWidth) {
        int height = parcel.getHeight();
        for (int i = 0; i < parcel.getHeight(); i++) {
            int width = parcel.getLineLength(height - 1);
            for (int j = 0; j < width; j++) {
                grid[startHeight - i][startWidth + j] = parcel.getDigit();
            }
            height--;
        }
    }

    private Parcel getParcel(int height, int width) {
        Comparator<Parcel> byHeightAndWidth = Comparator
                .comparingInt(Parcel::getWidth)
                .thenComparingInt(Parcel::getHeight)
                .reversed();

        Optional<Parcel> parcel = parcels.keySet().stream()
                .sorted(byHeightAndWidth)
                .filter(p -> p.getWidth() <= width && p.getHeight() <= height)
                .findFirst();
        if (parcel.isPresent()) {
            removeParcel(parcel.get());
            return parcel.get();
        } else {
            return null;
        }
    }

    private void removeParcel(Parcel parcel) {
        if (parcels.get(parcel) == 1) {
            parcels.remove(parcel);
        } else {
            parcels.put(parcel, parcels.get(parcel) - 1);
        }
    }
}