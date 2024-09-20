package ru.liga.parcel.model;

import lombok.Data;

import java.util.List;

@Data
public class Parcel {
    private int[][] shape;
    private int height;
    private int width;
    private int digit;

    public Parcel(List<String> shapeInput) {
        fillShape(shapeInput);
        this.height = this.shape.length;
        this.width = this.shape[0].length;
        this.digit = Character.getNumericValue(shapeInput.get(0).charAt(0));
    }

    public int getLineLength(int indexOfLine) {
        return shape[indexOfLine].length;
    }

    private void fillShape(List<String> shapeInput) {
        this.shape = new int[shapeInput.size()][];
        for (int i = 0; i < shapeInput.size(); i++) {
            this.shape[i] = new int[shapeInput.get(i).length()];
            for (int j = 0; j < shapeInput.get(i).length(); j++) {
                this.shape[i][j] = Character.getNumericValue(shapeInput.get(i).charAt(j));
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("\n");
        for (int[] line : this.shape) {
            for (int value : line) {
                string.append(value).append(" ");
            }
            string.append("\n");
        }
        return string.toString();
    }
}