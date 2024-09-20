package ru.liga.algorithm.packing.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.truck.model.Truck;
import ru.liga.parcel.model.Parcel;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public abstract class PackingAlgorithmImpl implements PackingAlgorithm {
    protected final Map<Parcel, Integer> parcels;
    protected final List<Truck> trucks;

    protected Optional<Parcel> getParcel(int height, int width) {
        log.trace("truing to get parcel with height {}, width {}", height, width);
        Comparator<Parcel> byHeightAndWidth = Comparator
                .comparingInt(Parcel::getHeight).reversed()
                .thenComparingInt(Parcel::getWidth);
        Optional<Parcel> parcel = parcels.keySet().stream()
                .sorted(byHeightAndWidth)
                .filter(p -> p.getHeight() <= height && p.getWidth() <= width)
                .findFirst();

        parcel.ifPresent(this::removeParcel);
        return parcel;
    }

    private void removeParcel(Parcel parcel) {
        log.debug("removing parcel {}", parcel);
        if (parcels.get(parcel) == 1) {
            parcels.remove(parcel);
            log.debug("Removed parcel {}", parcel);
        } else {
            parcels.put(parcel, parcels.get(parcel) - 1);
            log.debug("Reducing the number of parcels {}", parcel);
        }
    }

    protected Map.Entry<Integer, Integer> findEmptyPlace(int[][] grid, int height, int width) {
        log.debug("finding empty place with height {}, width {}", Truck.MAX_HEIGHT - height, Truck.MAX_WIDTH - width);
        int maxEmptyHeight = 0;
        int maxEmptyWidth = 0;
        for (int i = 0; i <= height; i++) {
            int emptyWidth = 0;
            for (int j = 0; j < grid.length - width; j++) {
                if (grid[height - i][width + j] == 0) {
                    emptyWidth++;
                }
            }
            if (emptyWidth > maxEmptyWidth) {
                maxEmptyWidth = emptyWidth;
            }
            maxEmptyHeight++;
        }
        return Map.entry(maxEmptyHeight, maxEmptyWidth);
    }

    protected void setParcelIntoGrid(int[][] grid, Parcel parcel, int startHeight, int startWidth) {
        log.debug("setting parcel into grid");
        int height = parcel.getHeight();
        for (int i = 0; i < parcel.getHeight(); i++) {
            int width = parcel.getLineLength(height - 1);
            for (int j = 0; j < width; j++) {
                grid[startHeight - i][startWidth + j] = parcel.getDigit();
            }
            height--;
        }
    }
}