package utilities;

import model.Individual;
import model.Knapsack;

import java.io.*;
import java.util.ArrayList;

public class TestUtility {

    public static ArrayList<Double> generationMean = new ArrayList<>();
    public static ArrayList<Integer> generationGreatest = new ArrayList<>();
    public  static ArrayList<Integer> generationNumber = new ArrayList<>();

    public static int maxFitnessOfGeneration(Knapsack knapsack){
        int max = knapsack.getPopulation().get(0).getFitness();
        for(int i = 1; i < knapsack.getPopulation().size(); i++){
            if(max <= knapsack.getPopulation().get(i).getFitness())
                max = knapsack.getPopulation().get(i).getFitness();
        }
        return max;
    }

    public static double meanFitnessOfGeneration(Knapsack knapsack){
        double sum = 0.0;
        for(Individual i : knapsack.getPopulation()){
            sum += i.getFitness();
        }
        return sum/knapsack.getPopulation().size();
    }

    public static void writeTestDataToFile(String filename) throws IOException {
        File fout = new File(filename);
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        if(generationNumber.size() == generationMean.size() && generationMean.size() == generationGreatest.size()){
            bw.append("genNum,genMean,genBest\n");
            for (int i = 0; i < generationNumber.size(); i++) {
                bw.append(generationNumber.get(i) + "," +
                        generationMean.get(i) + "," +
                        generationGreatest.get(i) +
                        "\n");
            }
        }
        bw.close();
    }

}
