package service.impl;

import org.junit.jupiter.api.Test;
import ru.liga.model.Parcel;
import ru.liga.model.Truck;
import ru.liga.service.PackingAlgorithm;
import ru.liga.service.impl.EvenlyPackingAlgorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EvenlyPackingAlgorithmTest {
    private PackingAlgorithm packingAlgorithm;


    @Test
    void shouldPackageOneParcelWhenOneTruck() {
        List<String> shape = List.of("1");
        Parcel parcel = new Parcel(shape);
        Map<Parcel, Integer> parcels = new HashMap<>();
        parcels.put(parcel, 1);
        packingAlgorithm = new EvenlyPackingAlgorithm();

        List<Truck> trucks = packingAlgorithm.packParcelsIntoTrucks(parcels, List.of(new Truck()));

        int[][] expectedTruckGrid = {
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0}};
        assertThat(trucks.get(0).getGrid()).isEqualTo(expectedTruckGrid);
    }

    @Test
    void shouldPackageTwoParcelsWhenTwoTrucks() {
        List<String> shape1 = List.of("1");
        Parcel parcel1 = new Parcel(shape1);
        Map<Parcel, Integer> parcels = new HashMap<>();
        parcels.put(parcel1, 2);
        packingAlgorithm = new EvenlyPackingAlgorithm();

        List<Truck> trucks = packingAlgorithm.packParcelsIntoTrucks(parcels, List.of(new Truck(), new Truck()));

        int[][] expectedTrucksGrid = {
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0}
        };
        assertThat(trucks.get(0).getGrid()).isEqualTo(expectedTrucksGrid);
        assertThat(trucks.get(1).getGrid()).isEqualTo(expectedTrucksGrid);
    }

    @Test
    void shouldPackageThreeParcelsWhenTwoTrucks() {
        List<String> shape1 = List.of("1");
        Parcel parcel1 = new Parcel(shape1);
        Map<Parcel, Integer> parcels = new HashMap<>();
        parcels.put(parcel1, 3);
        packingAlgorithm = new EvenlyPackingAlgorithm();

        List<Truck> trucks = packingAlgorithm.packParcelsIntoTrucks(parcels, List.of(new Truck(), new Truck()));

        int totalParcelsTruck1 = countParcelsInTruck(trucks.get(0));
        int totalParcelsTruck2 = countParcelsInTruck(trucks.get(1));

        assertThat(totalParcelsTruck1 + totalParcelsTruck2).isEqualTo(3);
    }

    // Вспомогательная функция для подсчёта посылок в грузовике
    private int countParcelsInTruck(Truck truck) {
        int[][] grid = truck.getGrid();
        int count = 0;
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == 1) {
                    count++;
                }
            }
        }
        return count;
    }

    @Test
    void shouldThrowPackingExceptionWhenParcelDoesNotFitInTruck() {
        List<String> largeParcelShape = List.of("55555");
        Parcel largeParcel = new Parcel(largeParcelShape);
        Map<Parcel, Integer> parcels = new HashMap<>();
        parcels.put(largeParcel, 7);

        packingAlgorithm = new EvenlyPackingAlgorithm();
        List<Truck> trucks = List.of(new Truck());

        assertThatThrownBy(() -> packingAlgorithm.packParcelsIntoTrucks(parcels, trucks))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Failed to pack all parcels");
    }
}