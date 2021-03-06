package shoppinglistgui;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.v2.DbxClientV2;
import javafx.scene.control.*;
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
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Optional;

/**
 * A class that contains all of the GUI stuff.
 */
public class Gui {
    private Stage stage;
    private TextField[] amountField;
    private TextField[] itemField;
    private GridPane grid;
    private TextArea allItems;
    private final int FIELDS = 15;
    private StringWriter stringWriter;
    private Button addAllButton;
    private Button removeAllButton;
    private Button saveToFileButton;
    private Button saveToDropboxButton;

    private static final String APP_KEY = "6fmddnkhrmvj2e9";
    private static final String APP_SECRET = "wvm32mdauu7hfiy";

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
        grid.add(allItems, 3, 1, 3, 15);

        Label shoppingListLabel = new Label("Shopping List");
        shoppingListLabel.setFont(new Font("Arial", 20));
        grid.add(shoppingListLabel, 2, 0, 1, 1);

        addItemLabels();
        addItemTextFields();
        addAmountTextFields();
        addButtons();
        addButtonActions();
    }

    /**
     * Adds all buttons to grid.
     */
    private void addButtons() {
        addAllButton = new Button("Add all");
        addAllButton.setStyle("-fx-font: 16 arial; -fx-base: #0a8a0a;");
        GridPane.setConstraints(addAllButton, 0, 16);

        removeAllButton = new Button("Remove all");
        removeAllButton.setStyle("-fx-font: 16 arial; -fx-base: #ff3c3c;");
        GridPane.setConstraints(removeAllButton, 1, 16);

        saveToFileButton = new Button("Save to file");
        saveToFileButton.setStyle("-fx-font: 16 arial; -fx-base: #8a8a8a");
        GridPane.setConstraints(saveToFileButton, 2, 16);

        saveToDropboxButton = new Button("Save to Dropbox");
        saveToDropboxButton.setStyle("-fx-font: 16 arial; -fx-base: #0061fe;");
        GridPane.setConstraints(saveToDropboxButton, 3, 16);

        grid.getChildren().addAll(addAllButton, removeAllButton, saveToFileButton, saveToDropboxButton);
    }

    /**
     * Adds all actions to buttons.
     */
    private void addButtonActions() {
        removeAllButton.setOnAction(action ->  {
            for (int i = 0; i < FIELDS; i++) {
                itemField[i].setText("");
                amountField[i].setText("");
                allItems.clear();
            }
        });

        addAllButton.setOnAction(action ->  {
            for (int i = 0; i < FIELDS; i++) {
                if (!itemField[i].getText().equals("")) {
                    allItems.appendText(itemField[i].getText() + " " + amountField[i].getText() + "\n");
                }
            }
        });

        saveToFileButton.setOnAction(action -> {
            writeToJson();
            try {
                fileChooser();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        saveToDropboxButton.setOnAction(action -> {
            writeToJson();
            try {
                saveToDropbox();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        shadowEffect(addAllButton);
        shadowEffect(removeAllButton);
        shadowEffect(saveToFileButton);
        shadowEffect(saveToDropboxButton);
    }

    /**
     * Adds a shadow effect to a button.
     *
     * @param button button
     */
    private void shadowEffect(Button button) {
        DropShadow shadow = new DropShadow();
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, action -> button.setEffect(shadow));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, action -> button.setEffect(null));
    }

    /**
     * Adds all item labels to grid.
     */
    private void addItemLabels() {
        for (int i = 0; i <= FIELDS; i++) {
            Label itemLabel = new Label("Item " + (i + 1) + ":");
            itemLabel.setFont(new Font("Arial", 16));
            GridPane.setConstraints(itemLabel, 0, i + 1);
            grid.getChildren().add(itemLabel);
        }
    }

    /**
     * Adds all amount textfields to grid.
     */
    private void addAmountTextFields() {
        amountField = new TextField[FIELDS];

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
        itemField = new TextField[FIELDS];

        for (int i = 0; i < FIELDS; i++) {
            TextField textField = new TextField();
            textField.setPromptText("item");
            GridPane.setConstraints(textField, 1, i + 1);
            itemField[i] = textField;
            grid.getChildren().add(textField);
        }
    }

    /**
     * Writes shopping list items and amounts to String in JSON format.
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
     * Opens up a file chooser for the ShoppingList.json file.
     *
     * @throws IOException exception
     */
    private void fileChooser() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("ShoppingList.json");
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

    private boolean uploaded = false;
    private int version;

    /**
     * Uploads the shopping list file to Dropbox.
     *
     * @throws Exception exception
     */
    private void saveToDropbox() throws Exception {
        DbxRequestConfig config = new DbxRequestConfig("ShoppingList");

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
        DbxWebAuth webAuth = new DbxWebAuth(config, appInfo);
        DbxWebAuth.Request webAuthRequest = DbxWebAuth.newRequestBuilder().withNoRedirect().build();

        String url = webAuth.authorize(webAuthRequest);

        Desktop.getDesktop().browse(new URL(url).toURI());

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Upload to Dropbox");
        dialog.setHeaderText("Sign in to Dropbox and click \"Allow\" in browser");
        dialog.setContentText("Please enter your access code:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String accessCode = result.get();
            DbxAuthFinish authFinish = webAuth.finishFromCode(accessCode);
            String accessToken = authFinish.getAccessToken();

            DbxClientV2 client = new DbxClientV2(config, accessToken);
            String fileContents = stringWriter.toString();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContents.getBytes());

            if (!uploaded) {
                client.files().uploadBuilder("/ShoppingList.json").uploadAndFinish(inputStream);
                uploaded = true;
            } else {
                version++;
                client.files().uploadBuilder("/ShoppingList" + version + ".json").uploadAndFinish(inputStream);
            }
        }
    }
}
