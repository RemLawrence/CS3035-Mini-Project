package fall3035.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import fall3035.Model.Task;
import fall3035.Widget.CalendarWidget;
import fall3035.Widget.EventWidget;
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
    CalendarWidget calendarWidget;

    @Override
    public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

        calendarWidget = new CalendarWidget();
        VBox flowPane = new VBox(calendarWidget);
        flowPane.setAlignment(Pos.TOP_CENTER);

        content.setContent(flowPane);
        content.setFitToWidth(true);
        update();
    }

    private void update(){
        for (Task task : model.getTasks()) {

            EventWidget addTodo = calendarWidget.addTodo(task.getPreferDate(), task.getDescription());
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
        ObservableList<Node> cells = ((GridPane) calendarWidget.getChildren().get(0)).getChildren();
        for (Node node : cells) {
            if (node instanceof CalendarWidget.CalendarCell && node.getOnMouseClicked() == null) {
                node.setOnMouseClicked(e -> {
                    stage.setUserData(node.getUserData());
                    loadView("/fall3035/View/EventList.fxml", stage, model);
                });
            }
        }
    }

    @FXML
    public void previous() {
        calendarWidget.previous();
        update();
    }

    @FXML
    public void next() {
        calendarWidget.next();
        update();
    }

}
