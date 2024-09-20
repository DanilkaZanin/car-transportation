package algorithm;

import org.junit.jupiter.api.Test;
import ru.liga.algorithm.packing.types.EvenlyPackingAlgorithm;
import ru.liga.algorithm.packing.model.PackingAlgorithm;
import ru.liga.truck.model.Truck;
import ru.liga.parcel.model.Parcel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.*;

public class EvenlyPackingAlgorithmTest {
    private PackingAlgorithm packingAlgorithm;


    @Test
    public void shouldPackageOneParcelInOneTruck() {
        List<String> shape = List.of("1");
        Parcel parcel = new Parcel(shape);
        Map<Parcel, Integer> parcels = new HashMap<>();
        parcels.put(parcel,1);
        packingAlgorithm = new EvenlyPackingAlgorithm(parcels, List.of(new Truck()));

        List<Truck> trucks = packingAlgorithm.packageParcels();

        int[][] expectedTruckGrid = {
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {1,0,0,0,0,0}};
        assertThat(trucks.get(0).getGrid()).isEqualTo(expectedTruckGrid);
    }

    @Test
    public void shouldPackageTwoParcelsInTwoTrucks() {
        List<String> shape1 = List.of("1");
        Parcel parcel1 = new Parcel(shape1);
        Map<Parcel, Integer> parcels = new HashMap<>();
        parcels.put(parcel1, 2);
        packingAlgorithm = new EvenlyPackingAlgorithm(parcels, List.of(new Truck(), new Truck()));

        List<Truck> trucks = packingAlgorithm.packageParcels();

        int[][] expectedTrucksGrid = {
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {0,0,0,0,0,0},
                {1,0,0,0,0,0}
        };
        assertThat(trucks.get(0).getGrid()).isEqualTo(expectedTrucksGrid);
        assertThat(trucks.get(1).getGrid()).isEqualTo(expectedTrucksGrid);
    }

    @Test
    public void shouldPackageThreeParcelsInTwoTrucks() {
        List<String> shape1 = List.of("1");
        Parcel parcel1 = new Parcel(shape1);
        Map<Parcel, Integer> parcels = new HashMap<>();
        parcels.put(parcel1, 3);
        packingAlgorithm = new EvenlyPackingAlgorithm(parcels, List.of(new Truck(), new Truck()));

        List<Truck> trucks = packingAlgorithm.packageParcels();

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
    public void shouldThrowPackingExceptionWhenParcelDoesNotFitInTruck() {
        List<String> largeParcelShape = List.of("55555");
        Parcel largeParcel = new Parcel(largeParcelShape);
        Map<Parcel, Integer> parcels = new HashMap<>();
        parcels.put(largeParcel, 7);

        packingAlgorithm = new EvenlyPackingAlgorithm(parcels, List.of(new Truck()));

        assertThatThrownBy(() -> packingAlgorithm.packageParcels())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Не удалось упаковать все посылки");
    }

    @Test
    public void shouldNotThrowException() {
        List<String> largeParcelShape = List.of("55555");
        List<String> shape1 = List.of("333");

        Parcel largeParcel = new Parcel(largeParcelShape);
        Parcel parcel1 = new Parcel(shape1);

        Map<Parcel, Integer> parcels = new HashMap<>();
        parcels.put(largeParcel, 1);
        parcels.put(parcel1, 1);

    }
}