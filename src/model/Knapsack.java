package model;

import java.util.ArrayList;

import utilities.Utility;

public class Knapsack {
	
	private ArrayList<Item> items;
	private ArrayList<byte[]> population;
	
	public Knapsack(ArrayList<Item> items, int populationSize) {
		this.items = items;
		population = new ArrayList<>();
		for(int i = 0; i < populationSize; i++) {
			population.add(Population.createChromosome(items.size()));
		}
	}
	
	public static int individualValue(byte[] individual, ArrayList<Item> items) {
		int value = 0;
		for(int c = 0; c < items.size(); c++) {
			if(individual[c] == 1) {
				value += items.get(c).getValue();
			}
		}
		return value;
	}
	
	public static int individualWeight(byte[] individual, ArrayList<Item> items) {
		int weight = 0;
		for(int c = 0; c < items.size(); c++) {
			if(individual[c] == 1) {
				weight += items.get(c).getWeight();
			}
		}
		return weight;
	}
	
	public static byte[] mutateChromosome(byte[] chromosome) {
		for(int i = 0; i < chromosome.length; i++) {			
			int chance = Utility.generateRandomBoundedInt(1, 100);
			if(chance <= 3) {
				if(chromosome[i] == 0) chromosome[i] = 1;
				else if(chromosome[i] == 1) chromosome[i] = 0;
			}
		}
		return chromosome;
	}
	
	public static int indexOfMaxWeight(byte[] individual, ArrayList<Item> items) {
		int index = 0;
		int max = items.get(index).getWeight();
		for(int i = 1; i < individual.length; i++) {
			if(individual[i] == 1 && items.get(i).getWeight() > max) {
				index = i;
				max = items.get(i).getWeight();
			}
		}
		return index;
	}
	
	public static int indexOfMaxValue(byte[] individual, ArrayList<Item> items) {
		int index = 0;
		int max = items.get(index).getValue();
		for(int i = 1; i < individual.length; i++) {
			if(individual[i] == 1 && items.get(i).getValue() > max) {
				index = i;
				max = items.get(i).getValue();
			}
		}
		return index;
	}
	
	public static int fitnessCalculation(byte[] individual, ArrayList<Item> items, int maxCapacity, boolean secondChance) {
		int value = individualValue(individual, items);
		int weight = individualWeight(individual, items);
		if(weight > maxCapacity) {
			int chance = Utility.generateRandomBoundedInt(0, 100);
			if (chance > 50 && secondChance == true) {
				individual[indexOfMaxWeight(individual, items)] = 0;
				return fitnessCalculation(individual, items, maxCapacity, secondChance);
			}
			else return 0;
		} 
		else return value;
	}

	@Override
	public String toString() {
		String ret = "";
		for (byte[] b : population) {
			ret += "Ratio: " + Utility.averageRatioOfPopulation(items, b) + " Chromosome: ";
			for(int i = 0; i < b.length; i++)
				ret += b[i];
			ret += "\n";
		}
		return ret;
	}
	
}
