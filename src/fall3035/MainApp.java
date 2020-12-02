package fall3035;

import java.util.Locale;

import fall3035.controller.BaseController;
import fall3035.model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BaseController.loadView("/fall3035/view/Splash.fxml",stage, new Model());
    }

}
