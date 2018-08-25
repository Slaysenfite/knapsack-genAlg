package model;

import utilities.Utility;

public class Population {
	
	public static byte[] createChromosome(int size) {
		byte[] chromosome = new byte[size];
		for(int i = 0; i < size; i++) 
			chromosome[i] = (byte) Utility.generateRandomBoundedInt(0, 1);
		return chromosome;
	}
}
	

