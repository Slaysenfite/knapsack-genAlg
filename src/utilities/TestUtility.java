package utilities;

import model.Individual;
import model.Population;

import java.io.*;
import java.util.ArrayList;

public class TestUtility {

    public static ArrayList<Double> generationMean = new ArrayList<>();
    public static ArrayList<Integer> generationGreatest = new ArrayList<>();
    public  static ArrayList<Integer> generationNumber = new ArrayList<>();

    public static int maxFitnessOfGeneration(Population population){
        int max = population.getPopulation().get(0).getFitness();
        for(int i = 1; i < population.getPopulation().size(); i++){
            if(max <= population.getPopulation().get(i).getFitness())
                max = population.getPopulation().get(i).getFitness();
        }
        return max;
    }

    public static double meanFitnessOfGeneration(Population population){
        double sum = 0.0;
        for(Individual i : population.getPopulation()){
            sum += i.getFitness();
        }
        return sum/ population.getPopulation().size();
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
