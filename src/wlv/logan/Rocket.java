package wlv.logan;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

public class Rocket extends Point2D implements Printable {
    private int angle; // from -90° to 90°
    private int thrust; // from 0 to 4
    private int fuel;

    public Rocket(double x, double y, int angle, int thrust, int fuel) {
        super(x, y);
        this.angle = angle;
        this.thrust = thrust;
        this.fuel = fuel;
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

    public ImageView print() {
        ImageView imageView = new ImageView("wlv/logan/resources/rocket.png");
        imageView.setX(getX());
        imageView.setY(getY());
        imageView.setFitWidth(64d);
        imageView.setFitHeight(64d);
        return imageView;
    }
}
