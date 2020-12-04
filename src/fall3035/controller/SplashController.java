package fall3035.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SplashController extends BaseController implements Initializable {

    @FXML
    VBox content;
    @Override
    public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
        stage.initStyle(StageStyle.UNDECORATED);
        FadeTransition fade = new FadeTransition(Duration.seconds(3), content);
        fade.setFromValue(1);
        fade.setToValue(0.5);
        fade.setCycleCount(1);

        fade.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                stage.hide();
                BaseController.loadView("/fall3035/View/TaskList.fxml",new Stage(), model);
            }
        });

        fade.play();
    }

}
