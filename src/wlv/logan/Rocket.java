package wlv.logan;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

import static wlv.logan.Main.HEIGHT_RATIO;
import static wlv.logan.Main.WIDTH_RATIO;

public class Rocket extends Point2D implements Printable {
    private final int angle; // from -90° to 90°
    private final int thrust; // from 0 to 4
    private final int fuel;
    private final double speedX;
    private final double speedY;
    private final double lastX;
    private final double lastY;

    public Rocket(double x, double y, int angle, int thrust, int fuel, double speedX, double speedY, double lastX, double lastY) {
        super(x, y);
        this.angle = angle;
        this.thrust = thrust;
        this.fuel = fuel;
        this.speedX = speedX;
        this.speedY = speedY;
        this.lastX = lastX;
        this.lastY = lastY;
    }

    public int getAngle() {
        return angle;
    }

    public int getThrust() {
        return thrust;
    }

    public int getFuel() {
        return fuel;
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedY() {
        return speedY;
    }

    public double getLastX() {
        return lastX;
    }

    public double getLastY() {
        return lastY;
    }

    public Rocket copy() {
        return new Rocket(getX(), getY(), angle, thrust, fuel, speedX, speedY, lastX, lastY);
    }

    public ImageView print() {
        ImageView imageView = new ImageView("wlv/logan/resources/rocket.png");
        imageView.setX(getX() / WIDTH_RATIO - 32d);
        imageView.setY(Math.abs(getY() - GamePane.MARS_HEIGHT) / HEIGHT_RATIO - 64d);
        imageView.setFitWidth(64d);
        imageView.setFitHeight(64d);
        imageView.setRotate(angle);
        return imageView;
    }
}
