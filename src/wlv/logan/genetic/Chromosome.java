package wlv.logan.genetic;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import wlv.logan.GamePane;
import wlv.logan.Printable;
import wlv.logan.Rocket;
import wlv.logan.RocketPath;
import wlv.logan.utils.PhysicUtils;

import java.util.ArrayList;
import java.util.List;

import static wlv.logan.MarsFloor.TEST_MAP;

public class Chromosome implements Printable {

    public List<Gene> genes;
    public Double fitnessValue = 0d;
    public Double normalizedFitnessValue;
    public Double cumulativeFitnessValue;
    private double[] points;
    private int angle;
    private int fuel;

    public Chromosome(List<Gene> genes, Rocket rocket) {
        this.genes = genes;
        RocketPath rocketPath = PhysicUtils.computeRocketPositions(rocket, this);
        points = rocketPath.path;
        angle = rocketPath.lastAngle;
        fuel = rocketPath.lastFuel;
        fitness(rocketPath.lastFuel, rocketPath.lastAngle, rocketPath.lastSpeedX,
                rocketPath.lastSpeedY);
    }

    private Chromosome(List<Gene> genes, Double fitnessValue, Double normalizedFitnessValue,
            Double cumulativeFitnessValue, double[] points, int angle, int fuel) {
        this.genes = genes;
        this.fitnessValue = fitnessValue;
        this.normalizedFitnessValue = normalizedFitnessValue;
        this.cumulativeFitnessValue = cumulativeFitnessValue;
        this.points = points;
        this.angle = angle;
        this.fuel = fuel;
    }

    private void fitness(int fuelLeft, int rotateAngle, double speedX, double speedY) {
        fitnessValue = 0d;
        Line winLine = GamePane.WINNING_AREA;

        Point2D crashPoint = PhysicUtils.getCrashPoint(points);

        double targetX = (winLine.getStartX() + winLine.getEndX()) / 2;
        double targetY = (winLine.getStartY() + winLine.getEndY()) / 2;

        double lastX = points[points.length - 2];
        double lastY = points[points.length - 1];

        double xDiff = targetX - lastX;
        double yDiff = targetY - lastY;
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


        if (rotateAngle == 0) {
            fitnessValue += 1d;
        } else {
            fitnessValue += (double) 1 / rotateAngle;
        }

        if (Math.abs(speedY) <= 40d) {
            fitnessValue += 1d;
        } else {
            fitnessValue += (double) 1 / speedY;
        }

        if (Math.abs(speedX) <= 20d) {
            fitnessValue += 1d;
        } else {
            fitnessValue += (double) 1 / speedX;
        }

        Polyline line = new Polyline(points);
        TEST_MAP.getFloors().forEach(losingFloor -> {
            if (line.getBoundsInParent().intersects(losingFloor.getBoundsInParent())) {
                fitnessValue -= 0.25d;
            } else {
                fitnessValue += 0.5d;
            }
        });
    }

    @Override
    public Node print() {
        Polyline line = new Polyline(points);
        line.setStroke(Color.GREENYELLOW);

        Tooltip tooltip = new Tooltip(
                "end X: " + getEndX() + "\nend Y: " + getEndY() + "\nangle: " + angle
                        + "\nfitness: " + fitnessValue);
        tooltip.setShowDelay(Duration.ZERO);

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
                cumulativeFitnessValue, points, angle, fuel
        );
    }

}
