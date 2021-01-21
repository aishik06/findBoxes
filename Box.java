package com.aishik.findBoxes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Represents an individual box on a board
 */
public class Box extends Rectangle {
    /**
     * @return the coordinates of the box
     */
    public Coordinates getCoordinate() {
        return coordinate;
    }

    /**
     * A final Coordinates variable that stores the coordinates of the box. Is set when a Box object is initialized
     */
    private final Coordinates coordinate;

    /**
     * Constructor for the Box class. Creates a Box object which represents an individual box on the board.
     * @param coordinate coordinate of the box on the board.
     */
    public Box(Coordinates coordinate) {
        super(30, 30);
        this.coordinate = coordinate;
        setFill(Color.LIGHTGRAY);
        setStroke(Color.BLACK);
    }

    /**
     * Method to set the color of the box to gray if it is not occupied
     */
    public void revealEmptyBox(){
        setFill(Color.GRAY);
    }

    /**
     * Method to set the color of the box red if it is occupied
     */
    public void revealOccupiedBox(){
        setFill(Color.RED);
    }
}
