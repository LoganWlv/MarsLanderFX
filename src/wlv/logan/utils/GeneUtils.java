package wlv.logan.utils;

import wlv.logan.genetic.Gene;

import java.util.ArrayList;
import java.util.List;

public class GeneUtils {

    private GeneUtils() {
    }

    public static List<Gene> generateGenes(int size) {
        List<Gene> genes = new ArrayList<>();

        for (int i = 1; i < size; i++) {
            Gene nextGene = randomGene();
            genes.add(nextGene);
        }

        return genes;
    }

    public static Gene randomGene() {
        return new Gene(
                RandomUtils.random(-90, 90),
                RandomUtils.random(0, 4)
        );
    }
}
