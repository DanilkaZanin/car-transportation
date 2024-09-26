package ru.liga.menu;

import lombok.RequiredArgsConstructor;
import ru.liga.algorithm.packing.model.PackingAlgorithm;
import ru.liga.algorithm.unpacking.UnpackingAlgorithm;
import ru.liga.parcel.model.Parcel;
import ru.liga.parcel.reader.MyCsvReader;
import ru.liga.truck.factory.TruckFactory;
import ru.liga.truck.model.Truck;
import ru.liga.truck.read.TruckReader;
import ru.liga.truck.validation.CountOfTrucksValidator;
import ru.liga.truck.write.TruckWriter;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@RequiredArgsConstructor
public class TruckMenu {

    private final TruckReader truckReader;
    private final UnpackingAlgorithm unpackingAlgorithm;
    private final TruckFactory truckFactory;
    private final TruckWriter truckWriter;
    private final AlgorithmSelector algorithmSelector;

    public void readAndUnpackTrucks() {
        unpackingAlgorithm.unpackParcels(truckReader.read());
    }

    public void packTrucks(MyCsvReader reader) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите количество грузовиков");
        int count = scanner.nextInt();
        if (!CountOfTrucksValidator.isValid(count)) {
            throw new RuntimeException("Неверное количество грузовиков");
        }
        List<Truck> trucks = truckFactory.createTrucks(count);
        Map<Parcel, Integer> parcels = reader.readCSV(new File("src\\main\\resources\\file.csv").getAbsolutePath());

        PackingAlgorithm algorithm = algorithmSelector.selectAlgorithm();
        List<Truck> packedTrucks = algorithm.packParcelsIntoTrucks(parcels, trucks);
        truckWriter.write(packedTrucks, new File("trucks.json"));
    }
}