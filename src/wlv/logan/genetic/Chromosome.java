package wlv.logan.genetic;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import wlv.logan.*;
import wlv.logan.utils.PhysicUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Chromosome implements Printable {

    public List<Gene> genes;
    public Double fitnessValue = 0d;
    public Double normalizedFitnessValue;
    public Double cumulativeFitnessValue;
    private double[] points;
    private final CrashPoint crashPoint;
    private int angle;
    private int fuel;

    public Chromosome(List<Gene> genes, Rocket rocket) {
        this.genes = genes;
        RocketPath rocketPath = PhysicUtils.computeRocketPositions(rocket, this);
        points = rocketPath.path;
        crashPoint = PhysicUtils.getCrashPoint(points);
        angle = rocketPath.lastAngle;
        fuel = rocketPath.lastFuel;
        fitness(rocketPath.lastFuel, rocketPath.lastAngle, rocketPath.lastSpeedX,
                rocketPath.lastSpeedY);
    }

    private Chromosome(List<Gene> genes, Double fitnessValue, Double normalizedFitnessValue,
                       Double cumulativeFitnessValue, double[] points, int angle, int fuel, CrashPoint crashPoint) {
        this.genes = genes;
        this.fitnessValue = fitnessValue;
        this.normalizedFitnessValue = normalizedFitnessValue;
        this.cumulativeFitnessValue = cumulativeFitnessValue;
        this.points = points;
        this.angle = angle;
        this.fuel = fuel;
        this.crashPoint = crashPoint;
    }

    private void fitness(int fuelLeft, int rotateAngle, double speedX, double speedY) {
        fitnessValue = 0d;
        Line winLine = GamePane.WINNING_AREA;

        double targetX = (winLine.getStartX() + winLine.getEndX()) / 2;
        double targetY = (winLine.getStartY() + winLine.getEndY()) / 2;

        double lastX = points[points.length - 2];
        double lastY = points[points.length - 1];

        double xDiff = targetX - lastX;
        double yDiff = targetY - lastY;

        if (null != crashPoint) {
            xDiff = targetX - crashPoint.point.getX();
            yDiff = targetY - crashPoint.point.getY();
        }

        double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);

        if (distance == 0f) {
            fitnessValue += 200d;
        } else {
            double distanceScore = 1 / distance;
            if (distanceScore > 100) {
                System.out.println("VERY CLOSE");
                distanceScore = 100;
            }
            fitnessValue += distanceScore;
        }
    }

    @Override
    public Node print() {
        double[] pointsToPrint;

        if (crashPoint != null) {
            pointsToPrint = new double[crashPoint.crashIndex + 1];
            System.arraycopy(points, 0, pointsToPrint, 0, pointsToPrint.length);
        } else {
            pointsToPrint = points;
        }


        Polyline line = new Polyline(pointsToPrint);
        line.setStrokeWidth(2d);
        line.setStroke(Color.GREENYELLOW);

        DecimalFormat df = new DecimalFormat("#.#");
        String crashPointMsg = this.crashPoint == null ? "\nRocket did not crash 🚀" : "\ncrash point: " + df.format(this.crashPoint.point.getX()) + " " + df.format(this.crashPoint.point.getY());

        Tooltip tooltip = new Tooltip(
                "end X: " + getEndX() + "\nend Y: " + getEndY() + "\nangle: " + angle
                        + "\nfitness: " + fitnessValue + crashPointMsg);
        tooltip.setShowDelay(Duration.ZERO);

        line.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            tooltip.setText("end X: " + getEndX() + "\nend Y: " + getEndY() + "\nangle: " + angle
                    + "\nfitness: " + fitnessValue + crashPointMsg + " \nx: " + mouseEvent.getX() + "\ny: " + mouseEvent.getY());
        });

        Tooltip.install(line, tooltip);
        return line;
    }

    public double getEndX() {
        return points[points.length - 2];
    }

    public double getEndY() {
        return points[points.length - 1];
    }

    public void updateGenes(List<Gene> genes, Rocket rocket) {
        this.genes = genes;
        RocketPath rocketPath = PhysicUtils.computeRocketPositions(rocket, this);
        this.points = rocketPath.path;
        this.angle = rocketPath.lastAngle;
        this.fuel = rocketPath.lastFuel;
        fitness(rocketPath.lastFuel, rocketPath.lastAngle, rocketPath.lastSpeedX,
                rocketPath.lastSpeedY);
    }

    public Chromosome copy() {
        return new Chromosome(
                new ArrayList<>(genes), fitnessValue, normalizedFitnessValue,
                cumulativeFitnessValue, points, angle, fuel, crashPoint
        );
    }

}
