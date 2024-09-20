package ru.liga.truck.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Getter
public class Truck {
    private final int id;
    private final CountOfParcels countOfParcels;
    public static final int MAX_HEIGHT = 6;
    public static final int MAX_WIDTH = 6;
    private final int[][] grid;

    public Truck() {
        this.grid = new int[MAX_HEIGHT][MAX_WIDTH];
        this.id = UUID.randomUUID().hashCode();
        this.countOfParcels = new CountOfParcels();
    }

    public Truck(int[][] grid, int count) {
        this.grid = grid;
        this.id = UUID.randomUUID().hashCode();
        this.countOfParcels = new CountOfParcels();
        countOfParcels.setCount(count);
    }

    @JsonCreator
    public Truck(@JsonProperty("id") int id,
                 @JsonProperty("countOfParcels") CountOfParcels countOfParcels,
                 @JsonProperty("grid") int[][] grid) {
        this.id = id;
        this.grid = grid;
        this.countOfParcels = countOfParcels;
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