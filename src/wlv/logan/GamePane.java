package wlv.logan;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import static wlv.logan.MarsFloor.TEST_MAP;

public class GamePane extends Pane {

    private final Line WINNING_AREA = TEST_MAP.getWinningFloor();

    private final Rocket rocket;
    private final Button startButton = initializeStartButton();

    public GamePane() {
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        getChildren().addAll(TEST_MAP.getFloors());

        rocket = new Rocket(1100d, 100d, 0, 0, 20, 0d, 0d, 1100d, 100d);
        getChildren().add(rocket.print());
        getChildren().add(startButton);
    }

    public Rocket getRocket() {
        return rocket;
    }

    public void onStartClick(EventHandler<MouseEvent> eventHandler) {
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    private Button initializeStartButton() {
        Button button = new Button("Start");
        button.setLayoutX(10d);
        button.setLayoutY(10d);
        button.setMinWidth(120d);
        button.setStyle("-fx-background-color:linear-gradient(to left, #CF3C26, #8A2819); -fx-text-fill:white; -fx-cursor: hand;");
        button.setFont(Font.font("Monospace", 16d));
        return button;
    }
}
