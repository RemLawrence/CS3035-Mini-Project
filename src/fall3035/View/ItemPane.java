package fall3035.View;

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

public class ItemPane extends HBox {

    Text text = new Text();
    CheckBox checkBox = new CheckBox();

    public ItemPane(String text) {
        this(text, true, false);
    }

    public ItemPane(String text, boolean hasLiStyle) {
        this(text, hasLiStyle, true);
    }

    public ItemPane(String str, boolean hasLiStyle, boolean hasCheckBox) {
        super();
        setAlignment(Pos.CENTER_LEFT);
        if (hasLiStyle) {
            Text e = new Text("\u2022 ");//Unicode code
            e.setFont(new Font(12));
            e.setFill(Color.valueOf("#8B4513"));
			getChildren().add(e);
        }
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
				Background background = new Background(new BackgroundFill(
						Color.valueOf("ffbb008a"), null, null));
				setBackground(background);
			}
		});
		setOnMouseExited(new EventHandler<Event>() {

			@Override
			public void handle(Event paramT) {
					Background background = new Background(new BackgroundFill(
							Color.valueOf("#fff"), null, null));
					setBackground(background);
			}
		});
	}
	public Button addDelete(){
        Button e = new Button("X");
        e.setId("delete");
        getChildren().add(e);
        return e;
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
