package shoppinglistgui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import jsonparser.JsonObject;
import jsonparser.JsonWriter;

import java.io.*;

public class Main extends Application {
    private Stage stage;
    private TextField[] amountField;
    private TextField[] itemField;
    private GridPane grid;
    private TextArea allItems;
    private final int FIELDS = 10;
    private File JsonFile;
    private FileWriter fileWriter;

    public static void main(String[] args) {
        System.out.println("Author: Juho Taakala");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Shopping List");

        grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        allItems = new TextArea();
        allItems.setStyle("-fx-font: 14 arial;");
        grid.add(allItems, 3, 1, 3, 10);

        Label shoppingList = new Label("Shopping List");
        shoppingList.setFont(new Font("Arial", 20));
        GridPane.setConstraints(shoppingList, 0, 0);

        addItemLabels();
        addItemTextFields();
        addAmountTextFields();
        addButtons();

        grid.getChildren().addAll(shoppingList);

        Scene scene = new Scene(grid, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Adds all buttons to grid.
     */
    public void addButtons() {
        Button addAll = new Button("Add all");
        addAll.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
        GridPane.setConstraints(addAll, 0, 11);

        Button removeAll = new Button("Remove all");
        removeAll.setStyle("-fx-font: 16 arial; -fx-base: #f08080;");
        GridPane.setConstraints(removeAll, 1, 11);

        Button saveToFile = new Button("Save to file");
        saveToFile.setStyle("-fx-font: 16 arial; -fx-base: #c0c0c0;");
        GridPane.setConstraints(saveToFile, 2, 11);

        removeAll.setOnAction(action ->  {
            for (int i = 0; i < FIELDS; i++) {
                itemField[i].setText("");
                amountField[i].setText("");
                allItems.clear();
            }
        });

        addAll.setOnAction(action ->  {
            for (int i = 0; i < FIELDS; i++) {
                if (!itemField[i].getText().equals("")) {
                    allItems.appendText(itemField[i].getText() + " " + amountField[i].getText() + "\n");
                }
            }
        });

        saveToFile.setOnAction(action -> {
            writeToJson();
            try {
                fileChooser();
            } catch (Exception e) {
                e.printStackTrace();
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
     */
    public void addItemLabels() {
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
     */
    public void addAmountTextFields() {
        amountField = new TextField[10];

        for (int i = 0; i < FIELDS; i++) {
            TextField textField = new TextField();
            textField.setPromptText("amount");
            GridPane.setConstraints(textField, 2, i + 1);
            amountField[i] = textField;
            grid.getChildren().add(textField);
        }
    }

    /**
     * Adds all item textfields to grid.
     */
    public void addItemTextFields() {
        itemField = new TextField[10];

        for (int i = 0; i < FIELDS; i++) {
            TextField textField = new TextField();
            textField.setPromptText("item");
            GridPane.setConstraints(textField, 1, i + 1);
            itemField[i] = textField;
            grid.getChildren().add(textField);
        }
    }

    /**
     * Writes shopping list items to txt file in JSON format.
     */
    public void writeToJson() {
        JsonObject jsonObject = new JsonObject();

        for (int i = 0; i < FIELDS; i++) {
            if (!itemField[i].getText().equals("") && !amountField[i].getText().equals("")) {
                jsonObject.put(itemField[i].getText(), amountField[i].getText());
            }
        }

        try (JsonWriter writer = new JsonWriter(fileWriter = new FileWriter(JsonFile = new File("values.txt")))) {
            writer.objectToJson(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fileChooser() throws IOException {
        FileChooser fileChooser = new FileChooser();

        /*//Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);*/

        fileChooser.setInitialFileName("values.txt");

        File fileToSave = fileChooser.showSaveDialog(stage);

        BufferedReader reader = new BufferedReader(new FileReader(
                JsonFile.getAbsolutePath()));

        String line = reader.readLine();
        SaveFile(line, fileToSave);
        reader.close();

    }

    private void SaveFile(String content, File file) throws IOException {
        FileWriter fileWriter;
        fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
    }
}
