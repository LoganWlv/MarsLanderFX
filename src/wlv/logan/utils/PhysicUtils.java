package wlv.logan.utils;

import wlv.logan.GamePane;
import wlv.logan.MarsFloor;
import wlv.logan.Rocket;
import wlv.logan.RocketPath;
import wlv.logan.genetic.Chromosome;
import wlv.logan.genetic.Gene;

import java.util.ArrayList;
import java.util.List;

import static wlv.logan.Main.HEIGHT_RATIO;
import static wlv.logan.Main.WIDTH_RATIO;

public class PhysicUtils {
    private PhysicUtils() {
    }

    public static RocketPath computeRocketPositions(Rocket rocket, Chromosome individual) {
        List<Double> nextPoints = new ArrayList<>();

        Rocket nextRocket = rocket.copy();
        nextPoints.add(nextRocket.getX() / WIDTH_RATIO);
        nextPoints.add(Math.abs(nextRocket.getY() - GamePane.MARS_HEIGHT) / HEIGHT_RATIO);

        for (Gene gene : individual.genes) {
            int nextThrust = getNextPossibleThrust(nextRocket.getThrust(), gene.thrustPower);
            int nextFuel = nextRocket.getFuel() - nextThrust;
            int nextAngle = getNextPossibleAngle(nextRocket.getAngle(), gene.rotateAngle);

            double accX = (double) nextThrust * Math.sin(Math.toRadians(nextAngle));
            double accY = (double) nextThrust * Math.cos(Math.toRadians(nextAngle)) - MarsFloor.GRAVITY;

            double nextSpeedX = nextRocket.getSpeedX() + accX;
            double nextSpeedY = nextRocket.getSpeedY() + accY;

            double nextX = nextRocket.getX() + nextSpeedX - (accX * 0.5d);
            double nextY = nextRocket.getY() + nextSpeedY - (accY * 0.5d);

            nextRocket = new Rocket(nextX, nextY, nextAngle, nextThrust, nextFuel, nextSpeedX, nextSpeedY, nextRocket.getX(), nextRocket.getY());
            nextPoints.add(nextRocket.getX() / WIDTH_RATIO);
            nextPoints.add(Math.abs(nextRocket.getY() - GamePane.MARS_HEIGHT) / HEIGHT_RATIO);
        }

        double[] rocketPathPoints = new double[nextPoints.size()];
        for (int i = 0; i < rocketPathPoints.length; i++) {
            rocketPathPoints[i] = nextPoints.get(i);
        }

        return new RocketPath(rocketPathPoints, nextRocket.getAngle(), nextRocket.getFuel(), nextRocket.getSpeedX(), nextRocket.getSpeedY());
    }

    private static int getNextPossibleAngle(int prevAngle, int nextAngle) {
        boolean isNegative = nextAngle < 0;

        if (Math.abs(prevAngle - nextAngle) > 15) {
            if (isNegative) {
                return Math.max(prevAngle - 15, -90);
            } else {
                return Math.min(prevAngle + 15, 90);
            }
        } else {
            if (isNegative) {
                return Math.max(prevAngle + nextAngle, -90);
            } else {
                return Math.min(prevAngle + nextAngle, 90);
            }
        }
    }

    private static int getNextPossibleThrust(int prevThrust, int nextThrust) {
        if (Math.abs(prevThrust - nextThrust) > 1) {
            if (nextThrust > prevThrust) {
                return Math.min(prevThrust + 1, 4);
            } else {
                return Math.max(prevThrust - 1, 0);
            }
        } else {
            return nextThrust;
        }
    }
}
