package model;

import java.util.ArrayList;

import utilities.FileUtility;

public class Knapsack {
	
	private ArrayList<Item> items;
	private ArrayList<Individual> population;
	
	public Knapsack(ArrayList<Item> items, int populationSize) {
		this.items = items;
		population = new ArrayList<>();
		for(int i = 0; i < populationSize; i++) {
			population.add(new Individual(items.size()));
		}
	}

    public Knapsack(ArrayList<Item> items, ArrayList<Individual> population) {
        this.items = items;
        this.population = population;
    }

	public void setPopulation(ArrayList<Individual> population) {
		this.population = population;
	}

    public ArrayList<Individual> getPopulation() {
		return population;
	}

	@Override
	public String toString() {
		String ret = "";
		for (Individual b : population) {
			ret += "Ratio: " + FileUtility.averageRatioOfPopulation(items, b.getChromosomeArray())
			+ " Fitness: " + b.getFitness()
			+ " Total Weight: " + GeneticAlgorithm.individualWeight(b.getChromosomeArray(), items)
			+ " Individual: " + b.chromosomeToString();
		}
		return ret;
	}
}
