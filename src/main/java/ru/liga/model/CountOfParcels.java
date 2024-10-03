package ru.liga.model;

import lombok.Data;

@Data
public class CountOfParcels {
    private int count;

    public void plusOne() {
        count++;
    }
}