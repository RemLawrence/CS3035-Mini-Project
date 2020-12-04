package fall3035.Controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import fall3035.Model.Task;
import fall3035.View.ItemPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

public class TaskListController extends BaseController implements Initializable {

    @FXML
    VBox contentDay;
    @FXML
    VBox contentDeadline;
    @FXML
    Label lb_day;

    @Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
    	String day;
    	if(stage.getUserData()==null){
			 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		     Date date = new Date();
		     day=dateFormat.format(date);
		}
    	else
    		day = stage.getUserData() + "";

    	model.getTasks();

		lb_day.setText(day);
		contentDay.getChildren().clear();
		contentDeadline.getChildren().clear();
		List<Task> noDeadLineTasks = model.getNoDeadLineTasks(day);

		show(noDeadLineTasks, contentDay);

		List<Task> deadLineTasks = model.getDeadLineTasks(day);
		show(deadLineTasks, contentDeadline);
	}
    private void show(List<Task> tasks,VBox content){
   	 for (int i = 0; i < tasks.size(); i++) {

            Task task = tasks.get(i);
            ItemPane itemPane = new ItemPane((i + 1) + " . " + task.getDescription(), false);
            Background background = new Background(new BackgroundFill(Color.valueOf("#fff"), null, null));
            itemPane.setBackground(background);
            itemPane.addMouseStyle();
            itemPane.getText().setFont(Font.font("", FontWeight.BOLD, 12));
            itemPane.getText().wrappingWidthProperty().set(430);
            if (task.isFinish()) {
                itemPane.getCheckBox().selectedProperty().set(true);
                itemPane.setStrikethrough(true);
            }
            itemPane.getCheckBox().selectedProperty().addListener((observable, oldValue, newValue) -> {
                itemPane.setStrikethrough(newValue);
                task.setFinish(newValue);
                model.update(task);
                initialize(null, null);
            });
			itemPane.addDelete().setOnAction(e -> {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setContentText("Delete?");
				alert.setResultConverter(new Callback<ButtonType, ButtonType>() {

					@Override
					public ButtonType call(ButtonType buttonType) {
						if (buttonType == ButtonType.OK) {
							itemPane.getChildren().clear();
							model.delete(task);
							initialize(null, null);
						}
						return buttonType;
					}
				});
				alert.showAndWait();
			});
            itemPane.setOnMouseClicked(e-> {
                    stage.setUserData(task);
                    loadView("/fall3035/View/TaskModify.fxml", stage, model);
            });
            content.getChildren().add(itemPane);
        }
   }
}
