package wlv.logan;

import javafx.geometry.Point2D;

import java.text.DecimalFormat;

public class CrashPoint {
    public Point2D point;
    public int crashIndex;
    public CrashType type;
    public Double x1;
    public Double y1;
    public Double x2;
    public Double y2;
    public Double A;
    public Double B;
    public Double startX;
    public Double startY;
    public Double endX;
    public Double endY;
    public Double loseA;
    public Double loseB;


    public CrashPoint(Point2D point, int crashIndex, CrashType type, Double x1, Double y1, Double x2, Double y2, Double a, Double b, Double startX, Double startY, Double endX, Double endY, Double loseA, Double loseB) {
        this.point = point;
        this.crashIndex = crashIndex;
        this.type = type;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        A = a;
        B = b;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.loseA = loseA;
        this.loseB = loseB;
    }

    public String debugCrashInfo() {
        DecimalFormat df = new DecimalFormat("#.#");
        return "crash details: x1: " + df.format(x1) + " y1: " + df.format(y1) +
                "               x2: " + df.format(x2) + " y2: " + df.format(y2) +
                "                A: " + df.format(A) + " B: " + df.format(B) +
                "          startX: " + df.format(startX) + " startY: " + df.format(startY) +
                "            endX: " + df.format(endX) + " endY: " + df.format(endY) +
                "           loseA: " + df.format(loseA) + " loseB: " + df.format(loseB);
    }
}
