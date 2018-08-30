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

    private static void unflagSelectionForPopuation(Population population){
        for(Individual individual: population.getPopulation())
            individual.setSelected(false);
    }

    public static void elitistSelection(Population population, ArrayList<Individual> newIndividuals){
        if(Consts.ELITISM) {
            int eliteIndex = getIndexofFittessIndividual(population.getPopulation());
            newIndividuals.add(population.getPopulation().get(eliteIndex));
            population.getPopulation().get(eliteIndex).setSelected(true);
        }
    }

    public static ArrayList<Individual> tournamentSelection(Population population){
        ArrayList<Individual> newIndividuals = new ArrayList<>();
        ArrayList<Individual> tournamentList;
        elitistSelection(population, newIndividuals);
        int tournamentSize = 6;
        int tournamentIndex;
        while(newIndividuals.size() < population.getPopulation().size()/2){
            tournamentList = new ArrayList<>();
            while(tournamentList.size() < tournamentSize) {
                tournamentIndex = FileUtility.generateRandomBoundedInt(0, population.getPopulation().size() - 1);
                if (!population.getPopulation().get(tournamentIndex).isSelected()) {
                    population.getPopulation().get(tournamentIndex).setSelected(true);
                    tournamentList.add(population.getPopulation().get(tournamentIndex));
                }
            }
            Collections.sort(tournamentList);
            Collections.reverse(tournamentList);
            if(!tournamentList.isEmpty()){
                newIndividuals.add(tournamentList.get(0));
                for(int i = 1; i < tournamentList.size(); i++)
                    tournamentList.get(i).setSelected(false);
            }
        }
        unflagSelectionForPopuation(population);
        return newIndividuals;
    }


    public static ArrayList<Individual> rankBasedSelection(Population population) {
        Collections.sort(population.getPopulation());
        //new population
        int chance;
        ArrayList<Individual> newIndividuals = new ArrayList<>();
        elitistSelection(population, newIndividuals);
        while(newIndividuals.size() < population.getPopulation().size()/2)
        {
            chance = FileUtility.generateRandomBoundedInt(1, population.getPopulation().size());
            for(int i = 0; i < population.getPopulation().size(); i++ ) {
                double rankPercentage = ((i*1.0 + 1)*100)/ population.getPopulation().size();
                if(chance >= rankPercentage && !population.getPopulation().get(i).isSelected()) {
                    population.getPopulation().get(i).setSelected(true);
                    newIndividuals.add(population.getPopulation().get(i).clone());
                    break;
                }
            }
        }
        unflagSelectionForPopuation(population);
        return newIndividuals;
    }

    public static ArrayList<Individual> rouletteSelection(Population population) {
		double totalFitness = sumPopulationFitness(population.getPopulation());
		double[] relFitness = new double[population.getPopulation().size()];
		for(int i = 0; i < relFitness.length; i++) {
		    if (totalFitness <= 0) relFitness[i] = 0;
		    else relFitness[i] = population.getPopulation().get(i).getFitness()/totalFitness;
		}
	    //Generate probability intervals for each individual
		double sumRelFitness = 0;
		double[] probabilities = new double[population.getPopulation().size()];
		for(int i = 0; i < relFitness.length; i++) {		
			sumRelFitness += relFitness[i];
			probabilities[i] = sumRelFitness* 100;
		}
		//new population
		int chance;
		ArrayList<Individual> newIndividuals = new ArrayList<>();
        elitistSelection(population, newIndividuals);
        while(newIndividuals.size() < population.getPopulation().size()/2)
		{
            chance = FileUtility.generateRandomBoundedInt(0, 100);
            for(int i = 0; i < population.getPopulation().size(); i++ ) {
				if(chance <= probabilities[i] && !population.getPopulation().get(i).isSelected()) {
				    population.getPopulation().get(i).setSelected(true);
                    newIndividuals.add(population.getPopulation().get(i).clone());
					break;
				}
			}
		}
        unflagSelectionForPopuation(population);
        return newIndividuals;
	}
	
	public static ArrayList<Individual> randomSelection(Population population) {
		ArrayList<Individual> newIndividuals = new ArrayList<>();
		ArrayList<Integer> randIndex = FileUtility.generateListOfRandomInts(1, population.getPopulation().size());
        elitistSelection(population, newIndividuals);
        for(int i = 0; i < population.getPopulation().size()/2; i++) {
			newIndividuals.add(population.getPopulation().get(randIndex.get(i)).clone());
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

	public static ArrayList<Individual> produceNextGeneration(Population population, ArrayList<Item> items, String selectionType){
		ArrayList<Individual> nextGen = new ArrayList<>();
		ArrayList<Individual> offspring = new ArrayList<>();
		ArrayList<Individual> bestOfCurrentGen = new ArrayList<>();
		switch (selectionType){
            case "tournament":
                bestOfCurrentGen = tournamentSelection(population);
                break;
            case "roulette":
                bestOfCurrentGen = rouletteSelection(population);
                break;
            case "rank":
                bestOfCurrentGen = rankBasedSelection(population);
                break;
            case "random":
                bestOfCurrentGen = randomSelection(population);
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
			offspring.addAll(twoPointCrossover(bestOfCurrentGen.get(randIndex1).getChromosomeArray(),
					bestOfCurrentGen.get(randIndex2).getChromosomeArray()));
			bestOfCurrentGen.set(randIndex1, null);
			bestOfCurrentGen.set(randIndex2, null);
			bestOfCurrentGen.removeAll(Collections.singletonList(null));
		}
		for(int i = 0; i < offspring.size(); i++) {
            nextGen.add(new Individual(offspring.get(i).mutateChromosome()));
        }
        calculateFitnessForEachIndividual(nextGen, items);
        if(Consts.OVERWEIGHT_REPLACEMENT){
            Collections.sort(bestOfCurrentGen);
            Collections.reverse(bestOfCurrentGen);
            for(int i = 0; i < nextGen.size(); i++) {
                if(nextGen.get(i).getFitness() == 0) {
                    for(int c = 0; c < bestOfCurrentGen.size(); c++) {
                        if(!bestOfCurrentGen.get(c).isSelected()) {
                            bestOfCurrentGen.get(c).setSelected(true);
                            bestOfCurrentGen.get(c).mutateChromosome();
                            nextGen.add(bestOfCurrentGen.get(c));
                        }
                        nextGen.remove(i);
                        break;
                    }
                }
            }
        }
		return nextGen;
	}
	
	public static ArrayList<Individual> twoPointCrossover(byte[] p1, byte[] p2) {
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
