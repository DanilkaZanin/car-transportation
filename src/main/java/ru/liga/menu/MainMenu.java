package ru.liga.menu;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import ru.liga.repository.ParcelReader;

import java.util.Scanner;

@Builder
@RequiredArgsConstructor
public class MainMenu {
    private final ParcelReader reader;
    private final TruckMenu truckMenu;
    private final Scanner scanner;

    public void showMenu() {
        System.out.println("Select operating mode");
        System.out.println("1 - read json");
        System.out.println("2 - load parcels into the truck");
        String str = scanner.nextLine();
        switch (str) {
            case "1" -> truckMenu.unpackTrucks();
            case "2" -> truckMenu.packTrucks(reader);
            default -> System.out.println("Invalid selection");
        }
    }
}