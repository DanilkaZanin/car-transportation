package ru.liga.menu;

import ru.liga.algorithm.packing.model.PackingAlgorithm;
import ru.liga.algorithm.packing.model.TypeOfAlgorithm;

import java.util.Scanner;

public class AlgorithmSelector {

    public PackingAlgorithm selectAlgorithm() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("s - simple");
        System.out.println("c - complex");
        System.out.println("e - evenly");

        String str = scanner.nextLine();
        return switch (str) {
            case "s" -> TypeOfAlgorithm.SIMPLE.createAlgorithm();
            case "c" -> TypeOfAlgorithm.COMPLEX.createAlgorithm();
            case "e" -> TypeOfAlgorithm.EVENLY.createAlgorithm();
            default -> throw new IllegalArgumentException("Invalid input");
        };
    }
}