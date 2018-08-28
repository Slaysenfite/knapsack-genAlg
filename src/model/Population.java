package model;

import java.util.ArrayList;

import utilities.FileUtility;

public class Population {
	
	private ArrayList<Item> items;
	private ArrayList<Individual> population;
	
	public Population(ArrayList<Item> items, int populationSize) {
		this.items = items;
		population = new ArrayList<>();
		for(int i = 0; i < populationSize; i++) {
			population.add(new Individual(items.size()));
		}
	}

    public Population(ArrayList<Item> items, ArrayList<Individual> population) {
        this.items = items;
        this.population = population;
    }

	public void setPopulation(ArrayList<Individual> population) {
		this.population = population;
	}

    public ArrayList<Individual> getPopulation() {
		return population;
	}

	public void enforceStrictWeightConstraint(){
	    for(int i = 0 ; i < this.population.size(); i++){
	        if(GeneticAlgorithm.individualWeight(population.get(i).getChromosomeArray(), items) <= 0){
                population.remove(i);
                population.add(i, new Individual(items.size()));
            }
        }
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
