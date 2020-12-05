package app;

import java.util.Locale;

import app.Controller.BaseController;
import app.Model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BaseController.loadView("/app/View/Splash.fxml",stage, new Model());
    }

}
