package ru.liga.model;

import lombok.Data;

import java.util.List;

@Data
public class Parcel {
    private int[] shape;
    private int height;
    private int width;
    private int digit;

    public Parcel(List<String> shape) {
        fillShape(shape);
        this.height = this.shape.length;
        this.width = shape.get(shape.size() - 1).length();
        this.digit = Character.getNumericValue(shape.get(0).charAt(0));
    }

    public int getLineLength(int indexOfLine) {
        return shape[indexOfLine];
    }

    private void fillShape(List<String> shape) {
        this.shape = new int[shape.size()];
        for (int i = 0; i < this.shape.length; i++) {
            this.shape[i] = shape.get(i).length();
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < this.shape.length; i++) {
            for (int j = 0; j < this.shape[i]; j++) {
                string.append(digit).append(" ");
            }
            string.append("\n");
        }
        return string.toString();
    }
}