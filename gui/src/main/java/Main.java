import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import jsonparser.JsonArray;
import jsonparser.JsonObject;
import jsonparser.JsonWriter;

import java.io.FileWriter;

public class Main extends Application {
    private Stage stage;
    private TextField[] amountField;
    private TextField[] itemField;

    public static void main(String[] args) {
        System.out.println("Author: Juho Taakala");
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

        Label shoppingList = new Label("Shopping List");
        shoppingList.setFont(new Font("Arial", 20));
        GridPane.setConstraints(shoppingList, 0, 0);

        addItemLabels(grid);
        addItemTextFields(grid);
        addAmountTextFields(grid);
        addButtons(grid);

        grid.getChildren().addAll(shoppingList);

        Scene scene = new Scene(grid, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Adds all buttons to grid.
     *
     * @param grid GridPane
     */
    public void addButtons(GridPane grid) {
        Button addAll = new Button("Add all");
        addAll.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
        GridPane.setConstraints(addAll, 0, 11);

        Button removeAll = new Button("Remove all");
        removeAll.setStyle("-fx-font: 16 arial; -fx-base: #f08080;");
        GridPane.setConstraints(removeAll, 1, 11);

        Button saveToFile = new Button("Save to file");
        saveToFile.setStyle("-fx-font: 16 arial; -fx-base: #c0c0c0;");
        GridPane.setConstraints(saveToFile, 2, 11);

        removeAll.setOnAction(e ->  {
            for (int i = 0; i < 10; i++) {
                itemField[i].setText("");
                amountField[i].setText("");
            }
        });

        DropShadow shadow = new DropShadow();

        addAll.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> addAll.setEffect(shadow));
        addAll.addEventHandler(MouseEvent.MOUSE_EXITED, e -> addAll.setEffect(null));

        removeAll.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> removeAll.setEffect(shadow));
        removeAll.addEventHandler(MouseEvent.MOUSE_EXITED, e -> removeAll.setEffect(null));

        saveToFile.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> saveToFile.setEffect(shadow));
        saveToFile.addEventHandler(MouseEvent.MOUSE_EXITED, e -> saveToFile.setEffect(null));

        grid.getChildren().addAll(addAll, removeAll, saveToFile);
    }

    /**
     * Adds all item labels to grid.
     *
     * @param grid GridPane
     */
    public void addItemLabels(GridPane grid) {
        String[] items = {"Item 1:", "Item 2:", "Item 3:", "Item 4:", "Item 5:",
                "Item 6:", "Item 7:", "Item 8:", "Item 9:", "Item 10:"};

        for (int i = 0; i < items.length; i++) {
            Label itemLabel = new Label(items[i]);
            itemLabel.setFont(new Font("Arial", 16));
            GridPane.setConstraints(itemLabel, 0, i + 1);
            grid.getChildren().add(itemLabel);
        }
    }

    /**
     * Adds all amount textfields to grid.
     *
     * @param grid GridPane
     */
    public void addAmountTextFields(GridPane grid) {
        amountField = new TextField[10];

        for (int i = 0; i < 10; i++) {
            TextField textField = new TextField();
            textField.setPromptText("amount");
            GridPane.setConstraints(textField, 2, i + 1);
            amountField[i] = textField;
            grid.getChildren().add(textField);
        }
    }

    /**
     * Adds all item textfields to grid.
     *
     * @param grid GridPane
     */
    public void addItemTextFields(GridPane grid) {
        itemField = new TextField[10];

        for (int i = 0; i < 10; i++) {
            TextField textField = new TextField();
            textField.setPromptText("item");
            GridPane.setConstraints(textField, 1, i + 1);
            itemField[i] = textField;
            grid.getChildren().add(textField);
        }
    }

    /*public void writeToJson() {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add("String");
        jsonArray.add(1);
        jsonArray.add(1.1);
        jsonArray.add(true);
        jsonArray.add(null);

        JsonObject jsonObject = new JsonObject();
        jsonObject.put("string", "name");
        jsonObject.put("int", 1);
        jsonObject.put("double", 1.5);
        jsonObject.put("boolean", true);
        jsonObject.put("null", null);
        jsonObject.put("list", jsonArray);

        try (JsonWriter writer = new JsonWriter(new FileWriter("values.txt"))) {
            writer.objectToJson(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
