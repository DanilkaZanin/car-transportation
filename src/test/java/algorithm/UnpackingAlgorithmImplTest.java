package algorithm;

import org.junit.jupiter.api.Test;
import ru.liga.algorithm.unpacking.UnpackingAlgorithm;
import ru.liga.algorithm.unpacking.UnpackingAlgorithmImpl;
import ru.liga.truck.model.Truck;

import java.util.List;

public class UnpackingAlgorithmImplTest {
    @Test
    public void testUnpackingAlgorithmImpl() {
        int[][] grid = {
                {3, 3, 3, 2, 2, 1},
                {6, 6, 6, 9, 9, 9},
                {6, 6, 6, 9, 9, 9},
                {7, 7, 7, 9, 9, 9},
                {7, 7, 7, 7, 2, 2},
                {5, 5, 5, 5, 5, 1}
        };
        Truck truck = new Truck(grid,0);

        UnpackingAlgorithm unpackingAlgorithm = new UnpackingAlgorithmImpl();
        unpackingAlgorithm.unpackParcels(List.of(truck));
    }
}