package app.Controller;

import app.Model.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ModifyBirthdayController extends BaseController implements Initializable {
    @FXML
    VBox content;
    //    @FXML
//    ToggleButton toggle;
    @FXML
    DatePicker dp1;
    @FXML
    TextField taskName;
    @FXML
    DatePicker dp2;
//    @FXML
//    ChoiceBox<Integer> choiceBox;

    Task task;

    StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        @Override
        public String toString(LocalDate object) {
            if (object != null) {
                return dateFormatter.format(object);
            }
            return "";
        }

        @Override
        public LocalDate fromString(String string) {
            if (string != null && !string.isEmpty()) {
                return LocalDate.parse(string,dateFormatter);
            }
            return null;
        }
    };


    @Override
    public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

        dp1.setConverter(converter);
        dp2.setConverter(converter);

        //toggle.setBackground(new Background(new BackgroundImage(new Image("/fall3035/images/off.png"), null, null, null, new BackgroundSize(50, 45, true, true, true, true))));
//        toggle.setPrefWidth(55);
//        toggle.setPrefHeight(50);
//        toggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
//            if (dp2.getValue() == null) {
//                showAlert("Notification can not be change when deadlineDate is empty!");
//                return;
//            }
////            if (newValue) {
////                toggle.setBackground(new Background(new BackgroundImage(new Image("/fall3035/images/on.png"), null, null, null, new BackgroundSize(50, 45, true, true, true, true))));
////            } else {
////                toggle.setBackground(new Background(new BackgroundImage(new Image("/fall3035/images/off.png"), null, null, null, new BackgroundSize(50, 45, true, true, true, true))));
////            }
//        });
        //choiceBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        task = ((Task)stage.getUserData());
        dp1.setValue(LocalDate.parse(task.getPreferDate()));
        if (task.getDeadlineDate() != null) {
            dp2.setValue(LocalDate.parse(task.getDeadlineDate()));
        }
        taskName.setText(task.getDescription());
        //choiceBox.getSelectionModel().select(task.getPriority()-1);
        //toggle.selectedProperty().set(task.isNotification());
    }

    @FXML
    public void save(){
        LocalDate preferDate = dp1.getValue();
        if (preferDate == null) {
            showAlert("PreferDate can not be empty!");
            return;
        }
        String desc = taskName.getText();
        if (desc.isEmpty()) {
            showAlert("Description can not be empty!");
            return;
        }
        //Integer priority = choiceBox.getSelectionModel().getSelectedItem();

        String deadlineDate = dp2.getValue() == null ? null : dp2.getValue().toString();
        //boolean isNotification = toggle.selectedProperty().get();
        if (deadlineDate != null && preferDate.isAfter(dp2.getValue())) {
            showAlert("DeadlineDate must more than PreferDate");
            return;
        }

        Task modifyTask = new Task(preferDate.toString(), deadlineDate, desc, false, 1,"task");
        modifyTask.setId(task.getId());

        if(model.update(modifyTask)){
            showAlert("Modify Success!");
            back();
        }else {
            showAlert("Modify Fail!");
        }

    }
}
