package com.aishik.findBoxes;

import java.util.Objects;

/**
 * This class stores x and y coordinates of a box. The x and y coordinates corresponds to the position on the board.
 */
public class Coordinates {
    /**
     * final int denoting the X coordinate
     */
    private final int x;
    /**
     * final int denoting the Y coordinate
     */
    private final int y;

    /**
     * getter or accessor method for the X coordinate
     * @return position on the X-axis of the board
     */
    public int getX() {
        return x;
    }
    /**
     * getter or accessor method for the Y coordinate
     * @return position on the Y-axis of the board
     */
    public int getY() {
        return y;
    }

    /**
     * Constructor for the coordinate class. Creates a Coordinates object which has the position on X-axis and Y-axis
     * on the Board.
     * @param x X coordinate
     * @param y Y coordinate
     */
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
