package wlv.logan;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import static wlv.logan.MarsFloor.TEST_MAP;

public class GamePane extends Pane {

    private final Line WINNING_AREA = TEST_MAP.getKey();

    private Rocket rocket;

    public GamePane() {
        setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        getChildren().addAll(TEST_MAP.getValue());

        rocket = new Rocket(1100d, 100d, 0, 0, 20);
        getChildren().add(rocket.print());


    }
}
