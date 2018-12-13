package shoppinglistgui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jsonparser.JsonObject;
import jsonparser.JsonWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

/**
 * A class that contains all of the GUI stuff.
 */
public class Gui {
    private Stage stage;
    private TextField[] amountField;
    private TextField[] itemField;
    private GridPane grid;
    private TextArea allItems;
    private final int FIELDS = 10;
    private StringWriter stringWriter;
    private Button addAll;
    private Button removeAll;
    private Button saveToFile;

    /**
     * Constructs the Gui class.
     *
     * @param grid GridPane
     * @param stage Stage
     */
    public Gui(GridPane grid, Stage stage) {
        this.grid = grid;
        this.stage = stage;

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
        addButtonActions();

        grid.getChildren().addAll(shoppingList);
    }

    /**
     * Adds all buttons to grid.
     */
    private void addButtons() {
        addAll = new Button("Add all");
        addAll.setStyle("-fx-font: 16 arial; -fx-base: #b6e7c9;");
        GridPane.setConstraints(addAll, 0, 11);

        removeAll = new Button("Remove all");
        removeAll.setStyle("-fx-font: 16 arial; -fx-base: #f08080;");
        GridPane.setConstraints(removeAll, 1, 11);

        saveToFile = new Button("Save to file");
        saveToFile.setStyle("-fx-font: 16 arial; -fx-base: #c0c0c0;");
        GridPane.setConstraints(saveToFile, 2, 11);

        grid.getChildren().addAll(addAll, removeAll, saveToFile);
    }

    /**
     * Adds all actions to buttons.
     */
    private void addButtonActions() {
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
            } catch (IOException e) {
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
    }

    /**
     * Adds all item labels to grid.
     */
    private void addItemLabels() {
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
    private void addAmountTextFields() {
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
    private void addItemTextFields() {
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
    private void writeToJson() {
        JsonObject jsonObject = new JsonObject();

        for (int i = 0; i < FIELDS; i++) {
            if (!itemField[i].getText().equals("") && !amountField[i].getText().equals("")) {
                jsonObject.put(itemField[i].getText(), amountField[i].getText());
            }
        }

        try (JsonWriter writer = new JsonWriter(stringWriter = new StringWriter())) {
            writer.objectToJson(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens up a file chooser for the ShoppingList.txt.
     *
     * @throws IOException exception
     */
    private void fileChooser() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("ShoppingList.txt");
        File fileToSave = fileChooser.showSaveDialog(stage);
        SaveFile(stringWriter.toString(), fileToSave);
    }

    /**
     * Saves StringWriter content to new file.
     *
     * @param content string content from StringWriter
     * @param file file to save
     * @throws IOException exception
     */
    private void SaveFile(String content, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
    }
}
