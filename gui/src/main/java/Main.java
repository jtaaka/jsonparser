import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
    Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Shopping List");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        String[] items = { "Item 1:", "Item 2:", "Item 3:", "Item 4:", "Item 5:",
                            "Item 6:", "Item 7:", "Item 8:", "Item 9:", "Item 10:"};

        Label shoppingList = new Label("Shopping List");
        shoppingList.setFont(new Font("Arial", 20));
        GridPane.setConstraints(shoppingList, 0, 0);

        // add all labels
        for (int i = 0; i < items.length; i++) {
            Label itemLabel = new Label(items[i]);
            itemLabel.setFont(new Font("Arial", 16));
            GridPane.setConstraints(itemLabel, 0, i +1);
            grid.getChildren().add(itemLabel);
        }

        TextField[] itemField = new TextField[10];

        // add all item textfields
        for (int i = 0; i < 10; i++) {
            TextField textField = new TextField();
            textField.setPromptText("item");
            GridPane.setConstraints(textField, 1, i +1);
            itemField[i] = textField;
            grid.getChildren().add(textField);
        }

        TextField[] amountField = new TextField[10];

        // add all amount textfields
        for (int i = 0; i < 10; i++) {
            TextField textField = new TextField();
            textField.setPromptText("amount");
            GridPane.setConstraints(textField, 2, i +1);
            amountField[i] = textField;
            grid.getChildren().add(textField);
        }

        // how to access
        // itemField[5].setPromptText("item5");
        // itemField[1].setText("testi");
        // System.out.println(itemField[1].getText());

        Button addAll = new Button("Add all");
        GridPane.setConstraints(addAll, 0, 11);

        Button removeAll = new Button("Remove all");
        GridPane.setConstraints(removeAll, 1, 11);

        Button saveToFile = new Button("Save to file");
        GridPane.setConstraints(saveToFile, 2, 11);

        grid.getChildren().addAll(shoppingList, addAll, removeAll, saveToFile);

        Scene scene = new Scene(grid, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
