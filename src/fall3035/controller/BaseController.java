package fall3035.controller;

import java.io.IOException;

import fall3035.model.Model;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BaseController {
    Model model;
    Stage stage;

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void loadView(String fxml, Stage stage, Model model) {
        Parent root = null;
        try {
            root = FXMLLoader.load(BaseController.class.getResource(fxml), null, null, c -> {
                try {
                    BaseController controller = (BaseController) c.newInstance();
                    controller.setModel(model);
                    controller.setStage(stage);
                    return controller;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("TODO");
        stage.centerOnScreen();
        stage.show();

        FadeTransition fade = new FadeTransition(Duration.seconds(0.3), root);
        fade.setFromValue(0.2);
        fade.setToValue(1);
        fade.setCycleCount(1);
        fade.play();
    }

    public void showAlert(String msg){
        new Alert(AlertType.INFORMATION, msg).showAndWait();
    }

    @FXML
    public void add() {
        loadView("/fall3035/view/TaskAdd.fxml", stage, model);
    }

    @FXML
    public void search() {
        loadView("/fall3035/view/TaskSearch.fxml", stage, model);
    }

    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    public void about() {
        loadView("/fall3035/view/about.fxml", stage, model);
    }

    @FXML
    public void help() {
        loadView("/fall3035/view/help.fxml", stage, model);
    }

    @FXML
    public void back() {
        loadView("/fall3035/view/Main.fxml", stage, model);
    }
}
