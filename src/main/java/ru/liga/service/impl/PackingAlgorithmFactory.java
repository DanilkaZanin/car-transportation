package ru.liga.service.impl;

import lombok.experimental.UtilityClass;
import ru.liga.model.TypeOfAlgorithm;
import ru.liga.service.PackingAlgorithm;

/**
 * Перечисление, представляющее различные типы алгоритмов упаковки.
 */
@UtilityClass
public class PackingAlgorithmFactory {

    /**
     * Создает соответствующий алгоритм упаковки.
     *
     * @return алгоритм упаковки
     */
    public PackingAlgorithm createAlgorithm(TypeOfAlgorithm type) {
        return switch (type) {
            case SIMPLE -> new SimplePackingAlgorithm();
            case COMPLEX -> new ComplexPackingAlgorithm();
            case EVENLY -> new EvenlyPackingAlgorithm();
        };
    }
}