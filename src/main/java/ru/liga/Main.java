package ru.liga;

import lombok.extern.slf4j.Slf4j;
import ru.liga.config.AppConfig;

/**
 * Основной класс программы.
 */
@Slf4j
public class Main {
    /**
     * Точка входа в программу. Запускает процесс работы с посылками и машинами.
     */
    public static void main(String[] args) {
        log.info("Start");
        AppConfig.createMainMenu().showMenu();
        log.info("End");
    }
}