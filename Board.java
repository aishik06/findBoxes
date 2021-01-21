package com.aishik.findBoxes;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashSet;
import java.util.Set;

/**
 * This is the Board class which represents the board on which the players play.
 */
public class Board extends Parent {
    /**
     * boolean variable to check the game mode
     * <p>gameModeOn = false denotes that the game cannot be started till the initialization is complete
     *    gameModeOn = true denotes that the initialization has been done and the game can be played now.</p>
     */
    private boolean gameModeOn = false; // whether game mode is on or board is still getting initialized
    /**
     * A final set of coordinates to denote the position of each box that is revealed
     */
    private final Set<Coordinates> revealedCoordinates = new HashSet<>();
    /**
     * A final int variable to store the number of rows in the board
     */
    private final int rowCount;
    /**
     * A final int variable to store the number of columns in the board
     */
    private final int columnCount;
    /**
     * A final set of coordinates that denotes the position of each box that is occupied
     */
    private final Set<Coordinates> occupied = new HashSet<>();
    /**
     * A final int to denote the total number of boxes each player should have in its board.
     */
    private final int maxBoxes; // the number of boxes a board can have
    /**
     * A boolean variable to control whether the board is active or not. If activeBoard = true, the board can be clicked
     * by the respective player otherwise, the board is not clickable by the respective player.
     */
    private boolean activeBoard = false; // makes the board active and clickable

    /**
     * A setter or mutator method for the variable activeBoard. Used to change the status of the activeBoard.
     * @param activeBoard A boolean value that will be set to activeBoard
     */
    public void setActiveBoard(boolean activeBoard) {
        this.activeBoard = activeBoard;
    }

    /**
     * Constructor for the Board class. Creates board objects.
     *
     * <p>The VBox object cols corresponds to the layout of children in a single vertical column
     *    The HBox object row corresponds to the layout of children in a single horizontal row</p>
     * @param rowCount denotes the total rows a board has.
     * @param columnCount denotes the total columns a board has.
     * @param maxBoxes denotes the number of boxes a player can hide in the board
     * @param handler a MouseEvent that is used to check if a player has made any move on the board
     */
    public Board(int rowCount, int columnCount, int maxBoxes, EventHandler<MouseEvent> handler) {
        validate(rowCount, columnCount);
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.maxBoxes = maxBoxes;

        VBox cols = new VBox();
        for (int y = 0; y < columnCount; y++) {
            HBox row = new HBox();
            for (int x = 0; x < rowCount; x++) {
                Box b = new Box(new Coordinates(x, y));
                b.setOnMouseClicked(handler);
                row.getChildren().add(b);
            }

            cols.getChildren().add(row);
        }
        getChildren().add(cols);
    }

    /**
     * This method validates the initialization of a Board object. If the number of rows and columns are not equal to 10,
     * throws an exception.
     * @param rowCount number of rows a board is initialized with
     * @param columnCount number of columns a board is initialized with
     */
    private void validate(int rowCount, int columnCount){
        if (rowCount!= 10 || columnCount !=10){
            throw new IllegalArgumentException("This is a game for 10x10 board. My game my rules");
        }
    }

    /**
     * Method to place a Box on the board during the initial setup of the game.
     *
     * <p>This method takes in a Coordinate point as a parameter which corresponds to the respective position on the
     * board where a player wants to place his box. If the player has already placed the desired number of boxes on the
     * board (maxBoxes) and still continues to place the box, the method throws an exception.</p>
     *<p>The method also checks if the player is placing a box on the board or not. If the coordinates are outside of the
     * scope of the board, the method throws an exception</p>
     * @param point The place where the player wants to add his box during the initial setup of the game
     */
    public void positionBox(Coordinates point) {
        if (occupied.size() == maxBoxes) {
            throw new IllegalArgumentException("Cannot specify more than " + maxBoxes);
        }
        if (point.getX() > columnCount || point.getY() > rowCount) {
            throw new IllegalArgumentException("Box should be placed within the board");
        }
        occupied.add(point);
    }

    /**
     * Checks if a coordinate is occupied by a box or not
     * @param coordinate the coordinate or position in the board which is to be checked for whether there's a box or not
     * @return true if the coordinate is occupied by a box. Otherwise, false.
     */
    public boolean isOccupied(Coordinates coordinate) {
        return occupied.contains(coordinate);
    }

    /**
     *This method checks whether the player has made any moves or not.
     *
     * <p>If activeBoard is true and gameModeOn is true, and a player has clicked on a blank box, the method checks
     * if the player's move reveals anything or not. If the player finds the hidden box, that box is removed from the
     * set of occupied(contains the set of hidden boxes) and added to the list of revealedCoordinates. The
     * revealOccupiedBox method is then invoked.</p>
     * <p>If the blank box does not contain the hidden box, the coordinates are simply added to the list of
     * revealedCoordinates</p>
     * <p>If gameModeOn is false, the positionBox method is invoked</p>
     * @param box The particular box that the player clicks
     * @return a boolean variable playerMoved
     */
    public boolean clickBox(Box box) {
        boolean playerMoved = false;
        if (activeBoard) {
            if (gameModeOn) {
                if (!revealedCoordinates.contains(box.getCoordinate())) {
                    if (isOccupied(box.getCoordinate())) {
                        occupied.remove(box.getCoordinate());
                        revealedCoordinates.add(box.getCoordinate());
                        box.revealOccupiedBox();
                    } else {
                        revealedCoordinates.add(box.getCoordinate());
                        box.revealEmptyBox();
                    }
                    playerMoved = true;
                }

            } else {
                positionBox(box.getCoordinate());
                playerMoved = true;
            }
        }
        return playerMoved;
    }

    /**
     * A getter or accessor method for gameModeOn
     * @return true if gameModeOn = true, false otherwise.
     */
    public boolean isGameModeOn() {
        return gameModeOn;
    }

    /**
     * A mutator or setter method for setting gameModeOn to true.
     */
    public void setGameModeOn() {
        gameModeOn = true;
    }

    /**
     * A method to return the total number of places that are occupied by boxes.
     * @return the number of Boxes on the board
     */
    public int placedBoxesNum() {
        return occupied.size();
    }
}
