package fall3035.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import fall3035.Model.Task;
import fall3035.View.CalendarPane;
import fall3035.View.ItemPane;
import fall3035.View.CalendarPane.Cell;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MainController extends BaseController implements Initializable {

    @FXML
    ScrollPane content;
    CalendarPane calendarPane;

    @Override
    public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

        calendarPane = new CalendarPane();
        VBox flowPane = new VBox(calendarPane);
        flowPane.setAlignment(Pos.TOP_CENTER);

        content.setContent(flowPane);
        content.setFitToWidth(true);
        update();
    }

    private void update(){
        for (Task task : model.getTasks()) {

            ItemPane addTodo = calendarPane.addTodo(task.getPreferDate(), task.getDescription());
            if (addTodo != null) {
                if (task.isFinish()) {
                    addTodo.setStrikethrough(true);
                }
                if (!task.isFinish() && task.isNotification() && task.getDeadlineDate() != null) {
                    if (LocalDate.parse(task.getDeadlineDate()).isBefore(LocalDate.now())) {
                    	Background background = new Background(new BackgroundFill(Color.valueOf("#FF4500"), null, null));
                    	addTodo.setBackground(background);
					}else if (LocalDate.now().toString().equals(task.getDeadlineDate())) {
						Background background = new Background(new BackgroundFill(Color.valueOf("#B0C4DE"), null, null));
						addTodo.setBackground(background);
					}
                }
            }
        }
        ObservableList<Node> cells = ((GridPane) calendarPane.getChildren().get(0)).getChildren();
        for (Node node : cells) {
            if (node instanceof Cell && node.getOnMouseClicked() == null) {
                node.setOnMouseClicked(e -> {
                    stage.setUserData(node.getUserData());
                    loadView("/fall3035/View/TaskList.fxml", stage, model);
                });
            }
        }
    }

    @FXML
    public void previous() {
        calendarPane.previous();
        update();
    }

    @FXML
    public void next() {
        calendarPane.next();
        update();
    }

}
