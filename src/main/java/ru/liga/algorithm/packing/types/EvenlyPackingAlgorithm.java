package ru.liga.algorithm.packing.types;

import lombok.extern.slf4j.Slf4j;
import ru.liga.algorithm.packing.model.PackingAlgorithmImpl;
import ru.liga.truck.model.Truck;
import ru.liga.parcel.model.Parcel;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class EvenlyPackingAlgorithm extends PackingAlgorithmImpl {
    public EvenlyPackingAlgorithm(Map<Parcel, Integer> parcels, List<Truck> trucks) {
        super(parcels, trucks);
        log.info("Using evenly packing algorithm");
    }

    @Override
    public List<Truck> packageParcels() {
        log.info("Packing Parcels");
        while (!this.parcels.isEmpty()) {
            boolean parcelPlaced = false;
            Truck truck = takeLessLoadedTruck();
            for (int i = Truck.MAX_HEIGHT - 1; i >= 0; i--) {
                for (int j = 0; j < Truck.MAX_WIDTH; j++) {
                    if (truck.getGrid()[i][j] == 0) {
                        Map.Entry<Integer, Integer> emptyPlace = findEmptyPlace(truck.getGrid(), i, j);
                        Optional<Parcel> parcel = getParcel(emptyPlace.getValue(), emptyPlace.getKey());
                        if (parcel.isPresent()) {
                            setParcelIntoGrid(truck.getGrid(), parcel.get(), i, j);
                            truck.getCountOfParcels().plusOne();
                            parcelPlaced = true;
                            break;
                        }
                    }
                }
                if (parcelPlaced) {
                    break;
                }
            }
            if (!parcelPlaced) {
                log.warn("Cannot place any more parcels in the truck {}", truck.hashCode());
                throw new RuntimeException("Не удалось упаковать все посылки");
            }
        }
        return this.trucks;
    }

    //Придумать мб ошибку вместо null
    private Truck takeLessLoadedTruck() {
        return trucks.stream()
                .min(Comparator.comparing(truck -> truck.getCountOfParcels().getCount()))
                .orElse(null);
    }
}