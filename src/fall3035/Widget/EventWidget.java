package fall3035.Widget;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class EventWidget extends HBox {

    Text text = new Text();
    CheckBox checkBox = new CheckBox();

    public EventWidget(String text) {
        this(text, true, false);
    }

    public EventWidget(String text, boolean hasLiStyle) {
        this(text, hasLiStyle, true);
    }

    public EventWidget(String str, boolean hasLiStyle, boolean hasCheckBox) {
        super();
        setAlignment(Pos.CENTER_LEFT);
        if (hasLiStyle) {
            Text e = new Text("\u2022 ");//Unicode code
            e.setFont(new Font(12));
            e.setFill(Color.valueOf("#8B4513"));
			getChildren().add(e);
        }
        // TODO: Add color to the string according to its event type
        text.setText(str);
        getChildren().add(text);
        if (hasCheckBox) {
            setSpacing(6);
            getChildren().add(checkBox);
        }
        
    }

	public void addMouseStyle() {

		setOnMouseEntered(new EventHandler<Event>() {
			@Override
			public void handle(Event paramT) {
                setStyle("-fx-background-color: lightblue");
			}
		});

		setOnMouseExited(new EventHandler<Event>() {
			@Override
			public void handle(Event paramT) {
                setStyle("-fx-background-color: white");
			}
		});
	}

	public Button delete(){
        // TODO: Make this delete button more natural
        Button delete = new Button("x");
        delete.setPrefSize(1,1);
        delete.setStyle("-fx-text-fill: red");
        delete.setId("delete");

        getChildren().add(delete);

        return delete;
	}

	public void setStrikethrough(boolean f){
        text.setStrikethrough(f);
    }
    
    public CheckBox getCheckBox(){
        return checkBox;
    }
    
    public Text getText(){
        return text;
    }
}
