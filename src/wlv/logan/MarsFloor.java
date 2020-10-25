package wlv.logan;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MarsFloor {

    public static final Double GRAVITY = 3.711d;

    private final Line winningFloor;
    private final List<Line> losingFloors;

    public static MarsFloor TEST_MAP = new MarsFloor(
            createFloorLine(300.0, 700.0, 500.0, 700.0),
            Arrays.asList(
                    createFloorLine(0.0, 600.0, 300.0, 700.0),
                    createFloorLine(300.0, 700.0, 500.0, 700.0),
                    createFloorLine(500.0, 700.0, 500.0, 400.0),
                    createFloorLine(500.0, 400.0, 600.0, 400.0),
                    createFloorLine(600.0, 400.0, 1400.0, 600.0)
            )
    );

    private MarsFloor(Line winningFloor, List<Line> losingFloors) {
        this.winningFloor = winningFloor;
        this.losingFloors = losingFloors;
    }

    public Line getWinningFloor() {
        return winningFloor;
    }

    public List<Line> getFloors() {
        List<Line> floors = new ArrayList<>();
        floors.add(winningFloor);
        floors.addAll(losingFloors);
        return Collections.unmodifiableList(floors);
    }

    private static Line createFloorLine(double startX, double startY, double endX, double endY) {
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.RED);
        return line;
    }
}
