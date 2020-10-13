package wlv.logan;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;

public class MarsFloor {

    public static Pair<Line, List<Line>> TEST_MAP = new Pair<>(createFloorLine(300.0, 700.0, 500.0, 700.0), Arrays.asList(
            createFloorLine(0.0, 600.0, 300.0, 700.0),
            createFloorLine(300.0, 700.0, 500.0, 700.0),
            createFloorLine(500.0, 700.0, 500.0, 400.0),
            createFloorLine(500.0, 400.0, 600.0, 400.0),
            createFloorLine(600.0, 400.0, 1400.0, 600.0)
    ));

    private static Line createFloorLine(double startX, double startY, double endX, double endY) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.RED);
        return line;
    }
}
