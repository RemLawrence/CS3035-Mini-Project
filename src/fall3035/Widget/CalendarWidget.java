package fall3035.Widget;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CalendarWidget extends VBox {
	private static final int CELL_LENGTH = 99;
	Calendar calendar;

	public CalendarWidget() {
		setPrefWidth(USE_PREF_SIZE);
		setStyle("-fx-background-color: lightblue");
		setAlignment(Pos.CENTER);

		calendar = Calendar.getInstance();
		draw(calendar);
		setPadding(new Insets(10));
	}

	public static class CalendarCell extends VBox {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String day;
		Label label;

		public CalendarCell(String text, String day) {
			this.day = day;
			//date
			label = new Label(text);
			label.setFont(Font.font(null, FontWeight.BOLD, 12));
			label.setPrefWidth(CELL_LENGTH);
			label.setAlignment(Pos.CENTER_RIGHT);

			setUserData(day);
			setMinHeight(50);
			Border b = new Border(new BorderStroke(Color.valueOf("#fb9"),
					BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
					BorderWidths.DEFAULT));
			setBorder(b);
			getChildren().add(label);

			if (day.equals(sdf.format(new Date()))) {
				Background background = new Background(new BackgroundFill(
						Color.valueOf("#fb0"), null, null));
				setBackground(background);
			}

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
					if (!day.equals(sdf.format(new Date()))) {
						Background background = new Background(new BackgroundFill(Color.LIGHTBLUE, null, null));
						setBackground(background);
					} else {
						Background background = new Background(new BackgroundFill(
								Color.valueOf("#fb0"), null, null));
						setBackground(background);
					}
				}
			});
		}
	}

	public EventWidget addTodo(String day, String title) {
		ObservableList<Node> cells = ((GridPane) getChildren().get(0))
				.getChildren();
		for (Node node : cells) {
			if (node instanceof CalendarCell) {
				CalendarCell c = (CalendarCell) node;
				if (day.equals(c.day)) {
					if (c.getChildren().size() > 3) {
						return null;
					}
					if (title.length() > 16) {
						title = title.substring(0, 16) + "..";
					}
					EventWidget eventWidget = new EventWidget(title);
					eventWidget.getText().setFont(
							Font.font("", FontWeight.BOLD, 11));
					eventWidget.getText().setFill(Paint.valueOf("#008080"));
					eventWidget.getText().setWrappingWidth(88);
					eventWidget.setPrefWidth(CELL_LENGTH);
					c.getChildren().add(eventWidget);
					return eventWidget;
				}
			}
		}
		return null;
	}

	public void draw(Calendar calendar) {

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int allDay = calendar.getActualMaximum(Calendar.DATE);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;

		GridPane gridPane = new GridPane();

		String[] week = new String[] {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};

		Text title = new Text(String.format("%d-%02d", year, month));
		title.setStroke(Color.GREY);
		title.setFont(new Font(30));
		gridPane.setAlignment(Pos.CENTER);
		gridPane.add(title, 0, 0);
		GridPane.setColumnSpan(title, 7);
		GridPane.setHalignment(title, HPos.CENTER);

		for (int i = 0; i < week.length; i++) {
			Label label = new Label(week[i]);
			if (i == 0 || i == 6) {
				label.setTextFill(Color.RED);
			}
			VBox vBox = new VBox(label);
			vBox.setPrefHeight(40);
			Background background = new Background(new BackgroundFill(Color.LIGHTBLUE, null, null));
			vBox.setBackground(background);
			vBox.setAlignment(Pos.CENTER);
//			GridPane.setHgrow(vBox, Priority.ALWAYS);
//			GridPane.setVgrow(vBox, Priority.ALWAYS);
			gridPane.add(vBox, i, 1);
			gridPane.getColumnConstraints().add(
					new ColumnConstraints(CELL_LENGTH));
		}

		for (int i = 0; i < allDay; i++) {
			CalendarCell cell = new CalendarCell(i + 1 + "", String.format("%d-%02d-%02d",
					year, month, (i + 1)));
			int m = dayOfWeek + i;
			GridPane.setHgrow(cell, Priority.ALWAYS);
			GridPane.setVgrow(cell, Priority.ALWAYS);
			gridPane.add(cell, m % 7, 2 + m / 7);
		}

		if (getChildren().size() == 1) {
			getChildren().set(0, gridPane);
		}
		else {
			getChildren().add(gridPane);
		}
	}

	@FXML
	public void next() {
		calendar.add(Calendar.MONTH, 1);
		draw(calendar);
	}

	@FXML
	public void previous() {
		calendar.add(Calendar.MONTH, -1);
		draw(calendar);
	}

}
