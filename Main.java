package com.aishik.findBoxes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    /**
     * This is an object of the gameController class. We'll use this controller object to get the functionality from the
     * gameController class
     */
    GameController controller = new GameController();
    /**
     *This method creates a new scene which is the container for all content in the scene graph. The scene is initialized
     * with the createContent() method which has the layout of the scene. We then use a Stage object primaryStage which
     * is the topmost javaFX container to set the title of the application and the scene. We then use the show() method
     * to display the container and set activeBoard to true for both the Board objects.
     *<p>setActiveBoard is a mutator method which sets the variable activeBoard to a boolean value.</p>
     * @param primaryStage the top level JavaFX container
     */
    public void start(Stage primaryStage) {
        Scene scene = new Scene(controller.createContent());
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    /**
     * The main function which launches the application
     * @param args Array of strings passed as parameters when running the application through command line
     */
    public static void main(String[] args) {
        launch(args);
    }
}
