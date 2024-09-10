
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.liga.algoritm.PackingAlgorithm;
import ru.liga.model.Parcel;
import ru.liga.model.Truck;
import ru.liga.model.TypeOfAlgorithm;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PackingAlgorithmTest {

    private Map<Parcel, Integer> parcels;
    private Parcel smallParcel;
    private Parcel mediumParcel;
    private Parcel largeParcel;

    @BeforeEach
    void setUp() {
        // Инициализируем тестовые данные
        smallParcel = new Parcel(List.of("1"));
        mediumParcel = new Parcel(List.of("22"));
        largeParcel = new Parcel(List.of("333"));

        parcels = new HashMap<>();
        parcels.put(smallParcel, 2);
        parcels.put(mediumParcel, 1);
        parcels.put(largeParcel, 1);
    }

    @Test
    void testSimpleAlgorithm() {
        PackingAlgorithm algorithm = new PackingAlgorithm(parcels, TypeOfAlgorithm.SIMPLE);

        List<Truck> trucks = algorithm.getTrucks();
        assertNotNull(trucks);
        assertEquals(4, trucks.size(), "Должны быть использованы 3 грузовика");

        Truck firstTruck = trucks.get(0);
        Truck secondTruck = trucks.get(1);
        Truck thirdTruck = trucks.get(2);

        assertTrue(isParcelInTruck(firstTruck, largeParcel));
        assertTrue(isParcelInTruck(secondTruck, mediumParcel));
        assertTrue(isParcelInTruck(thirdTruck, smallParcel));
    }

    @Test
    void testComplexAlgorithm() {
        PackingAlgorithm algorithm = new PackingAlgorithm(parcels, TypeOfAlgorithm.COMPLEX);

        List<Truck> trucks = algorithm.getTrucks();
        assertNotNull(trucks);
        assertEquals(1, trucks.size(), "Должны быть использованы 2 грузовика при сложном алгоритме");

        Truck firstTruck = trucks.get(0);

        assertTrue(isParcelInTruck(firstTruck, largeParcel));
        assertTrue(isParcelInTruck(firstTruck, mediumParcel));
    }

    private boolean isParcelInTruck(Truck truck, Parcel parcel) {
        int[][] grid = truck.getGrid();
        for (int i = 0; i < Truck.MAX_HEIGHT; i++) {
            for (int j = 0; j < Truck.MAX_WIDTH; j++) {
                if (grid[i][j] == parcel.getDigit()) {
                    return true;
                }
            }
        }
        return false;
    }
}

