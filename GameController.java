package com.aishik.findBoxes;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Optional;

/**
 * This is the main game controller which is responsible for the functionality of the game.
 */
public class GameController{
    /**
     * To determine whose turn is it. Turn.PLAYER1 corresponds to player 1's turn and Turn.PLAYER2 corresponds to
     * player 2's turn.
     */
    enum Turn {PLAYER1, PLAYER2}

    /**
     * A variable to store the number of boxes the players want in the game. In the start of the game, a player has to
     * specify how many boxes he wants on the board for the game.
     * <p>For example, if numberOfBoxes is 2, each player must choose 2 boxes in their respective boards</p>
     */
    public int numberOfBoxes;
    /**
     * Board object denoting the board of player1
     */
    protected Board p1Board;
    /**
     * Board object denoting the board of player1
     */
    protected Board p2Board;
    /**
     * Player objects who play the game
     */
    private Player player1, player2;
    /**
     * Text object for displaying information such as number of boxes left, score, turn etc.
     */
    private Text status;
    /**
     * boolean variable to check if the game is over or not. Is set to true for discontinuing the game once an end
     * condition is met.
     */
    private boolean gameOver = false;

    /**
     * Variable to store the player's turn. Is initialized as null as the game has not started yet. By setting currentTurn
     * to Turn.PLAYER1 or Turn.PLAYER2, the player turns can be managed.
     */
    private Turn currentTurn = null;

    /**
     * Button object for starting the game
     */
    private Button startGame;

    /**
     * Variable to store the score gameHistory of previous games
     */
    private gameHistory gameHistory;

    /**
     * This method returns the layout of children in the scene graph. It will be passed as a parameter
     * during the construction of Scene Object.
     * <p> Objects required for the game are constructed here - player1, player2, p1Board, p2Board, player1Display,
     * player2Display, currentGameDisplay, gameHistory, fullDisplay.</p>
     *
     * @return  The layout of the game.
     */
    public Parent createContent() {

        BorderPane root = new BorderPane();
        root.setPrefSize(600, 900);
        numberOfBoxes = Integer.parseInt(getUserInput("Enter number of boxes for the game"));
        int boxLimit = 90;
        if(numberOfBoxes <= 0 || numberOfBoxes > boxLimit) {throw new IllegalArgumentException("Invalid number of boxes");}

        String player1Name = getUserInput("Enter name of first player");
        String player2Name = getUserInput("Enter name of second player");

        player1 = new Player(player1Name);
        player2 = new Player(player2Name);

        p1Board = new Board(10, 10, numberOfBoxes, event -> {
            if (!gameOver) {
                Box box = (Box) event.getSource();
                boolean playerMoved = p1Board.clickBox(box);
                if(playerMoved) {
                    checkIfOver();
                    changeTurn(Turn.PLAYER1);
                    updateStatus();
                }
            }

        });

        HBox player1Display = new HBox(50, p1Board, new Text(player1.getName() + "'s board, populated by " + player2.getName()));

        p2Board = new Board(10, 10, numberOfBoxes, event -> {
            if (!gameOver) {
                Box box = (Box) event.getSource();
                boolean playerMoved = p2Board.clickBox(box);
                if(playerMoved) {
                    checkIfOver();
                    changeTurn(Turn.PLAYER2);
                    updateStatus();
                }
            }
        });

        HBox player2Display = new HBox(50, p2Board, new Text(player2.getName() + "'s board, populated by " + player1.getName()));

        startGame = new Button("Start Game!");
        startGame.setOnMouseClicked(event -> validateStart());

        status = new Text("Initializing game, please set boxes for both players");


        VBox currentGameDisplay = new VBox(50, player1Display, player2Display, startGame, status);
        currentGameDisplay.setAlignment(Pos.CENTER);


        gameHistory = new gameHistory();
        Text history = new Text(this.gameHistory.getText());

        HBox fullDisplay = new HBox(50, currentGameDisplay, history);

        root.setCenter(fullDisplay);

        p1Board.setActiveBoard(true);
        p2Board.setActiveBoard(true);

        return root;
    }

