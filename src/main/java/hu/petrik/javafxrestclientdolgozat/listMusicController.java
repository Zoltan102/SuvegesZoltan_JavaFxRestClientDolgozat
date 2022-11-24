package hu.petrik.javafxrestclientdolgozat;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class listMusicController extends Controller {

    @FXML
    public TableView<Music> musicTable;
    @FXML
    public TableColumn<Music, String> title;
    @FXML
    public TableColumn<Music, String> artist;
    @FXML
    public TableColumn<Music, String> genre;
    @FXML
    public TableColumn<Music, Integer> length;
    @FXML
    public Button btnInsert;
    @FXML
    public Button btnUpdate;
    @FXML
    public Button btnRemove;


    @FXML
    private void init() {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        length.setCellValueFactory(new PropertyValueFactory<>("length"));
        Platform.runLater(() -> {
            try {
                loadMusicFromServer();
            } catch (IOException e){
                error("Couldn't get data from server", e.getMessage());
                Platform.exit();
            }
        });
    }

    private void loadMusicFromServer() throws IOException {
        Response response = RequestHandler.get(App.BASE_URL);
        String content = response.getContent();
        Gson converter = new Gson();
        Music[] music = converter.fromJson(content, Music[].class);
        musicTable.getItems().clear();
        for (Music m : music) {
            musicTable.getItems().add(m);
        }
    }

    @FXML
    public void insertClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("create-people-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            stage.setTitle("Create music");
            stage.setScene(scene);
            stage.show();
            btnInsert.setDisable(true);
            btnUpdate.setDisable(true);
            btnRemove.setDisable(true);
            stage.setOnCloseRequest(event -> {
                btnInsert.setDisable(false);
                btnUpdate.setDisable(false);
                btnRemove.setDisable(false);
                try {
                    loadMusicFromServer();
                } catch (IOException e) {
                    error("An erro occured while communicating with the server!");
                }
            });
        } catch (IOException e) {
            error("Could not load form", e.getMessage());
        }
    }

    @FXML
    public void updateClick(ActionEvent actionEvent) {
        int selectedIndex = musicTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            warning("Please select a person from the list first");
            return;
        }
        Music selected = musicTable.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("update-music-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            stage.setTitle("Update music");
            stage.setScene(scene);
            UpdatePeopleController controller = fxmlLoader.getController();
            controller.setPerson(selected);
            stage.show();
            btnInsert.setDisable(true);
            btnUpdate.setDisable(true);
            btnRemove.setDisable(true);
            stage.setOnCloseRequest(event -> {
                btnInsert.setDisable(false);
                btnUpdate.setDisable(false);
                btnRemove.setDisable(false);
                try {
                    loadMusicFromServer();
                } catch (IOException e){
                    error("An error occured while communicating with the server!");
                }
            });
        } catch (IOException e) {
            error("Could not load form", e.getMessage());
        }
    }

    @FXML
    public void removeClick(ActionEvent actionEvent) {
        int selectedIndex = musicTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            warning("Please select a person from the list first");
            return;
        }
        Music selected = musicTable.getSelectionModel().getSelectedItem();
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText(String.format("Are you sure you want to delete %s?", selected.getTitle()));
        Optional<ButtonType> optionalButtonType = confirmation.showAndWait();
        if (optionalButtonType.isEmpty()){
            System.err.println("Unknown error occured!");
            return;
        }
        ButtonType clickedButton = optionalButtonType.get();
        if (clickedButton.equals(ButtonType.OK)){
            String url = App.BASE_URL + "/" + selected.getId();
            try {
                RequestHandler.delete(url);
                loadMusicFromServer();
            } catch (IOException e){
                error("An error occurred while communicating with the server!");
            }
        }
    }
}