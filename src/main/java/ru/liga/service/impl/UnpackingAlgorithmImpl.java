package ru.liga.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.liga.model.Truck;
import ru.liga.service.UnpackingAlgorithm;
import ru.liga.util.ParcelPatterns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class UnpackingAlgorithmImpl implements UnpackingAlgorithm {
    private static final int BASE_WIDTH_INDEX_FOR_PATTERN = 0;

    @Override
    public void unpackParcels(List<Truck> trucks) {
        log.info("Unpacking parcels");
        int numberOfTruck = 1;
        for (Truck truck : trucks) {
            Map<Integer, Integer> parcelCount = countParcelsInTruck(truck);
            printResults(truck, parcelCount, numberOfTruck);
            numberOfTruck++;
        }
    }

    private Map<Integer, Integer> countParcelsInTruck(Truck truck) {
        int[][] grid = truck.getGrid();
        Map<Integer, Integer> parcelCount = new HashMap<>();
        boolean[][] visited = new boolean[Truck.MAX_HEIGHT][Truck.MAX_WIDTH];

        for (int currentHeight = 0; currentHeight < Truck.MAX_HEIGHT; currentHeight++) {
            for (int currentWidth = 0; currentWidth < Truck.MAX_WIDTH; currentWidth++) {
                if (grid[currentHeight][currentWidth] > 0 && !visited[currentHeight][currentWidth]) {
                    processParcel(grid, visited, currentHeight, currentWidth, parcelCount);
                }
            }
        }
        return parcelCount;
    }

    private void processParcel(int[][] grid, boolean[][] visited, int currentHeight, int currentWidth, Map<Integer, Integer> parcelCount) {
        int parcelId = grid[currentHeight][currentWidth];
        if (tryToMatchPattern(grid, visited, currentHeight, currentWidth, parcelId)) {
            parcelCount.put(parcelId, parcelCount.getOrDefault(parcelId, 0) + 1);
        }
    }


    private boolean tryToMatchPattern(int[][] grid, boolean[][] visited, int row, int col, int parcelType) {
        return ParcelPatterns.getShape(parcelType)
                .map(pattern -> matchPattern(grid, visited, row, col, parcelType, pattern))
                .orElse(false);
    }

    private boolean matchPattern(int[][] grid, boolean[][] visited, int row, int col, int parcelType, int[][] pattern) {
        int patternHeight = pattern.length;
        int patternWidth = pattern[BASE_WIDTH_INDEX_FOR_PATTERN].length;

        if (!fitsWithinGrid(row, col, patternHeight, patternWidth)) {
            return false;
        }

        for (int currentHeight = 0; currentHeight < patternHeight; currentHeight++) {
            for (int currentWidth = 0; currentWidth < patternWidth; currentWidth++) {
                if (grid[row + currentHeight][col + currentWidth] != parcelType || visited[row + currentHeight][col + currentWidth]) {
                    return false;
                }
            }
        }

        for (int currentHeight = 0; currentHeight < patternHeight; currentHeight++) {
            for (int currentWidth = 0; currentWidth < patternWidth; currentWidth++) {
                visited[row + currentHeight][col + currentWidth] = true;
            }
        }
        return true;
    }

    private boolean fitsWithinGrid(int row, int col, int patternHeight, int patternWidth) {
        return row + patternHeight <= Truck.MAX_HEIGHT && col + patternWidth <= Truck.MAX_WIDTH;
    }

    private void printResults(Truck truck, Map<Integer, Integer> parcelCount, int numberOfTruck) {
        log.trace("Printing results of truck: {} ", truck);
        System.out.println("truck "  + numberOfTruck);
        System.out.println(truck);
        System.out.println("Number of parcels in the truck:");
        for (Map.Entry<Integer, Integer> entry : parcelCount.entrySet()) {
            System.out.println("Parcels of type " + entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("--------------");
    }
}