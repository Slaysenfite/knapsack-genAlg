package utilities;

import model.Individual;
import model.Knapsack;

public class TestUtility {
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
}
