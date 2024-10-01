package ru.liga.parcel.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Класс хранит шаблоны посылок в виде двумерных массивов целых чисел.
 * Используется для определения формы посылки на основе её цифрового идентификатора.
 */
public class ParcelPatterns {
    private static final Map<Integer, int[][]> PATTERNS = new HashMap<>();

    static {
        PATTERNS.put(1, new int[][]{{1}});
        PATTERNS.put(2, new int[][]{{2, 2}});
        PATTERNS.put(3, new int[][]{{3}, {3}, {3}});
        PATTERNS.put(5, new int[][]{{5, 5, 5, 5, 5}});
        PATTERNS.put(6, new int[][]{{6, 6, 6}, {6, 6, 6}});
        PATTERNS.put(7, new int[][]{{7, 7, 7}, {7, 7, 7, 7}});
        PATTERNS.put(8, new int[][]{{8, 8, 8, 8}, {8, 8, 8, 8}});
        PATTERNS.put(9, new int[][]{{9, 9, 9}, {9, 9, 9}, {9, 9, 9}});
    }

    /**
     * Возвращает шаблон посылки в виде двумерного массива, соответствующий переданному числу.
     *
     * @param digit число, которое представляет тип посылки.
     * @return {@link Optional}, содержащий шаблон посылки, если такой существует; пустой {@link Optional} в противном случае.
     */
    public static Optional<int[][]> getShape(int digit) {
        return Optional.ofNullable(PATTERNS.get(digit));
    }
}