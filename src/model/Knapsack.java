package model;

import java.util.ArrayList;

import utilities.Utility;

public class Knapsack {
	
	private ArrayList<Item> items;
	private ArrayList<Chromosome> population;
	
	public Knapsack(ArrayList<Item> items, int populationSize) {
		this.items = items;
		population = new ArrayList<>();
		for(int i = 0; i < populationSize; i++) {
			population.add(new Chromosome(items.size()));
		}
	}
	
	public Knapsack(ArrayList<Item> items, ArrayList<Chromosome> population) {
		this.items = items;
		this.population = population;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public ArrayList<Chromosome> getPopulation() {
		return population;
	}

	@Override
	public String toString() {
		String ret = "";
		for (Chromosome b : population) {
			ret += "Ratio: " + Utility.averageRatioOfPopulation(items, b.getChromosomeArray()) 
			+ " Fitness: " + b.getFitness()
			+ " Total Weight: " + GeneticAlgorithm.individualWeight(b.getChromosomeArray(), items)
			+ " Chromosome: " + b.chromosomeToString();
		}
		return ret;
	}
	
}
