package ru.liga.algorithm.packing.model;

import ru.liga.algorithm.packing.types.ComplexPackingAlgorithm;
import ru.liga.algorithm.packing.types.EvenlyPackingAlgorithm;
import ru.liga.algorithm.packing.types.SimplePackingAlgorithm;

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

    public abstract PackingAlgorithm createAlgorithm();
}