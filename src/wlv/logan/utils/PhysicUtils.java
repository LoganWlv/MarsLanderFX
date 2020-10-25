package wlv.logan.utils;

import wlv.logan.MarsFloor;
import wlv.logan.Rocket;
import wlv.logan.genetic.Chromosome;
import wlv.logan.genetic.Gene;

import java.util.ArrayList;
import java.util.List;

public class PhysicUtils {
    private PhysicUtils() {
    }

    public static double[] computeRocketPositions(Rocket rocket, Chromosome individual) {
        List<Double> nextPoints = new ArrayList<>();

        Rocket nextRocket = rocket.copy();
        nextPoints.add(nextRocket.getX());
        nextPoints.add(nextRocket.getY());

        for (Gene gene : individual.genes) {
            int nextFuel = nextRocket.getFuel() - gene.thrustPower;
            int nextAngle = nextRocket.getAngle() + gene.rotateAngle;
            double forceX = gene.thrustPower * Math.sin(Math.toRadians(nextAngle));
            double forceY = gene.thrustPower * Math.cos(Math.toRadians(nextAngle));

            double nextX = 2 * nextRocket.getX() - nextRocket.getLastX() - forceX;
            double nextY = 2 * (1200 - nextRocket.getY()) - (1200 - nextRocket.getLastY()) - MarsFloor.GRAVITY + forceY;
            double nextSpeedX = nextRocket.getSpeedX() - forceX;
            double nextSpeedY = nextRocket.getSpeedY() - MarsFloor.GRAVITY + forceY;

            nextRocket = new Rocket(nextX, Math.abs(nextY - 1200), nextAngle, gene.thrustPower, nextFuel, nextSpeedX, nextSpeedY, nextRocket.getX(), nextRocket.getLastY());
            nextPoints.add(nextRocket.getX());
            nextPoints.add(nextRocket.getY());
        }

        double[] rocketPathPoints = new double[nextPoints.size()];
        for(int i = 0; i < rocketPathPoints.length; i++) {
            rocketPathPoints[i] = nextPoints.get(i);
        }

        return rocketPathPoints;
    }
}
