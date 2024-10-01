package ru.liga;

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

/**
 * Основной класс программы.
 */
@Slf4j
public class Main {
    /**
     * Точка входа в программу. Запускает процесс работы с посылками и машинами.
     */
    public static void main(String[] args) {
        log.info("Start");
        MyCsvReader csvReader = new MyCsvReader(new ParcelsValidator());
        UnpackingAlgorithm unpackingAlgorithm = new UnpackingAlgorithmImpl();
        TruckFactory truckFactory = new TruckFactoryImpl();

        AlgorithmSelector algorithmSelector = new AlgorithmSelector();
        TruckMenu truckMenu = new TruckMenu(unpackingAlgorithm, truckFactory, algorithmSelector);
        MainMenu mainMenu = new MainMenu(truckMenu);
        mainMenu.showMenu(csvReader);
        log.info("End");
    }
}