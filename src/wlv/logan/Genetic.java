package wlv.logan;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;


class Genetic {

    /**
     *
     * IN CASE OF CONTINOUS GA - change required at Initialize - Crossover - Mutation steps
     *
     * initialize random number in given interval
     *
     * cross over child1_i = Ri * Parent1_i + (1 - Ri) * Parent2_i
     *            child2_i (1-Ri) * Parent1i + Bi * Parent2i
     *
     *
     * mutation random number in given interval
     *
     *
     * @param <T>
     */


    static class Chromosome<T> {
        public T[] genes;
        public Double fitnessValue;
        public Double normalizedFitnessValue;
        public Double cumulativeFitnessValue;

        public Chromosome(T[] genes) {
            this.genes = genes;
        }
    }


    public static void main(String[] args) {
        Chromosome[] population = new Chromosome[20];
        Random random = new Random();

        Double crossOverRatio = 0.75;
        Double mutationRatio = 0.20;
        Double elitismRatio = 0.5;

        for (int i = 0; i < population.length; i++) {
            Double[] genes = new Double[10];
            for (int j = 0; j < genes.length; j++) {
                genes[j] = (double) random.nextInt(10);
            }
            population[i] = new Chromosome<>(genes);
            population[i].fitnessValue = fitness(genes);
        }

        while (true) {
            //Fitness scaling if you have negative value - shift every values (min = -20, add 20 to every values)

            Double fitnessTotal = Arrays.stream(population).mapToDouble(chromosome -> chromosome.fitnessValue).sum();

            //normalize fitness value
            Arrays.stream(population).forEach(chromosome -> chromosome.normalizedFitnessValue = chromosome.fitnessValue / fitnessTotal);

            //sort by normalized fitness value
            Arrays.sort(population, Comparator.comparing(chromosome -> chromosome.normalizedFitnessValue, Comparator.reverseOrder()));

            //compute cumulative fitness
            for (int i = 0; i < population.length; i++) {
                double cumulativeResult = 0;
                for (int j = i; j < population.length; j++) {
                    cumulativeResult = cumulativeResult + (population[j].fitnessValue / fitnessTotal);
                }
                if (i == 0) {
                    population[i].cumulativeFitnessValue = 1.0;
                } else if (i == population.length - 1) {
                    population[i].cumulativeFitnessValue = 0.0;
                } else {
                    population[i].cumulativeFitnessValue = cumulativeResult;

                }
            }

            Chromosome[] nextPopulation = new Chromosome[population.length];

            //elitism
            for (int popCounter = 0; popCounter < 2; popCounter++) {
                nextPopulation[popCounter] = copyChromosome(population[popCounter]);
            }

            for (int popCounter = 2; popCounter < nextPopulation.length -1; popCounter++) {

                //find two parents
                int firstParentIndex = -1;
                int secondParentIndex = -1;
                Double selectR = Math.random();
                for (int i = 0; i < population.length; i++) {
                    if (selectR >= population[i].cumulativeFitnessValue) {
                        if (firstParentIndex == -1) {
                            firstParentIndex = i - 1;
                        }
                    }
                }

                selectR = Math.random() ;
                for (int i = 0; i < population.length; i++) {
                    if (selectR >= population[i].cumulativeFitnessValue) {
                        if (secondParentIndex == -1) {
                            secondParentIndex = i - 1;
                        }
                    }
                }

                //cross over the 2 parents
                Chromosome firstChild = copyChromosome(population[firstParentIndex]);
                Chromosome secondChild = copyChromosome(population[secondParentIndex]);

                int crossOverPointA = random.nextInt(firstChild.genes.length - 1);
                int crossOverPointB = crossOverPointA;

                while (crossOverPointA == crossOverPointB || crossOverPointA > crossOverPointB) {
                    crossOverPointB = random.nextInt(firstChild.genes.length);
                }

                for (int i = crossOverPointA; i < crossOverPointB; i++) {
                    firstChild.genes[i] = population[secondParentIndex].genes[i];
                    secondChild.genes[i] = population[firstParentIndex].genes[i];
                }

                //cross over probability to know if parent or child survive
                Double r1 = Math.random();
                Double r2 = Math.random();

                if (r1 <= crossOverRatio) {
                    nextPopulation[popCounter] = firstChild;
                } else {
                    nextPopulation[popCounter] = copyChromosome(population[firstParentIndex]);
                }

                if (r2 <= crossOverRatio) {
                    nextPopulation[popCounter + 1] = secondChild;
                } else {
                    nextPopulation[popCounter + 1] = copyChromosome(population[secondParentIndex]);
                }


                //mutation
                for (int i = 0; i < nextPopulation[popCounter].genes.length; i++) {
                    Double r = Math.random();
                    if (r < mutationRatio) {
                        nextPopulation[popCounter].genes[i] = (double) random.nextInt(10);
                    }
                }

                for (int i = 0; i < nextPopulation[popCounter + 1].genes.length; i++) {
                    Double r = Math.random();
                    if (r < mutationRatio) {
                        nextPopulation[popCounter + 1].genes[i] = (double) random.nextInt(10);
                    }
                }


            }

            System.out.println("**** END ****");
            population = nextPopulation;
        }

    }

    public static Double fitness(Double[] genes) {
        return Arrays.stream(genes).reduce(0.0, Double::sum);
    }

    public static Chromosome<Double> copyChromosome(Chromosome<Double> c) {
        Double[] genes = new Double[c.genes.length];

        for (int i = 0; i < genes.length; i++) {
            genes[i] = c.genes[i];
        }

        Chromosome copied = new Chromosome<>(genes);
        copied.fitnessValue = fitness(genes);
        return copied;
    }
}