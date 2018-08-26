package model;

import java.util.ArrayList;
import java.util.Collections;

import utilities.Utility;

public class GeneticAlgorithm {
	
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
	
	public static void calculateFitnessForEachIndividual(Knapsack knapsack, int maxCapacity, boolean secondChance) {
		for(int i = 0; i < knapsack.getPopulation().size(); i++) {
			knapsack.getPopulation().get(i).setFitness(fitnessCalculation(knapsack.getPopulation().get(i).getChromosomeArray(), knapsack.getItems(), maxCapacity, secondChance));
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
	
	
	public static ArrayList<Chromosome> rouletteSelection(Knapsack knapsack) {
		Collections.sort(knapsack.getPopulation());
		double totalFitness = sumFitnesses(knapsack.getPopulation());
		double[] relFitness = new double[knapsack.getPopulation().size()];
		for(int i = 0; i < relFitness.length; i++) {		
			relFitness[i] = knapsack.getPopulation().get(i).getFitness()/totalFitness;
		}
	    //Generate probability intervals for each individual
		double sumRelFitness = 0;
		double[] probs = new double[knapsack.getPopulation().size()];
		for(int i = 0; i < relFitness.length; i++) {		
			sumRelFitness += relFitness[i];
			probs[i] = sumRelFitness* 10000;
		}
		//new population
		int chance = 0;
		int newPopCount = 0;
		ArrayList<Chromosome> newChromosomes = new ArrayList<>();
		while(newPopCount < knapsack.getPopulation().size()/2)
		{
			for(int i = 0; i < knapsack.getPopulation().size(); i++) {
				chance = Utility.generateRandomBoundedInt(1, 100);
				if(chance <= probs[i]) {
					newChromosomes.add(knapsack.getPopulation().get(i).clone());
					knapsack.getPopulation().remove(i);
					newPopCount++;
				}
			}
		}		
		return newChromosomes;
	}
	
	public static ArrayList<Chromosome> randomSelection(Knapsack knapsack) {
		int randIndex = 0;
		int newPopCount = 0;
		ArrayList<Chromosome> newChromosomes = new ArrayList<>();
		while(newPopCount < knapsack.getPopulation().size()/2)
		{
			randIndex = Utility.generateRandomBoundedInt(0, knapsack.getPopulation().size() - 1); //may generate same index
			newChromosomes.add(knapsack.getPopulation().get(randIndex).clone());
			knapsack.getPopulation().get(randIndex).setChromosome(null); 
			knapsack.getPopulation().remove(randIndex);
			newPopCount++;
		}		
		return newChromosomes;
	}
	
	private static int sumFitnesses(ArrayList<Chromosome> population) {
		int sum = 0;
		for(Chromosome c : population) {
			sum += c.getFitness();
		}
		return sum;
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
	
	public int indexOfMaxValue(byte[] individual, ArrayList<Item> items) {
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
	
	
	public static Knapsack produceNextGeneration(Knapsack knapsack, ArrayList<Item> items){
		ArrayList<Chromosome> nextGen = new ArrayList<>();
		ArrayList<Chromosome> offspring = new ArrayList<>();
		ArrayList<Chromosome> bestOfCurrentGen = rouletteSelection(knapsack);
		nextGen.addAll(bestOfCurrentGen);
		int randIndex1 = 0;
		int randIndex2 = 0;
		while(bestOfCurrentGen.size() > 1) {
			randIndex1 = Utility.generateRandomBoundedInt(0, bestOfCurrentGen.size() - 1);
			randIndex2 = Utility.generateRandomBoundedInt(0, bestOfCurrentGen.size() - 1);
			while(randIndex1 == randIndex2)
				randIndex2 = Utility.generateRandomBoundedInt(0, bestOfCurrentGen.size() - 1);
			offspring.addAll(uniformCrossover(bestOfCurrentGen.get(randIndex1).getChromosomeArray(), 
					bestOfCurrentGen.get(randIndex2).getChromosomeArray()));
			bestOfCurrentGen.set(randIndex1, null);
			bestOfCurrentGen.set(randIndex2, null);
			bestOfCurrentGen.removeAll(Collections.singletonList(null));
		}
		for(int i = 0; i < offspring.size(); i++)
			offspring.get(i).mutateChromosome();
		nextGen.addAll(offspring);
		return new Knapsack(items, nextGen);
	}
	
	public static ArrayList<Chromosome> uniformCrossover(byte[] p1, byte[] p2) {
		ArrayList<Chromosome> ret = new ArrayList<>();
		
		byte[] o1 = new byte[p1.length];
		byte[] o2 = new byte[p1.length];

		int cPoint1 = Utility.generateRandomBoundedInt(0, p1.length - 1);
		int cPoint2 = Utility.generateRandomBoundedInt(cPoint1, p1.length);		
		if(cPoint1 == cPoint2) cPoint2++;
		
		for(int i = 0; i < cPoint1; i++) {
			o1[i] = p1[i];
			o2[i] = p2[i];
		}
		for(int i = cPoint1; i < cPoint2; i++) {
			o1[i] = p2[i];
			o2[i] = p1[i];
		}
		for(int i = cPoint2; i < p1.length; i++) {
			o1[i] = p1[i];
			o2[i] = p2[i];
		}
		
		ret.add(new Chromosome(o1));
		ret.add(new Chromosome(o2));
		
		return ret;
	}
	

}
