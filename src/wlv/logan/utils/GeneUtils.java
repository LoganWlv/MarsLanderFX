package wlv.logan.utils;

import wlv.logan.genetic.Gene;

import java.util.ArrayList;
import java.util.List;

public class GeneUtils {

    private GeneUtils() {
    }

    public static List<Gene> generateGenes(int size) {
        List<Gene> genes = new ArrayList<>();

        Gene previousGene = randomGene();
        genes.add(previousGene);
        for (int i = 1; i < size; i++) {
            Gene nextGene = randomGene(previousGene.rotateAngle, previousGene.thrustPower);
            genes.add(nextGene);
            previousGene = nextGene;
        }

        return genes;
    }

    private static Gene randomGene() {
        return new Gene(RandomUtils.random(-90, 91), RandomUtils.random(0, 5));
    }

    private static Gene randomGene(int previousRotation, int previousThrust) {
        int minRotation = Math.max(previousRotation - 15, -90);
        int maxRotation = Math.min(previousRotation + 15, 90);

        List<Integer> possibleThrust = new ArrayList<>();
        possibleThrust.add(previousThrust);

        int maxThrust = Math.min(previousThrust + 1, 4);
        if (!possibleThrust.contains(maxThrust)) {
            possibleThrust.add(maxThrust);
        }

        int minThrust = Math.max(previousThrust - 1, 0);
        if (!possibleThrust.contains(minThrust)) {
            possibleThrust.add(minThrust);
        }

        return new Gene(
                RandomUtils.random(minRotation, maxRotation),
                possibleThrust.get(RandomUtils.random(0, possibleThrust.size() - 1))
        );
    }
}
