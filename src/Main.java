import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import model.GeneticAlgorithm;
import model.Item;
import model.Knapsack;
import utilities.FileUtility;
import utilities.TestUtility;
import utilities.Consts;


public class Main {

	public static void main(String[] args) {
		System.out.println("Starting Evolutionary Process");
        long start = System.currentTimeMillis();

        DecimalFormat df = new DecimalFormat("#.##");

        ArrayList<Item> items = FileUtility.readDataFile(Consts.FILE_NAME);
		Knapsack knapsack = new Knapsack(items, Consts.POPULATION_SIZE);

		int generationCount = 1;
		while(generationCount < Consts.MAX_GENERATIONS + 1) {
            System.out.println("Generation: " + generationCount);

            GeneticAlgorithm.calculateFitnessForEachIndividual(knapsack.getPopulation(), items);
			System.out.println("Mean Fitness: " + df.format(TestUtility.meanFitnessOfGeneration(knapsack))
                    + " Greatest Fitness: " + TestUtility.maxFitnessOfGeneration(knapsack)
                    + "\n");

            TestUtility.generationGreatest.add(TestUtility.maxFitnessOfGeneration(knapsack));
            TestUtility.generationMean.add(TestUtility.meanFitnessOfGeneration(knapsack));
            TestUtility.generationNumber.add(generationCount);

            knapsack.setPopulation(GeneticAlgorithm.produceNextGeneration(knapsack, items, Consts.SELECTION));
			generationCount++;
		}
		
	   knapsack.getPopulation().stream().forEach(x -> {
	    	System.out.print("Fitness: " + x.getFitness() + " Weight: "  +
                    GeneticAlgorithm.individualWeight(x.getChromosomeArray(), items) + " "
                    + x.chromosomeToString());
	    });

	   if(Consts.OUTPUT_TEST_DATA){
           try {
               TestUtility.writeTestDataToFile("C:\\development\\knapsackOptimisation_GA\\knapsack-genAlg\\TestResults\\" + Consts.SELECTION + "_pop" + Consts.POPULATION_SIZE +
                       "_gen" + Consts.MAX_GENERATIONS + "_chance-" + Consts.SECOND_CHANCE + ".csv");
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

        long end = System.currentTimeMillis();
		System.out.println("Program execution time: " + 1.0 *(end - start)/1000 + "s");

	}

}
