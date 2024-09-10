package ru.liga.model;

import lombok.Getter;


public class Truck {
    public static final int MAX_HEIGHT = 6;
    public static final int MAX_WIDTH = 6;
    @Getter
    private int[][] grid;

    public Truck() {
        this.grid = new int[MAX_HEIGHT][MAX_WIDTH];
    }

    @Override
    public String toString() {
        String string = new String();
        for (int i = 0; i < MAX_HEIGHT; i++) {
            for (int j = 0; j < MAX_WIDTH; j++) {
                string += grid[i][j] + " ";
            }
            string += "\n";
        }
        return string;
    }
}