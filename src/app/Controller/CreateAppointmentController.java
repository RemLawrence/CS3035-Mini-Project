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

public class CreateAppointmentController extends Controller implements Initializable{
    @FXML
    VBox content;
    @FXML
    DatePicker dp1;
    @FXML
    TextField appointmentName;
    @FXML
    DatePicker dp2;

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
    }

    @FXML
    public void save(){
        LocalDate preferDate = dp1.getValue();
        if (preferDate == null) {
            showAlert("PreferDate can not be empty!");
            return;
        }
        String desc = appointmentName.getText();
        if (desc.isEmpty()) {
            showAlert("Description can not be empty!");
            return;
        }

        Integer priority = 1;

        String deadlineDate = dp2.getValue() == null ? null : dp2.getValue().toString();

        boolean isNotification = false;
        if (deadlineDate != null && preferDate.isAfter(dp2.getValue())) {
            showAlert("DeadlineDate must more than PreferDate");
            return;
        }
        Task task = new Task(preferDate.toString(), deadlineDate, desc, false, priority, "appointment");
        if(model.add(task)){
            showAlert("Add Success!");
            back();
        }else {
            showAlert("Add Fail!");
        }
    }
}

