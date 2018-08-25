import java.util.ArrayList;
import java.util.Collections;

import model.GeneticAlgorithm;
import model.Item;
import model.Knapsack;
import utilities.Utility;

public class Main {
	
	private static int KNAPSACK_CAPACITY = 5200;
	private static int POPULATION_SIZE = 100;
	
	private static boolean SECOND_CHANCE = true;
	private static boolean ELITISM = true;

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		ArrayList<Item> items = Utility.readDataFile("TestCases//testCase500.txt");
		
		Knapsack knapsack = new Knapsack(items, POPULATION_SIZE);
		GeneticAlgorithm.calculateFitnessForEachIndividual(knapsack.getPopulation(), items, KNAPSACK_CAPACITY, SECOND_CHANCE);
		
		long s1 = System.currentTimeMillis();
		Collections.sort(knapsack.getPopulation());
		long s2 = System.currentTimeMillis();
		System.out.println("Sort time: " + (s2 - s1) + "ms");

		System.out.println(knapsack.toString());
		long end = System.currentTimeMillis();
		System.out.println("Program execution time: " + (double) 1.0*(end - start)/1000 + "s");

	}

}
