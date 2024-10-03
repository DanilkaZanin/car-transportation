package service.impl;

import org.junit.jupiter.api.Test;
import ru.liga.model.Parcel;
import ru.liga.model.Truck;
import ru.liga.service.PackingAlgorithm;
import ru.liga.service.impl.SimplePackingAlgorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SimplePackingAlgorithmTest {

    @Test
    void shouldPackageOneParcelInOneTruck() {
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
    void shouldPackageOneParcelInTwoTrucks() {
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
    void shouldThrowExceptionWhenTwoParcelsDontFitInOneTruck() {
        List<String> shape = List.of("1");
        Parcel parcel = new Parcel(shape);
        Map<Parcel, Integer> parcels = new HashMap<>();
        parcels.put(parcel, 2); // Две посылки
        PackingAlgorithm packingAlgorithm = new SimplePackingAlgorithm();

        List<Truck> trucks = List.of(new Truck());

        assertThatThrownBy(() -> packingAlgorithm.packParcelsIntoTrucks(parcels, trucks))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Unable to pack all parcels.");
    }
}