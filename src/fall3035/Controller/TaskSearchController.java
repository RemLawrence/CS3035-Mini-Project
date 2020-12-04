package fall3035.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fall3035.Model.Task;
import fall3035.Widget.EventWidget;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class TaskSearchController extends BaseController implements Initializable {

    @FXML
    VBox content;
    @FXML
    TextField day;

    @Override
    public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

    }

    private void show(List<Task> tasks) {
        content.getChildren().clear();
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);

            EventWidget eventWidget = new EventWidget((i + 1) + " . " + task.getDescription()+"\n\t"+task.getPreferDate(), false);
            Background background = new Background(new BackgroundFill(Color.valueOf("#fff"), null, null));
            eventWidget.setBackground(background);
            eventWidget.addMouseStyle();
            eventWidget.getText().wrappingWidthProperty().set(430);
            eventWidget.getText().setFont(Font.font("", FontWeight.BOLD, 12));
            if (task.isFinish()) {
                eventWidget.getCheckBox().selectedProperty().set(true);
                eventWidget.setStrikethrough(true);
            }
            eventWidget.getCheckBox().selectedProperty().addListener((observable, oldValue, newValue) -> {
                eventWidget.setStrikethrough(newValue);
                task.setFinish(newValue);
                model.update(task);
            });
            eventWidget.delete().setOnMouseClicked(new EventHandler<Event>() {

                @Override
                public void handle(Event event) {
                    eventWidget.getChildren().clear();
                    model.delete(task);
                }
            });
            content.getChildren().add(eventWidget);
        }
    }

    @FXML
    public void search() {
        String num = day.getText();
        if (!"".equals(num)) {
            try {

                int valueOf = Integer.valueOf(num);
                if (valueOf < 0) {
                    showAlert("Day must more than 0");
                    return;
                }
                List<Task> search = model.search(valueOf, null);
                show(search);
            } catch (Exception e) {
            }
        }
    }

    @FXML
    public void byDate() {
        String num = day.getText();
        if (!"".equals(num)) {
            try {

                int valueOf = Integer.valueOf(num);
                if (valueOf < 0) {
                    showAlert("Day must more than 0");
                    return;
                }
                List<Task> search = model.search(valueOf, "preferDate");
                show(search);
            } catch (Exception e) {
            }
        }
    }

    @FXML
    public void byDeadline() {
        String num = day.getText();
        if (!"".equals(num)) {
            try {

                int valueOf = Integer.valueOf(num);
                if (valueOf < 0) {
                    showAlert("Day must more than 0");
                    return;
                }
                List<Task> search = model.search(valueOf, "deadlineDate");
                show(search);
            } catch (Exception e) {
            }
        }
    }

    @FXML
    public void byPriority() {
        String num = day.getText();
        if (!"".equals(num)) {
            try {

                int valueOf = Integer.valueOf(num);
                if (valueOf < 0) {
                    showAlert("Day must more than 0");
                    return;
                }
                List<Task> search = model.search(valueOf, "priority");
                show(search);
            } catch (Exception e) {
            }
        }
    }

    
}
