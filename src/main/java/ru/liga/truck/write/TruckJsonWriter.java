package ru.liga.truck.write;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.truck.model.Truck;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TruckJsonWriter implements TruckWriter {
    private final ObjectMapper mapper;

    @Override
    public List<Truck> write(List<Truck> trucks, File file) {
        log.info("Writing trucks into {}", file.getName());
        try {
            mapper.writeValue(file, trucks);
        } catch (IOException e) {
            log.warn("Failed to write trucks into {}", file.getName(), e);
        }
        return trucks;
    }
}