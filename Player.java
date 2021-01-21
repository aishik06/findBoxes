package com.aishik.findBoxes;

import java.io.Serializable;

/**
 * Class for player
 */
public class Player implements Serializable {
    /**
     * A private instance variable name which will contain the name of the Player object.
     */
    private final String name;

    /**
     * This is the accessor or getter method for the instance variable name
     * @return the name of the Player object
     */
    public String getName() {
        return name;
    }

    /**
     * This is the constructor for the Player class. Constructs the Player objects
     * @param name name that is to be passed to the constructor to create a Player object with the name
     */
    public Player(String name) {
        this.name = name;
    }
}
