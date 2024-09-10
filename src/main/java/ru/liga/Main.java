package ru.liga;

import ru.liga.algoritm.PackingAlgorithm;
import ru.liga.model.Parcel;
import ru.liga.model.Truck;
import ru.liga.model.TypeOfAlgorithm;
import ru.liga.reader.MyCsvReader;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MyCsvReader reader = new MyCsvReader();
        String possibleParcelsPath = "src\\main\\resources\\possible-parcels.csv";
        String parcelsPath = "src\\main\\resources\\file.csv";
        Map<Parcel, Integer> parcels = reader.readCSV(possibleParcelsPath, parcelsPath);

        PackingAlgorithm packingAlgorithm = new PackingAlgorithm(parcels, menu());
        List<Truck> trucks = packingAlgorithm.getTrucks();

        for (Truck truck : trucks) {
            System.out.println(truck);
        }
    }

    public static TypeOfAlgorithm menu() {
        System.out.println("s - simple");
        System.out.println("c - complex");

        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        return switch (str) {
            case "s" -> TypeOfAlgorithm.SIMPLE;
            case "c" -> TypeOfAlgorithm.COMPLEX;
            default -> throw new IllegalArgumentException("Invalid input");
        };
    }
}