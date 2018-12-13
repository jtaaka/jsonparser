package shoppinglistgui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Main class of the shopping list application.
 */
public class Main extends Application {

    public static void main(String[] args) {
        System.out.println("Author: Juho Taakala");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Shopping List");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Gui gui = new Gui(grid, primaryStage);

        Scene scene = new Scene(grid, 640, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
