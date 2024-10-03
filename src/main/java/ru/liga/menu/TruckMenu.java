package ru.liga.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import ru.liga.exception.InvalidTrucksNumberException;
import ru.liga.model.Parcel;
import ru.liga.model.Truck;
import ru.liga.repository.ParcelReader;
import ru.liga.repository.impl.TruckJsonReader;
import ru.liga.repository.impl.TruckJsonWriter;
import ru.liga.service.PackingAlgorithm;
import ru.liga.service.TruckFactory;
import ru.liga.service.UnpackingAlgorithm;
import ru.liga.validation.CountOfTrucksValidator;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Builder
@RequiredArgsConstructor
public class TruckMenu {
    private static final String BASE_FILE_PATH = "src\\main\\resources\\";

    private final UnpackingAlgorithm unpackingAlgorithm;
    private final TruckFactory truckFactory;
    private final AlgorithmSelector algorithmSelector;
    private final Scanner scanner;

    public void unpackTrucks() {
        unpackingAlgorithm.unpackParcels(
                new TruckJsonReader(new ObjectMapper())
                        .read(new File(
                                BASE_FILE_PATH +
                                        readFileNameToUnpackTrucks("trucks.json"))
                        )
        );
    }

    public void packTrucks(ParcelReader reader) {
        int count = readCountOfTrucksToPuck();
        if (!CountOfTrucksValidator.isValid(count)) {
            throw new InvalidTrucksNumberException("Invalid number of trucks");
        }
        List<Truck> trucks = truckFactory.createTrucks(count);
        File file = new File(BASE_FILE_PATH + readFileNameToUnpackTrucks("file.csv"));
        Map<Parcel, Integer> parcels = reader.readCsv(file.getAbsolutePath());

        PackingAlgorithm algorithm = algorithmSelector.selectAlgorithm();
        List<Truck> packedTrucks = algorithm.packParcelsIntoTrucks(parcels, trucks);
        new TruckJsonWriter(new ObjectMapper()).write(packedTrucks, file);
    }

    private String readFileNameToUnpackTrucks(String recommendedFileName) {
        System.out.printf("Enter the filename to read (recommended: %s)%n", recommendedFileName);
        return scanner.nextLine().trim();
    }

    private int readCountOfTrucksToPuck() {
        System.out.println("Enter the number of trucks");
        return scanner.nextInt();
    }
}