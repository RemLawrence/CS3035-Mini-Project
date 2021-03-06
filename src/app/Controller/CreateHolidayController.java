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

public class CreateHolidayController extends Controller implements Initializable {
    @FXML
    VBox content;
//    @FXML
//    ToggleButton toggle;
    @FXML
    DatePicker dp1;
    @FXML
    TextField holidayName;
    @FXML
    DatePicker dp2;
//    @FXML
//    ChoiceBox<Integer> choiceBox;


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
//        choiceBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9));
//        choiceBox.getSelectionModel().select(0);
    }

    @FXML
    public void save(){
        LocalDate preferDate = dp1.getValue();
        if (preferDate == null) {
            showAlert("PreferDate can not be empty!");
            return;
        }
        String desc = holidayName.getText();
        if (desc.isEmpty()) {
            showAlert("Description can not be empty!");
            return;
        }
        //Integer priority = choiceBox.getSelectionModel().getSelectedItem();
        Integer priority = 1;


        String deadlineDate = dp2.getValue() == null ? null : dp2.getValue().toString();
        //boolean isNotification = toggle.selectedProperty().get();
        boolean isNotification = false;
        if (deadlineDate != null && preferDate.isAfter(dp2.getValue())) {
            showAlert("DeadlineDate must more than PreferDate");
            return;
        }
        Task task = new Task(preferDate.toString(), deadlineDate, desc, false, priority,"holiday");
        if(model.add(task)){
            showAlert("Add Success!");
            back();
        }else {
            showAlert("Add Fail!");
        }

    }

}
