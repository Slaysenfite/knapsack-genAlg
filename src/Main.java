import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.GeneticAlgorithm;
import model.Item;
import model.Population;
import utilities.FileUtility;
import utilities.TestUtility;
import utilities.Consts;


public class Main {

	public static void main(String[] args) {
		System.out.println("Starting Evolutionary Process");
        long start = System.currentTimeMillis();

        DecimalFormat df = new DecimalFormat("#.##");

        ArrayList<Item> items = FileUtility.readDataFile(Consts.DATA_FILE_NAME);
		Population population = new Population(items, Consts.POPULATION_SIZE);

		int generationCount = 1;
        while(generationCount <= Consts.MIN_GENERATIONS + 1 || TestUtility.meanFitnessOfGeneration(population) != TestUtility.maxFitnessOfGeneration(population)) {
            System.out.println("Generation: " + generationCount);

            if(Consts.STRICT_WEIGHT_CONSTRAINT && generationCount % 50 == 0)
                population.enforceStrictWeightConstraint();

            GeneticAlgorithm.calculateFitnessForEachIndividual(population.getPopulation(), items);
			System.out.println("Mean Fitness: " + df.format(TestUtility.meanFitnessOfGeneration(population))
                    + " Greatest Fitness: " + TestUtility.maxFitnessOfGeneration(population)
                    + "\n");

            TestUtility.generationGreatest.add(TestUtility.maxFitnessOfGeneration(population));
            TestUtility.generationMean.add(TestUtility.meanFitnessOfGeneration(population));
            TestUtility.generationNumber.add(generationCount);

            population.setPopulation(GeneticAlgorithm.produceNextGeneration(population, items, Consts.SELECTION));
			generationCount++;
		}
		
	   population.getPopulation().stream().forEach(x -> {
	    	System.out.print("Fitness: " + x.getFitness() + " Weight: "  +
                    GeneticAlgorithm.individualWeight(x.getChromosomeArray(), items) + " "
                    + x.chromosomeToString());
	    });

	   if(Consts.OUTPUT_TEST_DATA){
           try {
               TestUtility.writeTestDataToFile(Consts.TEST_FILE_NAME);
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

        long end = System.currentTimeMillis();
		System.out.println("Program execution time: " + 1.0 *(end - start)/1000 + "s");
	}

}
