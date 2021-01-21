package com.aishik.findBoxes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for the game history. Shows which player won the matches in the previous games.
 * It only shows the name of the previous winners and not the score associated with the winners.
 */
public class gameHistory {
    /**
     * List of players who play the game
     */
    List<Player> playerList;

    /**
     *Constructor for the gameHistory class. Initializes and loads the playerList.
     */
    public gameHistory() {
        playerList = new ArrayList<>();
        load();
    }

    /**
     * A variable that stores the file path as a String
     */
    private final String fileName = File.pathSeparator+ "tmp" +File.pathSeparator +"temp_leaderboard";

    /**
     * This method loads the player names from the file to the playerList
     */
    private void load()  {
        try {
            File javaFile = new File(fileName);
            if (javaFile.exists()) {
                FileInputStream fileIn = new FileInputStream(fileName);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                playerList  = (ArrayList<Player>) in.readObject();
            }
        }
        catch (Exception e) {
            System.out.println("Error while loading file "+ fileName + e);
        }
    }

    /**
     * When a player wins a game, this method is used to add the winner to the playerList
     * @param p Player object that would be added to the playerList
     */
    public void addPlayer(Player p) {
        playerList.add(p);
    }

    /**
     * This method writes to the file which contains the names of previous winners
     */
    public void persist() {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(playerList);
            oos.close();
            fos.close();
        }catch (Exception e) {
            System.out.println("Error while writing to file "+ fileName + e);
        }
    }

    /**
     * This method displays the names of previous winners under the GAME HISTORY
     * @return A String of previous winners
     */
    public String getText() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GAME HISTORY\n");
        for(Player player: playerList) {
            stringBuilder.append(player.getName()).append(" won!\n");
        }
        return stringBuilder.toString();
    }
}
