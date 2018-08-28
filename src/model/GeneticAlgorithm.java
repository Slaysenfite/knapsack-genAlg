package model;

import java.util.ArrayList;
import java.util.Collections;

import utilities.Consts;
import utilities.FileUtility;

public class GeneticAlgorithm {
	
	public static int fitnessCalculation(byte[] individual, ArrayList<Item> items) {
		int value = individualValue(individual, items);
		int weight = individualWeight(individual, items);
		if(weight > Consts.KNAPSACK_CAPACITY) {
			int chance = FileUtility.generateRandomBoundedInt(0, 100);
			if (chance > 50 && Consts.SECOND_CHANCE == true) {
				individual[indexOfMaxWeight(individual, items)] = 0;
				return fitnessCalculation(individual, items);
			}
			else return 0;
		} 
		else return value;
	}

    public static void calculateFitnessForEachIndividual(ArrayList<Individual> individuals, ArrayList<Item> items) {
	    for(int i = 0; i < individuals.size(); i++){
	        individuals.get(i).setFitness(fitnessCalculation(individuals.get(i).getChromosomeArray(), items));
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

	public static int getIndexofFittessIndividual(ArrayList<Individual> individuals){
	    int index = 0;
	    int max = individuals.get(index).getFitness();
	    for(int i = 1; i < individuals.size(); i++){
	        if(max < individuals.get(i).getFitness()){
	            max = individuals.get(i).getFitness();
	            index = i;
            }
        }
        return index;
    }

    public static void ElisistSelection(Knapsack knapsack, ArrayList<Individual> newIndividuals){
        if(Consts.ELITISM) {
            int eliteIndex = getIndexofFittessIndividual(knapsack.getPopulation());
            newIndividuals.add(knapsack.getPopulation().get(eliteIndex));
            knapsack.getPopulation().get(eliteIndex).setSelected(true);
        }
    }

    public static ArrayList<Individual> rankBasedSelection(Knapsack knapsack) {
        Collections.sort(knapsack.getPopulation());
        //new population
        int chance;
        ArrayList<Individual> newIndividuals = new ArrayList<>();
        ElisistSelection(knapsack, newIndividuals);
        while(newIndividuals.size() < knapsack.getPopulation().size()/2)
        {
            chance = FileUtility.generateRandomBoundedInt(1, knapsack.getPopulation().size());
            for(int i = 0; i < knapsack.getPopulation().size(); i++ ) {
                double rankPercentage = ((i*1.0 + 1)*100)/knapsack.getPopulation().size();
                if(chance >= rankPercentage && !knapsack.getPopulation().get(i).isSelected()) {
                    knapsack.getPopulation().get(i).setSelected(true);
                    newIndividuals.add(knapsack.getPopulation().get(i).clone());
                    break;
                }
            }
        }
        return newIndividuals;
    }

    public static ArrayList<Individual> rouletteSelection(Knapsack knapsack) {
		double totalFitness = sumPopulationFitness(knapsack.getPopulation());
		double[] relFitness = new double[knapsack.getPopulation().size()];
		for(int i = 0; i < relFitness.length; i++) {		
			relFitness[i] = knapsack.getPopulation().get(i).getFitness()/totalFitness;
		}
	    //Generate probability intervals for each individual
		double sumRelFitness = 0;
		double[] probabilities = new double[knapsack.getPopulation().size()];
		for(int i = 0; i < relFitness.length; i++) {		
			sumRelFitness += relFitness[i];
			probabilities[i] = sumRelFitness* 100;
		}
		//new population
		int chance;
		ArrayList<Individual> newIndividuals = new ArrayList<>();
        ElisistSelection(knapsack, newIndividuals);
        while(newIndividuals.size() < knapsack.getPopulation().size()/2)
		{
            chance = FileUtility.generateRandomBoundedInt(0, 100);
            for(int i = 0; i < knapsack.getPopulation().size(); i++ ) {
				if(chance <= probabilities[i] && !knapsack.getPopulation().get(i).isSelected()) {
				    knapsack.getPopulation().get(i).setSelected(true);
                    newIndividuals.add(knapsack.getPopulation().get(i).clone());
					break;
				}
			}
		}		
		return newIndividuals;
	}
	
	public static ArrayList<Individual> randomSelection(Knapsack knapsack) {
		ArrayList<Individual> newIndividuals = new ArrayList<>();
		ArrayList<Integer> randIndex = FileUtility.generateListOfRandomInts(1, knapsack.getPopulation().size());
        ElisistSelection(knapsack, newIndividuals);
        for(int i = 0; i < knapsack.getPopulation().size()/2; i++) {
			newIndividuals.add(knapsack.getPopulation().get(randIndex.get(i)).clone());
		}
		return newIndividuals;
	}
	
	private static int sumPopulationFitness(ArrayList<Individual> population) {
		int sum = 0;
		for(Individual c : population) {
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

	public static ArrayList<Individual> produceNextGeneration(Knapsack knapsack, ArrayList<Item> items, String selectionType){
		ArrayList<Individual> nextGen = new ArrayList<>();
		ArrayList<Individual> offspring = new ArrayList<>();
		ArrayList<Individual> bestOfCurrentGen = new ArrayList<>();
		switch (selectionType){
            case "roulette":
                bestOfCurrentGen = rouletteSelection(knapsack);
                break;
            case "rank":
                bestOfCurrentGen = rankBasedSelection(knapsack);
                break;
            case "random":
                bestOfCurrentGen = randomSelection(knapsack);
                break;
            default:
                System.err.println("Invalid selection mechanism called.");
                break;
        }
		nextGen.addAll(bestOfCurrentGen);
		int randIndex1, randIndex2;
		while(bestOfCurrentGen.size() > 1) {
			randIndex1 = FileUtility.generateRandomBoundedInt(0, bestOfCurrentGen.size() - 1);
			randIndex2 = FileUtility.generateRandomBoundedInt(0, bestOfCurrentGen.size() - 1);
			while(randIndex1 == randIndex2)
				randIndex2 = FileUtility.generateRandomBoundedInt(0, bestOfCurrentGen.size() - 1);
			offspring.addAll(uniformCrossover(bestOfCurrentGen.get(randIndex1).getChromosomeArray(),
					bestOfCurrentGen.get(randIndex2).getChromosomeArray()));
			bestOfCurrentGen.set(randIndex1, null);
			bestOfCurrentGen.set(randIndex2, null);
			bestOfCurrentGen.removeAll(Collections.singletonList(null));
		}
		for(int i = 0; i < offspring.size(); i++) {
            nextGen.add(new Individual(offspring.get(i).mutateChromosome()));
        }
        calculateFitnessForEachIndividual(nextGen, items);
		return nextGen;
	}
	
	public static ArrayList<Individual> uniformCrossover(byte[] p1, byte[] p2) {
		ArrayList<Individual> ret = new ArrayList<>();

		byte[] o1 = new byte[p1.length];
		byte[] o2 = new byte[p1.length];

		int cPoint1 = FileUtility.generateRandomBoundedInt(0, p1.length - 1);
		int cPoint2 = FileUtility.generateRandomBoundedInt(0, p1.length - 1);

		while(cPoint1 == cPoint2) {
            cPoint2 = FileUtility.generateRandomBoundedInt(cPoint1, p1.length);
        }

        if(cPoint1 > cPoint2){
            int temp = cPoint2;
            cPoint2 = cPoint1;
            cPoint1 = temp;
        }


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

		ret.add(new Individual(o1));
		ret.add(new Individual(o2));

		return ret;
	}
	

}
