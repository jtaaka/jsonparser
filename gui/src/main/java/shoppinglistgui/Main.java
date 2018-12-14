package shoppinglistgui;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.v2.DbxClientV2;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Optional;

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
