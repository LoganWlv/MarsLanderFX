package wlv.logan;

public class RocketPath {
    public final double[] path;
    public final int lastAngle;
    public final int lastFuel;
    public final double lastSpeedX;
    public final double lastSpeedY;
    public RocketPath(double[] path, int lastAngle, int lastFuel, double lastSpeedX, double lastSpeedY) {
        this.path = path;
        this.lastAngle = lastAngle;
        this.lastFuel = lastFuel;
        this.lastSpeedX = lastSpeedX;
        this.lastSpeedY = lastSpeedY;
    }
}
