package ru.liga.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import ru.liga.algorithm.packing.model.PackingAlgorithm;
import ru.liga.algorithm.unpacking.UnpackingAlgorithm;
import ru.liga.parcel.model.Parcel;
import ru.liga.parcel.reader.MyCsvReader;
import ru.liga.truck.factory.TruckFactory;
import ru.liga.truck.model.Truck;
import ru.liga.truck.read.TruckJsonReader;
import ru.liga.truck.validation.CountOfTrucksValidator;
import ru.liga.truck.write.TruckJsonWriter;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@RequiredArgsConstructor
public class TruckMenu {
    private static final String BASE_FILE_PATH = "src\\main\\resources\\";

    private final UnpackingAlgorithm unpackingAlgorithm;
    private final TruckFactory truckFactory;
    private final AlgorithmSelector algorithmSelector;

    public void readAndUnpackTrucks() {
        unpackingAlgorithm.unpackParcels(
                new TruckJsonReader(
                        new File(BASE_FILE_PATH + readFileNameToUnpackTrucks("trucks.json")),
                        new ObjectMapper()).read()
        );
    }

    public void packTrucks(MyCsvReader reader) {
        int count = readCountOfTrucksToPuck();
        if (!CountOfTrucksValidator.isValid(count)) {
            throw new RuntimeException("Неверное количество грузовиков");
        }
        List<Truck> trucks = truckFactory.createTrucks(count);
        File file = new File(BASE_FILE_PATH + readFileNameToUnpackTrucks("file.csv"));
        Map<Parcel, Integer> parcels = reader.readCSV(file.getAbsolutePath());

        PackingAlgorithm algorithm = algorithmSelector.selectAlgorithm();
        List<Truck> packedTrucks = algorithm.packParcelsIntoTrucks(parcels, trucks);
        new TruckJsonWriter(new ObjectMapper()).write(packedTrucks, file);
    }

    private String readFileNameToUnpackTrucks(String recommendedFileName) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Введите имя файла, которое необходимо прочитать (рекомендуется: %s)%n", recommendedFileName);
        return scanner.nextLine().trim();
    }

    private int readCountOfTrucksToPuck() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите количество грузовиков");
        return scanner.nextInt();
    }
}