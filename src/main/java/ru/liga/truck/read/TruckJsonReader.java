package ru.liga.truck.read;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.truck.model.Truck;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Класс, реализующий чтение машин из JSON-файла.
 */
@Slf4j
@RequiredArgsConstructor
public class TruckJsonReader implements TruckReader {
    private final File file;
    private final ObjectMapper mapper;

    /**
     * Читает список машин из JSON-файла.
     *
     * @return список машин
     */
    @Override
    public List<Truck> read() {
        log.info("Reading trucks from csv file, {}", file.getName());
        try {
            return mapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            log.warn("failed to read file {}", file.getName(), e);
            throw new RuntimeException(e);
        }
    }
}