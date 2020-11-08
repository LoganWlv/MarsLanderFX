package wlv.logan;

import javafx.geometry.Point2D;

public class CrashPoint {
    public Point2D point;
    public int crashIndex;

    public CrashPoint(Point2D point, int crashIndex) {
        this.point = point;
        this.crashIndex = crashIndex;
    }
}