    /**
     * Method to change the turn of the player.
     * This method first checks if the current turn is null or not, if it is null, nothing happens, it simply returns.
     * It then checks if the variable currentTurn(which denotes which player's turn it is currently) is equal to the
     * playerTurn. If it is not, it simply returns, if it is, the turn is changed with the help of a switch statement.
     *
     * <p>The switch statement takes currentTurn as the argument, if the current turn is PLAYER1, the board of player 1
     * is set to be inactive so that player 1 cannot make any more moves(player 1's turn is over). Player 2's turn is set
     * to active(It is now player 2's turn). The currentTurn is then updated corresponding to the Player.</p>
     *
     * @param playerTurn Denotes whose turn is it
     */
    public void changeTurn(Turn playerTurn) {
        if (currentTurn == null) return;
        if (currentTurn != playerTurn) return;

        switch (currentTurn) {
            case PLAYER1:
                p1Board.setActiveBoard(false);
                p2Board.setActiveBoard(true);
                currentTurn = Turn.PLAYER2;
                break;
            case PLAYER2:
                p1Board.setActiveBoard(true);
                p2Board.setActiveBoard(false);
                currentTurn = Turn.PLAYER1;
                break;

        }

    }

    /**
     * This method checks if the game is over or not. If the game is over, it displays the winner and adds the winner
     * to a file.
     * <p>If currentTurn is not null, check whose turn is it and if that turn ends the game. The end condition is that
     * the player in his turn finds all the boxes of the opponent player while the game is on. </p>
     */
    public void checkIfOver() {
        if (currentTurn != null) {
            switch (currentTurn) {
                case PLAYER1:
                    if (p1Board.isGameModeOn() && p1Board.placedBoxesNum() == 0) {
                        showAlert(player1.getName() + " has won!");
                        gameOver = true;
                        gameHistory.addPlayer(player1);
                        gameHistory.persist();
                    }

                    break;
                case PLAYER2:
                    if (p2Board.isGameModeOn() && p2Board.placedBoxesNum() == 0) {
                        showAlert(player2.getName() + " has won!");
                        gameOver = true;
                        gameHistory.addPlayer(player2);
                        gameHistory.persist();
                    }

                    break;
            }
        }

    }

    /**
     * This method creates an alert with the message that was passed to it as an argument. The alert will wait for the
     * user to respond by clicking the OK button.
     * @param message The message which will be displayed in the alert
     */
    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game has ended");
        alert.setHeaderText("Congratulations!");
        alert.setContentText(message);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }

    /**
     * Validates the setup of the game. Each player is expected to put numOfBoxes boxes on the board. If a player tries
     * to start the game before placing all the boxes, an alert would be generated requesting the player to put the
     * required number of boxes on the board.
     * <p>After the validation part, it turns the game mode on for both the player boards and sets the currentTurn to
     * player 1. It also makes the player 2's board inactive so only player 1 can make his move until the turn is
     * changed.</p>
     */
    public void validateStart() {
        if (p1Board.placedBoxesNum() < numberOfBoxes) {
            showAlert("Please place " + numberOfBoxes + " boxes for " + player1.getName());
            return;
        }
        if (p2Board.placedBoxesNum() < numberOfBoxes) {
            showAlert("Please place " + numberOfBoxes + " boxes for " + player2.getName());
            return;
        }
        p1Board.setGameModeOn();
        p2Board.setGameModeOn();
        startGame.setDisable(true);

        currentTurn = Turn.PLAYER1;
        p1Board.setActiveBoard(true);
        p2Board.setActiveBoard(false);
    }

    /**
     * This method shows a dialog with the message that is passed in as argument.
     * @param message The message that is going to be returned
     * @return The message user gives to the function
     */
    public String getUserInput(String message) {
        TextInputDialog dialog = new TextInputDialog();  // create an instance
        dialog.setTitle("Welcome to Find Boxes");
        dialog.setHeaderText("Requesting Input");
        dialog.setContentText(message);
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()) {
            System.out.println(result.get());
            return result.get();
        }
        else throw new IllegalArgumentException("No Input given");
    }

    /**
     * Method to update the status of players and display it in the application.
     * This method is responsible for displaying the player names, current turns corresponding to the players, and their
     * respective scores.
     */
    private void updateStatus() {
        String playerOneCurrentStatus = "";
        String playerTwoCurrentStatus = "";
        if (currentTurn == Turn.PLAYER1) playerOneCurrentStatus = " (current turn)";
        else if (currentTurn == Turn.PLAYER2) playerTwoCurrentStatus = " (current turn)";

        String statusText = player1.getName() + playerOneCurrentStatus + ": " + p1Board.placedBoxesNum() + "  VS  " + player2.getName() + playerTwoCurrentStatus + ": " + p2Board.placedBoxesNum() + "  Max number of boxes = " + numberOfBoxes;
        status.setFont(Font.font(20));
        status.setText(statusText);

    }

}
