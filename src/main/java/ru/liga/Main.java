package ru.liga;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.liga.algorithm.unpacking.UnpackingAlgorithm;
import ru.liga.algorithm.unpacking.UnpackingAlgorithmImpl;
import ru.liga.menu.AlgorithmSelector;
import ru.liga.menu.MainMenu;
import ru.liga.menu.TruckMenu;
import ru.liga.parcel.reader.MyCsvReader;
import ru.liga.parcel.validation.ParcelsValidator;
import ru.liga.truck.factory.TruckFactory;
import ru.liga.truck.factory.TruckFactoryImpl;
import ru.liga.truck.read.TruckJsonReader;
import ru.liga.truck.read.TruckReader;
import ru.liga.truck.write.TruckJsonWriter;
import ru.liga.truck.write.TruckWriter;

import java.io.File;

@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("Start");
        ObjectMapper objectMapper = new ObjectMapper();
        MyCsvReader csvReader = new MyCsvReader(new ParcelsValidator());
        TruckReader truckReader = new TruckJsonReader(new File("trucks.json"), objectMapper);
        UnpackingAlgorithm unpackingAlgorithm = new UnpackingAlgorithmImpl();
        TruckFactory truckFactory = new TruckFactoryImpl();
        TruckWriter truckWriter = new TruckJsonWriter(objectMapper);

        AlgorithmSelector algorithmSelector = new AlgorithmSelector();
        TruckMenu truckMenu = new TruckMenu(truckReader, unpackingAlgorithm, truckFactory, truckWriter, algorithmSelector);
        MainMenu mainMenu = new MainMenu(truckMenu);
        mainMenu.showMenu(csvReader);
        log.info("End");
    }
}