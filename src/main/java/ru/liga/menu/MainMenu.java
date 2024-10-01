package ru.liga.menu;

import lombok.RequiredArgsConstructor;
import ru.liga.parcel.reader.MyCsvReader;

import java.util.Scanner;

@RequiredArgsConstructor
public class MainMenu {
    private final TruckMenu truckMenu;

    public void showMenu(MyCsvReader reader) {
        System.out.println("Выберите режим работы");
        System.out.println("1 - читать json");
        System.out.println("2 - погрузка посылок в грузовик");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        switch (str) {
            case "1" -> truckMenu.readAndUnpackTrucks();
            case "2" -> truckMenu.packTrucks(reader);
            default -> System.out.println("Неверный выбор");
        }
    }
}