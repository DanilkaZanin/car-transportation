package ru.liga;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.liga.algorithm.packing.model.PackingAlgorithm;
import ru.liga.algorithm.packing.model.TypeOfAlgorithm;
import ru.liga.algorithm.unpacking.model.UnpackingAlgorithm;
import ru.liga.algorithm.unpacking.model.UnpackingAlgorithmImpl;
import ru.liga.parcel.model.Parcel;
import ru.liga.parcel.reader.MyCsvReader;
import ru.liga.truck.model.Truck;
import ru.liga.truck.read.TruckJsonReader;
import ru.liga.truck.read.TruckReader;
import ru.liga.truck.validation.CountOfTrucksValidator;
import ru.liga.truck.write.TruckJsonWriter;
import ru.liga.truck.write.TruckWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Slf4j
public class Main {
    static File parcelsPath = new File("src\\main\\resources\\file.csv"); //file.csv

    public static void main(String[] args) {
        log.info("Start");
        MyCsvReader reader = new MyCsvReader();
        mainMenu(reader);
        log.info("End");
    }

    public static void mainMenu(MyCsvReader reader) {
        log.info("main menu");
        System.out.println("Выберите режим работы");
        System.out.println("1 - читать json");
        System.out.println("2 - погрузка посылок в грузовик");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        switch (str) {
            case "1":
                log.info("case 1, reading json");
                TruckReader truckReader = new TruckJsonReader(new File("trucks.json"), new ObjectMapper());
                UnpackingAlgorithm unpacker = new UnpackingAlgorithmImpl();
                unpacker.unpackParcels(truckReader.read());
                break;
            case "2":
                log.info("case 2, packing parcels");
                System.out.println("Введите количество грузовиков");
                int count = scanner.nextInt();

                if (!CountOfTrucksValidator.isValid(count)) {
                    log.warn("invalid count {} of trucks", count);
                    throw new RuntimeException();
                }

                List<Truck> trucks = getTrucks(count);
                Map<Parcel, Integer> parcels = reader.readCSV(parcelsPath.getAbsolutePath());

                PackingAlgorithm algorithm = typeOfAlgorithmMenu(parcels, trucks);

                writeTrucks(algorithm.packageParcels());
        }
    }

    public static PackingAlgorithm typeOfAlgorithmMenu(Map<Parcel, Integer> parcels, List<Truck> trucks) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("s - simple");
        System.out.println("c - complex");
        System.out.println("e - evenly");

        String str = scanner.nextLine();
        return switch (str) {
            case "s" -> TypeOfAlgorithm.SIMPLE.createAlgorithm(parcels, trucks);
            case "c" -> TypeOfAlgorithm.COMPLEX.createAlgorithm(parcels, trucks);
            case "e" -> TypeOfAlgorithm.EVENLY.createAlgorithm(parcels, trucks);
            default -> {
                log.error("invalid type {}", str);
                throw new IllegalArgumentException("Invalid input");
            }
        };

    }

    private static List<Truck> getTrucks(int count) {
        List<Truck> trucks = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            trucks.add(new Truck());
        }
        return trucks;
    }

    private static void writeTrucks(List<Truck> trucks) {
        TruckWriter truckWriter = new TruckJsonWriter(
                trucks,
                new ObjectMapper(),
                new File("trucks.json"));
        truckWriter.write();
    }
}
