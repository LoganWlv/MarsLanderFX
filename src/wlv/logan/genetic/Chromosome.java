package wlv.logan.genetic;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import wlv.logan.Printable;
import wlv.logan.Rocket;
import wlv.logan.utils.PhysicUtils;

import java.util.List;

public class Chromosome implements Printable {
    public List<Gene> genes;
    public Double fitnessValue;
    public Double normalizedFitnessValue;
    public Double cumulativeFitnessValue;

    private final Rocket rocket;

    public Chromosome(List<Gene> genes, Rocket rocket) {
        this.genes = genes;
        this.rocket = rocket;
    }

    @Override
    public Node print() {
        double[] points = PhysicUtils.computeRocketPositions(rocket, this);
        Polyline line = new Polyline(points);
        line.setStroke(Color.BLUE);
        return line;
    }
}
