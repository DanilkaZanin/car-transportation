package ru.liga.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.liga.model.Truck;
import ru.liga.repository.TruckWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Класс, реализующий запись машин в JSON-файл.
 */
@Slf4j
@RequiredArgsConstructor
public class TruckJsonWriter implements TruckWriter {
    private final ObjectMapper mapper;

    /**
     * Записывает список машин в указанный JSON-файл.
     *
     * @param trucks список машин для записи
     * @param file   файл для записи
     * @return список записанных машин
     */
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