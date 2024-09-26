package ru.liga.algorithm.packing.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.parcel.model.Parcel;
import ru.liga.truck.model.Truck;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public abstract class PackingAlgorithmImpl implements PackingAlgorithm {
    private static final int LAST_PARCEL = 1;
    private static final int CONVERSION_INDEX = 1;
    private static final int FREE_ZONE_VALUE = 0;

    protected Optional<Parcel> getParcel(int height, int width, Map<Parcel, Integer> parcels) {
        log.trace("truing to get parcel with height {}, width {}", height, width);
        Comparator<Parcel> byHeightAndWidth = Comparator
                .comparingInt(Parcel::getHeight).reversed()
                .thenComparingInt(Parcel::getWidth);
        Optional<Parcel> optionalParcel = parcels.keySet().stream()
                .sorted(byHeightAndWidth)
                .filter(p -> p.getHeight() <= height && p.getWidth() <= width)
                .findFirst();

        optionalParcel.ifPresent(parcel -> removeParcel(parcel, parcels));
        return optionalParcel;
    }

    private void removeParcel(Parcel parcel, Map<Parcel, Integer> parcels) {
        log.debug("removing parcel {}", parcel);
        if (parcels.get(parcel) == LAST_PARCEL) {
            parcels.remove(parcel);
            log.debug("Removed parcel {}", parcel);
        } else {
            parcels.put(parcel, parcels.get(parcel) - CONVERSION_INDEX);
            log.debug("Reducing the number of parcels {}", parcel);
        }
    }

    protected Map.Entry<Integer, Integer> findEmptyPlace(int[][] grid, int height, int width) {
        log.debug("finding empty place with height {}, width {}", Truck.MAX_HEIGHT - height, Truck.MAX_WIDTH - width);
        int maxEmptyHeight = 0;
        int maxEmptyWidth = 0;

        for (int curretHeight = 0; curretHeight <= height; curretHeight++) {
            int emptyWidth = 0;
            for (int currentWidth = 0; currentWidth < grid.length - width; currentWidth++) {
                if (grid[height - curretHeight][width + currentWidth] == FREE_ZONE_VALUE) {
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
        int height = parcel.getHeight() - CONVERSION_INDEX;
        for (int currentHeight = 0; currentHeight < parcel.getHeight(); currentHeight++) {
            int width = parcel.getLineLength(height);
            for (int currentWidth = 0; currentWidth < width; currentWidth++) {
                grid[startHeight - currentHeight][startWidth + currentWidth] = parcel.getDigit();
            }
            height--;
        }
    }
}