package ru.liga.algorithm.packing.model;

import ru.liga.algorithm.packing.types.ComplexPackingAlgorithm;
import ru.liga.algorithm.packing.types.EvenlyPackingAlgorithm;
import ru.liga.algorithm.packing.types.SimplePackingAlgorithm;

/**
 * Перечисление, представляющее различные типы алгоритмов упаковки.
 */
public enum TypeOfAlgorithm {
    SIMPLE {
        @Override
        public PackingAlgorithm createAlgorithm() {
            return new SimplePackingAlgorithm();
        }
    },
    COMPLEX {
        @Override
        public PackingAlgorithm createAlgorithm() {
            return new ComplexPackingAlgorithm();
        }
    },
    EVENLY {
        @Override
        public PackingAlgorithm createAlgorithm() {
            return new EvenlyPackingAlgorithm();
        }
    };

    /**
     * Создает соответствующий алгоритм упаковки.
     *
     * @return алгоритм упаковки
     */
    public abstract PackingAlgorithm createAlgorithm();
}