package utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import model.Item;

import static utilities.Consts.*;

public class FileUtility {

	public static int generateRandomBoundedInt(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}

	public static ArrayList<Integer> generateListOfRandomInts(int min, int max){
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = min; i < max; i++)
			list.add(i);
		Collections.shuffle(list);
		return list;
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
		
		int weight;
		int value;
	 
		for (int i = 0; i < numItems; i++) {
			weight = generateRandomBoundedInt(MIN_ITEM_WEIGHT, MAX_ITEM_WEIGHT);
			value = generateRandomBoundedInt(MIN_ITEM_VALUE, MAX_ITEM_VALUE);
			bw.append(weight + "	" + value);
			if(i == numItems - 1) break;
			else bw.newLine();
		}
	 
		bw.close();
	}

	public static void main(String[] args) {
		try {
			writeToFile(FILE_NAME, 500);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
