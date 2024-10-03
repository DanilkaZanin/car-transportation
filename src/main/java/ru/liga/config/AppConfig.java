package ru.liga.config;

import lombok.experimental.UtilityClass;
import ru.liga.menu.AlgorithmSelector;
import ru.liga.menu.MainMenu;
import ru.liga.menu.TruckMenu;
import ru.liga.repository.impl.ParcelCsvReader;
import ru.liga.service.impl.TruckFactoryImpl;
import ru.liga.service.impl.UnpackingAlgorithmImpl;
import ru.liga.validation.ParcelsValidator;

import java.util.Scanner;

@UtilityClass
public class AppConfig {

    public MainMenu createMainMenu() {
        Scanner scanner = new Scanner(System.in);

        TruckMenu truckMenu = TruckMenu.builder()
                .algorithmSelector(new AlgorithmSelector(scanner))
                .truckFactory(new TruckFactoryImpl())
                .scanner(scanner)
                .unpackingAlgorithm(new UnpackingAlgorithmImpl())
                .build();

        return MainMenu.builder()
                .truckMenu(truckMenu)
                .reader(new ParcelCsvReader(new ParcelsValidator()))
                .scanner(scanner)
                .build();
    }
}