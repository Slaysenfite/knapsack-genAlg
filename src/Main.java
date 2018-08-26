import java.util.ArrayList;
import java.util.Collections;

import model.GeneticAlgorithm;
import model.Item;
import model.Knapsack;
import utilities.Utility;

public class Main {
	
	private static int KNAPSACK_CAPACITY = 5200;
	private static int POPULATION_SIZE = 100;
	private static int MAX_GENERATION_NUMS = 5;
	private static boolean SECOND_CHANCE = true;

	public static void main(String[] args) {
		System.out.println("Starting Evolutionary Process");
		
		
		long start = System.currentTimeMillis();
		
		ArrayList<Item> items = Utility.readDataFile("TestCases//testCase1000.txt");
		Knapsack knapsack = new Knapsack(items, POPULATION_SIZE);

		int generationCount = 0;
		while(generationCount < MAX_GENERATION_NUMS) {
			GeneticAlgorithm.calculateFitnessForEachIndividual(knapsack, KNAPSACK_CAPACITY, SECOND_CHANCE);

			knapsack = GeneticAlgorithm.produceNextGeneration(knapsack, items);

			generationCount++;
			System.out.println("Generation: " + generationCount);
			/*for(int i = 0; i < knapsack.getPopulation().size(); i++) {
				System.out.println(i + " " + knapsack.getPopulation().get(i).getFitness() 
						+ " " + knapsack.getPopulation().get(i).chromosomeToString());
			}*/
		}
		
//		System.out.println(knapsack.getPopulation().get(POPULATION_SIZE-1).chromosomeToString());		
	    knapsack.getPopulation().stream().forEach(x -> {
	    	System.out.print("Fitness " + x.getFitness() + " " + x.chromosomeToString());
	    });
		long end = System.currentTimeMillis();
		System.out.println("Program execution time: " + (double) 1.0*(end - start)/1000 + "s");

	}

}
