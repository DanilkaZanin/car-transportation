package ru.liga.algorithm.unpacking.model;

import ru.liga.parcel.model.ParcelPatterns;
import ru.liga.truck.model.Truck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UnpackingAlgorithmImpl implements UnpackingAlgorithm {
    @Override
    public void unpackParcels(List<Truck> trucks) {
        for (Truck truck : trucks) {
            int[][] grid = truck.getGrid();
            Map<Integer, Integer> parcelCount = new HashMap<>();

            boolean[][] visited = new boolean[Truck.MAX_HEIGHT][Truck.MAX_WIDTH];

            for (int i = 0; i < Truck.MAX_HEIGHT; i++) {
                for (int j = 0; j < Truck.MAX_WIDTH; j++) {
                    int parcelType = grid[i][j];
                    if (parcelType > 0 && !visited[i][j]) {
                        if (tryToMatchPattern(grid, visited, i, j, parcelType)) {
                            parcelCount.put(parcelType, parcelCount.getOrDefault(parcelType, 0) + 1);
                        }
                    }
                }
            }
            printResults(truck, parcelCount);
        }
    }

    private boolean tryToMatchPattern(int[][] grid, boolean[][] visited, int row, int col, int parcelType) {
        Optional<int[][]> optionalPattern = ParcelPatterns.getShape(parcelType);

        if (optionalPattern.isEmpty()) {
            return false;
        }
        int[][] pattern = optionalPattern.get();

        int patternHeight = pattern.length;
        int patternWidth = pattern[0].length;

        if (row + patternHeight > Truck.MAX_HEIGHT || col + patternWidth > Truck.MAX_WIDTH) {
            return false;
        }

        for (int i = 0; i < patternHeight; i++) {
            for (int j = 0; j < patternWidth; j++) {
                if (grid[row + i][col + j] != parcelType || visited[row + i][col + j]) {
                    return false;
                }
            }
        }

        for (int i = 0; i < patternHeight; i++) {
            for (int j = 0; j < patternWidth; j++) {
                visited[row + i][col + j] = true;
            }
        }

        return true;
    }

    private void printResults(Truck truck, Map<Integer, Integer> parcelCount) {
        System.out.println("грузовик");
        System.out.println(truck);
        System.out.println("Количество посылок в грузовике:");
        for (Map.Entry<Integer, Integer> entry : parcelCount.entrySet()) {
            System.out.println("Посылок типа " + entry.getKey() + ": " + entry.getValue());
        }
    }
}