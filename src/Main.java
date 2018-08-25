import java.util.ArrayList;

import model.Item;
import model.Knapsack;
import utilities.Utility;

public class Main {
	
	private static int KNAPSACK_CAPACITY = 8000;
	private static int POPULATION_SIZE = 100;
	
	private static boolean SECOND_CHANCE = true;
	private static boolean ELITISM = true;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*long start = System.currentTimeMillis();
		ArrayList<Item> items = Utility.readDataFile("TestCases//testCase500.txt");
		for(Item i : items) {
			System.out.println("Weight: " + i.getWeight() + " Value: " + i.getValue() + " Ratio: " + i.valueVersusWeightRatio());
		}
		
		Knapsack knapsack = new Knapsack(items, POPULATION_SIZE);
		System.out.println(knapsack.toString());
		long end = System.currentTimeMillis();
		System.out.println("Program execution time: " + (double) 1.0*(end - start)/1000 + "s");*/
		
		byte[] ones = new byte[20];
		byte[] zeros = new byte[20];

		for(int i = 0; i < 20; i++) {
			ones[i] = 1;
			zeros[i] = 0;
		}
		
		int count = 0;
		while(count < 40) {
			ones = Knapsack.mutateChromosome(ones);
			System.out.println(Utility.chromosomeToString(ones));
			zeros = Knapsack.mutateChromosome(zeros);
			System.out.println(Utility.chromosomeToString(zeros));
			count++;
		}
		ArrayList<Item> items = Utility.readDataFile("TestCases//testCase20.txt");
		System.out.println(Knapsack.fitnessCalculation(ones, items, 150, SECOND_CHANCE));
		System.out.println(Knapsack.fitnessCalculation(zeros, items, 150, SECOND_CHANCE));

	}

}
