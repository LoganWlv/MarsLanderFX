package wlv.logan.genetic;

import javafx.scene.Node;
import wlv.logan.GamePane;
import wlv.logan.utils.GeneUtils;
import wlv.logan.utils.RandomUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static wlv.logan.utils.GeneUtils.randomGene;


public class Genetic {

    /**
     * IN CASE OF CONTINOUS GA - change required at Initialize - Crossover - Mutation steps
     * <p>
     * initialize random number in given interval
     * <p>
     * cross over child1_i = Ri * Parent1_i + (1 - Ri) * Parent2_i
     * child2_i (1-Ri) * Parent1i + Bi * Parent2i
     * <p>
     * <p>
     * mutation random number in given interval
     *
     * @param <T>
     */

    private final int POPULATION_SIZE = 100;
    private final int NB_OF_GENES = 40;

    public static List<Chromosome> population = null;
    public static List<Node> displayedNodes = new ArrayList<>();
    public static int currentGeneration = 0;

    public void genetic(GamePane gamePane) {
        if (null == population) {
            initPopulation(gamePane);
        }

        double crossOverRatio = 0.75;
        double mutationRatio = 0.50;
        double elitismRatio = 0.5;

        gamePane.getChildren().removeAll(displayedNodes);

        for (int z = 0; z < 1; z++) {
            System.out.println("Genetic process will start...");

            //Fitness scaling if you have negative value - shift every values (min = -20, add 20 to every values)

            Double fitnessTotal = population.stream().mapToDouble(chromosome -> chromosome.fitnessValue).sum();

            //normalize fitness value
            population.forEach(chromosome -> chromosome.normalizedFitnessValue = chromosome.fitnessValue / fitnessTotal);

            //sort by normalized fitness value
            population.sort(Comparator.comparing(chromosome -> chromosome.normalizedFitnessValue, Comparator.reverseOrder()));

            //compute cumulative fitness
            for (int i = 0; i < population.size(); i++) {
                double cumulativeResult = 0;
                for (int j = i; j < population.size(); j++) {
                    cumulativeResult = cumulativeResult + (population.get(j).fitnessValue / fitnessTotal);
                }
                if (i == 0) {
                    population.get(i).cumulativeFitnessValue = 1.0;
                } else if (i == population.size() - 1) {
                    population.get(i).cumulativeFitnessValue = 0.0;
                } else {
                    population.get(i).cumulativeFitnessValue = cumulativeResult;

                }
            }

            List<Chromosome> nextPopulation = new ArrayList<>(population);

            // ELITISM - we start at index 5 to keep the 5 best elements
            for (int popCounter = 50; popCounter < population.size() - 1; popCounter++) {

                //find two parents
                int firstParentIndex = -1;
                int secondParentIndex = -1;
                double selectR = Math.random();
                for (int i = 0; i < population.size(); i++) {
                    if (selectR >= population.get(i).cumulativeFitnessValue) {
                        firstParentIndex = i - 1;
                        break;
                    }
                }

                selectR = Math.random();
                for (int i = 0; i < population.size(); i++) {
                    if (selectR >= population.get(i).cumulativeFitnessValue) {
                        secondParentIndex = i - 1;
                        break;
                    }
                }

                //cross over the 2 parents
                Chromosome firstChild = population.get(firstParentIndex).copy();
                Chromosome secondChild = population.get(secondParentIndex).copy();
                List<Gene> firstChildGenes = new ArrayList<>();
                List<Gene> secondChildGenes = new ArrayList<>();
                for (int i = 0; i < NB_OF_GENES - 1; i++) {

                    double firstRandom = RandomUtils.random(0, 1);
                    double secondRandom = RandomUtils.random(0, 1);

                    int firstChildAngle = (int) (firstRandom * firstChild.genes.get(i).rotateAngle + (1 - firstRandom) * secondChild.genes.get(i).rotateAngle);
                    int firstChildThrust = (int) (firstRandom * firstChild.genes.get(i).thrustPower + (1 - firstRandom) * secondChild.genes.get(i).thrustPower);

                    int secondChildAngle = (int) ((1 - secondRandom) * firstChild.genes.get(i).rotateAngle + secondRandom * secondChild.genes.get(i).rotateAngle);
                    int secondChildThrust = (int) ((1 - secondRandom) * firstChild.genes.get(i).thrustPower + secondRandom * secondChild.genes.get(i).thrustPower);

                    firstChildGenes.add(new Gene(firstChildAngle, firstChildThrust));
                    secondChildGenes.add(i, new Gene(secondChildAngle, secondChildThrust));
                }
                firstChild.updateGenes(firstChildGenes, gamePane.getRocket());
                secondChild.updateGenes(secondChildGenes, gamePane.getRocket());


                //cross over probability to know if parent or child survive
                double r1 = Math.random();
                double r2 = Math.random();

                if (r1 <= crossOverRatio) {
                    nextPopulation.set(popCounter, firstChild);
                } else {
                    nextPopulation.set(popCounter, population.get(firstParentIndex).copy());
                }

                if (r2 <= crossOverRatio) {
                    nextPopulation.set(popCounter + 1, secondChild);
                } else {
                    nextPopulation.set(popCounter + 1, population.get(secondParentIndex).copy());
                }


                //mutation
                for (int i = 0; i < nextPopulation.get(popCounter).genes.size(); i++) {
                    double r = Math.random();
                    if (r < mutationRatio) {
                        nextPopulation.get(popCounter).genes.set(i, randomGene());
                        nextPopulation.get(popCounter).updateGenes(nextPopulation.get(popCounter).genes, gamePane.getRocket());
                    }
                }
            }

            System.out.println("**** END ****");
            population = nextPopulation;
            currentGeneration++;
        }


        displayedNodes = population.stream().map(Chromosome::print).collect(Collectors.toList());
        System.out.println(displayedNodes.size());
        gamePane.getChildren().addAll(displayedNodes);
        gamePane.setButtonTooltip("Generation: " + currentGeneration);
    }

    public void initPopulation(GamePane gamePane) {
        population = new ArrayList<>();

        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(new Chromosome(GeneUtils.generateGenes(NB_OF_GENES), gamePane.getRocket()));
        }
    }
}
