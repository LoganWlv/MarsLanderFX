package wlv.logan.utils;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import wlv.logan.*;
import wlv.logan.genetic.Chromosome;
import wlv.logan.genetic.Gene;

import java.util.ArrayList;
import java.util.List;

import static wlv.logan.Main.*;
import static wlv.logan.MarsFloor.TEST_MAP;

public class PhysicUtils {
    private PhysicUtils() {
    }

    public static CrashPoint getCrashPoint(double[] points) {
        for (int i = 0; i < points.length - 2; i += 2) {
            double x1 = points[i];
            double y1 = transformY(points[i + 1]);

            double x2 = points[i + 2];
            double y2 = transformY(points[i + 3]);

            boolean isVertical = x2 == x1;
            boolean isHorizontal = y2 == y1;

            double A = (y2 - y1) / (x2 - x1);
            double B = y1 - A * x1;

            for (Line losingFloor : TEST_MAP.getFloors()) {
                double startX = losingFloor.getStartX();
                double startY = transformY(losingFloor.getStartY());

                double endX = losingFloor.getEndX();
                double endY = transformY(losingFloor.getEndY());

                boolean isLosingFloorVertical = startX == endX;
                boolean isLosingFloorHorizontal = startY == endY;

                if (isLosingFloorVertical) {
                    if (isVertical && x1 == startX) { // same vertical
                        if (y2 <= startY && y2 >= endY) {
                            return new CrashPoint(new Point2D(x1, endY), i + 3, CrashType.V_x_V, x1, y1, x2, y2, Double.NaN, Double.NaN, startX, startY, endX, endY, Double.NaN, Double.NaN);
                        } else if (y2 >= startY && y2 <= endY) {
                            return new CrashPoint(new Point2D(x1, startY), i + 3, CrashType.V_x_V, x1, y1, x2, y2, Double.NaN, Double.NaN, startX, startY, endX, endY, Double.NaN, Double.NaN);
                        }
                    } else if (isHorizontal) {
                        if (((startX <= x2 && x1 >= startX) || (x2 >= startX && x1 <= startX)) &&
                                ((y1 <= startY && endY >= y1) || (startX >= y1 && y1 <= endX))) {
                            return new CrashPoint(new Point2D(startX, y2), i + 3, CrashType.V_x_H, x1, y1, x2, y2, Double.NaN, Double.NaN, startX, startY, endX, endY, Double.NaN, Double.NaN);
                        }
                    } else {
                        double interY = A * startX + B;
                        if (((interY <= y2 && interY >= y1) || (interY >= y2 && interY <= y1)) &&
                                ((interY <= endY && interY >= startY) || (interY >= endY && interY <= startY))) {
                            return new CrashPoint(new Point2D(endX, interY), i + 3, CrashType.V_x_A, x1, y1, x2, y2, A, B, startX, startY, endX, endY, Double.NaN, Double.NaN);
                        }
                    }
                } else if (isLosingFloorHorizontal) {
                    if (isHorizontal && y1 == startY) { // same horizontal
                        if (x2 <= startX && x2 >= endX) {
                            return new CrashPoint(new Point2D(endX, y2), i + 3, CrashType.H_x_H, x1, y1, x2, y2, Double.NaN, Double.NaN, startX, startY, endX, endY, Double.NaN, Double.NaN);
                        } else if (y2 >= startX && y2 <= endX) {
                            return new CrashPoint(new Point2D(startX, y2), i + 3, CrashType.H_x_H, x1, y1, x2, y2, Double.NaN, Double.NaN, startX, startY, endX, endY, Double.NaN, Double.NaN);
                        }
                    } else if (isVertical) {
                        if (((startY <= y2 && startY >= y1) || (startY >= y2 && startY <= y1)) &&
                                ((x1 <= startX && x1 >= endX) || (x1 >= startX && x1 <= endX))) {
                            return new CrashPoint(new Point2D(x2, endY), i + 3, CrashType.H_x_V, x1, y1, x2, y2, Double.NaN, Double.NaN, startX, startY, endX, endY, Double.NaN, Double.NaN);
                        }
                    } else {
                        double interX = (startY - B) / A;
                        if (((interX <= x2 && interX >= x1) || (interX >= x2 && interX <= x1)) &&
                                ((interX <= startX && interX >= endX) || (interX >= startX && interX <= endX))) {
                            return new CrashPoint(new Point2D(interX, endY), i + 3, CrashType.H_x_A, x1, y1, x2, y2, A, B, startX, startY, endX, endY, Double.NaN, Double.NaN);
                        }
                    }
                } else {
                    double loseA = (endY - startY) / (endX - startX);
                    double loseB = startY - loseA * startX;

                    if (isVertical) {
                        double interY = loseA * x2 + loseB;
                        if ((interY <= y2 && interY >= y1) || (interY >= y2 && interY <= y1)) {
                            return new CrashPoint(new Point2D(x2, interY), i + 3, CrashType.A_x_V, x1, y1, x2, y2, Double.NaN, Double.NaN, startX, startY, endX, endY, loseA, loseB);
                        }
                    } else if (isHorizontal) {
                        double interX = (y2 - loseB) / loseA;
                        if ((interX <= x2 && interX >= x1) || (interX >= x2 && interX <= x1)) {
                            return new CrashPoint(new Point2D(interX, y2), i + 3, CrashType.A_x_H, x1, y1, x2, y2, Double.NaN, Double.NaN, startX, startY, endX, endY, loseA, loseB);
                        }
                    } else {
                        if (loseA == A) {
                            if (loseB == B) {
                                if (x2 <= startX && x2 >= endX) {
                                    return new CrashPoint(new Point2D(startX, startY), i + 3, CrashType.SAME, x1, y1, x2, y2, A, B, startX, startY, endX, endY, loseA, loseB);
                                } else if (x2 >= startX && x2 <= endX) {
                                    return new CrashPoint(new Point2D(endX, endY), i + 3, CrashType.SAME, x1, y1, x2, y2, A, B, startX, startY, endX, endY, loseA, loseB);
                                }
                            }
                        }

                        double interX = (loseB - B) / (A - loseA);
                        double interY = A * interX + B;

                        if (((interX >= x1 && interX <= x2) || (interX <= x1 && interX >= x2)) &&
                                ((interX >= startX && interX <= endX) || (interX <= startX && interX >= endX))) {
                            return new CrashPoint(new Point2D(interX, interY), i + 3, CrashType.A_x_A, x1, y1, x2, y2, A, B, startX, startY, endX, endY, loseA, loseB);
                        }
                    }
                }
            }
        }

        return null;
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

    private static double transformY(double y) {
        return 800 - y;
    }
}
