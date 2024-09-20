package ru.liga.algorithm.packing.types;

import lombok.extern.slf4j.Slf4j;
import ru.liga.algorithm.packing.model.PackingAlgorithmImpl;
import ru.liga.truck.model.Truck;
import ru.liga.parcel.model.Parcel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class SimplePackingAlgorithm extends PackingAlgorithmImpl {

    public SimplePackingAlgorithm(Map<Parcel, Integer> parcels, List<Truck> trucks) {
        super(parcels, trucks);
        log.info("Using simple packing algorithm");
        if(getCountOfParcels() > trucks.size()) {
            log.warn("Not enough trucks to simple packing algorithm");
            throw new RuntimeException("Не удастся упаковать все посылки");
        }
    }

    @Override
    public List<Truck> packageParcels() {
        log.info("Packing parcels");
        for (Truck truck : trucks) {
            Optional<Parcel> parcel = getParcel(Truck.MAX_HEIGHT - 1, Truck.MAX_WIDTH - 1);
            parcel.ifPresent(p -> setParcelIntoGrid(truck.getGrid(), p, Truck.MAX_HEIGHT - 1, 0));
        }
        return trucks;
    }

    private int getCountOfParcels() {
        return parcels.values().stream().mapToInt(Integer::intValue).sum();
    }
}