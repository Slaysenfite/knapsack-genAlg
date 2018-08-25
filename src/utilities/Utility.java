package utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

import model.Item;

public class Utility {
	
	public static int MAX_WEIGHT = 40;
	public static int MIN_WEIGHT = 2;

	public static int MAX_VALUE = 75;
	public static int MIN_VALUE = 5;
	
	public static int generateRandomBoundedInt(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	public static double distanceBetweenItems(Item i1, Item i2) {
		return Math.sqrt(Math.pow(Math.abs(i2.getWeight() - i1.getWeight()),2)
				+ Math.pow(Math.abs(i2.getValue() - i1.getValue()),2));
	}
	
	public static double averageRatioOfPopulation(ArrayList<Item> items, byte[] chromosome) {
		double sum = 0;
		for(int i = 0; i < items.size(); i++) {
			if(chromosome[i] == 1) 
				sum += items.get(i).valueVersusWeightRatio();
			else if(chromosome[i] == 0)
				continue;
		}
		return sum/items.size();
	}
	
	public static int indexOfMax(ArrayList<Item> items) {
		int index = 0;
		double max = items.get(index).valueVersusWeightRatio();
		for(int i = 1; i < items.size(); i++) {
			if(max <= items.get(i).valueVersusWeightRatio()) {
				max = items.get(i).valueVersusWeightRatio();
				index = i;
			}
		}
		return index;
	}
	
	public static String chromosomeToString(byte[] chromosome) {
		String ret = "";
		for (byte gene : chromosome)
			ret += gene;
		return ret;
	}
	
	public static ArrayList<Item> readDataFile(String filename) {
		FileReader file;
		ArrayList<Item> items = new ArrayList<>();
		Scanner input;
		try {
			file = new FileReader(filename);
			input = new Scanner(file);
			while(input.hasNextLine()) {
				items.add(new Item(input.nextInt(), input.nextInt()));
			}
			input.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return items;
	}
	
	private static void writeToFile(String filename, int numItems) throws IOException {
		File fout = new File(filename);
		FileOutputStream fos = new FileOutputStream(fout);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		int weight = 0;
		int value = 0;
	 
		for (int i = 0; i < numItems; i++) {
			weight = generateRandomBoundedInt(MIN_WEIGHT, MAX_WEIGHT);
			value = generateRandomBoundedInt(MIN_VALUE, MAX_VALUE);
			bw.append(weight + "	" + value);
			if(i == numItems - 1) break;
			else bw.newLine();
		}
	 
		bw.close();
	}

	public static void main(String[] args) {
		try {
			writeToFile("TestCases//testCase1500.txt", 1500);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
