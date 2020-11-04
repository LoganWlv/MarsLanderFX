package wlv.logan;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.util.Duration;

import static wlv.logan.Main.HEIGHT_RATIO;
import static wlv.logan.Main.WIDTH_RATIO;
import static wlv.logan.MarsFloor.TEST_MAP;

public class GamePane extends Pane {

    public static final Line WINNING_AREA = TEST_MAP.getWinningFloor();
    public static final int MARS_WIDTH = 7000;
    public static final int MARS_HEIGHT = 3000;

    private final Rocket rocket;
    private final Button startButton = initializeStartButton();
    private final Tooltip buttonTooltip = new Tooltip("No population yet.");

    public GamePane() {
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        getChildren().addAll(TEST_MAP.getFloors());

        double initialRocketX = 700d * WIDTH_RATIO;
        double initialRocketY = 700d * HEIGHT_RATIO;
        rocket = new Rocket(initialRocketX, initialRocketY, 0, 0, 20, 0d, 0d, initialRocketX, initialRocketY);
        getChildren().add(rocket.print());

        buttonTooltip.setShowDelay(Duration.ZERO);
        startButton.setTooltip(buttonTooltip);
        getChildren().add(startButton);
    }

    public Rocket getRocket() {
        return rocket;
    }

    public void onStartClick(EventHandler<MouseEvent> eventHandler) {
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    public void setButtonTooltip(String text) {
        buttonTooltip.setText(text);
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
