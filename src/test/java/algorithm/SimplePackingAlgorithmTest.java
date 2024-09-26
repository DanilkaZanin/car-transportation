package algorithm;

import org.junit.jupiter.api.Test;
import ru.liga.algorithm.packing.model.PackingAlgorithm;
import ru.liga.algorithm.packing.types.SimplePackingAlgorithm;
import ru.liga.parcel.model.Parcel;
import ru.liga.truck.model.Truck;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SimplePackingAlgorithmTest {

    @Test
    public void shouldPackageOneParcelInOneTruck() {
        List<String> shape = List.of("1");
        Parcel parcel = new Parcel(shape);
        Map<Parcel, Integer> parcels = new HashMap<>();
        parcels.put(parcel, 1);
        PackingAlgorithm packingAlgorithm = new SimplePackingAlgorithm();

        List<Truck> trucks = packingAlgorithm.packParcelsIntoTrucks(parcels, List.of(new Truck()));

        int[][] expectedTruckGrid = {
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0}
        };
        assertThat(trucks.get(0).getGrid()).isEqualTo(expectedTruckGrid);
    }

    @Test
    public void shouldPackageOneParcelInTwoTrucks() {
        List<String> shape = List.of("1");
        Parcel parcel = new Parcel(shape);
        Map<Parcel, Integer> parcels = new HashMap<>();
        parcels.put(parcel, 1);
        PackingAlgorithm packingAlgorithm = new SimplePackingAlgorithm();

        List<Truck> trucks = packingAlgorithm.packParcelsIntoTrucks(parcels, List.of(new Truck(), new Truck()));

        int[][] expectedTruckGrid1 = {
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0}
        };
        int[][] expectedTruckGrid2 = {
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };

        assertThat(trucks.get(0).getGrid()).isEqualTo(expectedTruckGrid1);
        assertThat(trucks.get(1).getGrid()).isEqualTo(expectedTruckGrid2);
    }

    @Test
    public void shouldThrowExceptionWhenTwoParcelsDontFitInOneTruck() {
        List<String> shape = List.of("1");
        Parcel parcel = new Parcel(shape);
        Map<Parcel, Integer> parcels = new HashMap<>();
        parcels.put(parcel, 2); // Две посылки
        PackingAlgorithm packingAlgorithm = new SimplePackingAlgorithm();
        assertThatThrownBy(() -> packingAlgorithm.packParcelsIntoTrucks(parcels, List.of(new Truck())))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Не удастся упаковать все посылки");
    }
}