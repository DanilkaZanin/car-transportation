package ru.liga.menu;

import lombok.RequiredArgsConstructor;
import ru.liga.model.TypeOfAlgorithm;
import ru.liga.service.PackingAlgorithm;
import ru.liga.service.impl.PackingAlgorithmFactory;

import java.util.Scanner;

/**
 * Класс отвечает за выбор подходящего алгоритма упаковки.
 * Предлагает пользователю ввести символ для выбора алгоритма и возвращает соответствующую реализацию.
 */
@RequiredArgsConstructor
public class AlgorithmSelector {
    private final Scanner scanner;

    /**
     * Предлагает пользователю выбрать алгоритм упаковки, вводя один из символов:
     * <ul>
     *     <li>'s' - простой алгоритм упаковки</li>
     *     <li>'c' - сложный алгоритм упаковки</li>
     *     <li>'e' - равномерный алгоритм упаковки</li>
     * </ul>
     *
     * @return выбранный алгоритм {@link PackingAlgorithm}, исходя из ввода пользователя.
     * @throws IllegalArgumentException если введённый символ не соответствует ни одному алгоритму.
     */
    public PackingAlgorithm selectAlgorithm() {
        System.out.println("s - simple");
        System.out.println("c - complex");
        System.out.println("e - evenly");

        String str = scanner.nextLine();
        return switch (str) {
            case "s" -> PackingAlgorithmFactory.createAlgorithm(TypeOfAlgorithm.SIMPLE);
            case "c" -> PackingAlgorithmFactory.createAlgorithm(TypeOfAlgorithm.COMPLEX);
            case "e" -> PackingAlgorithmFactory.createAlgorithm(TypeOfAlgorithm.EVENLY);
            default -> throw new IllegalArgumentException("Invalid input");
        };
    }
}