package model;

import utilities.Utility;

public class Chromosome implements Comparable<Chromosome>{
	
	private byte[] chromosome;
	private int fitness;

	public Chromosome(int size) {
		this.fitness = 0;
		chromosome = new byte[size];
		for(int i = 0; i < size; i++) 
			chromosome[i] = (byte) Utility.generateRandomBoundedInt(0, 1);
	}
	
	public Chromosome(byte[] chromosome) {
		this.chromosome = chromosome;
	}
	
	public int getSize() {
		return chromosome.length;
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

	public byte[] getChromosomeArray() {
		return chromosome;
	}

	public String chromosomeToString() {
		String ret = "";
		for (byte gene : this.chromosome)
			ret += gene;
		return ret + "\n";
	}
	
	public byte[] mutateChromosome(byte[] chromosome) {
		for(int i = 0; i < chromosome.length; i++) {			
			int chance = Utility.generateRandomBoundedInt(1, 100);
			if(chance <= 2) {
				if(chromosome[i] == 0) chromosome[i] = 1;
				else if(chromosome[i] == 1) chromosome[i] = 0;
			}
		}
		return chromosome;
	}

	@Override
	public int compareTo(Chromosome c) {
		if(this.fitness > c.getFitness()) return 1;
		if(this.fitness == c.getFitness()) return 0;
		if(this.fitness < c.getFitness()) return -1;
		else return -2;
	}
	

}