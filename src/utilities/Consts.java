package utilities;

public final class Consts {
    public static final int MAX_ITEM_WEIGHT = 200;
    public static final int MIN_ITEM_WEIGHT = 2;
    public static final int MAX_ITEM_VALUE = 5000;
    public static final int MIN_ITEM_VALUE = 5;

    public static final int KNAPSACK_CAPACITY = 33500;
    public static final int MIN_GENERATIONS = 1000;
    public static final int POPULATION_SIZE = 100;
    public static final String SELECTION = "random";

    public static final boolean SECOND_CHANCE = true;
    public static final boolean OVERWEIGHT_REPLACEMENT = true;
    public static final boolean ELITISM = true;
    public static final boolean OUTPUT_TEST_DATA = true;
    public static final boolean STRICT_WEIGHT_CONSTRAINT = false;
    public static final boolean BIASED_CREATION = false;

    public static final String DATA_FILE_NAME = "C:\\development\\knapsackOptimisation_GA\\knapsack-genAlg\\TestCases\\testCase1500.txt";
    public static final String TEST_FILE_NAME = "C:\\development\\knapsackOptimisation_GA\\knapsack-genAlg\\TestResults\\"
            + Consts.SELECTION + "_pop" + Consts.POPULATION_SIZE + "_gen" + Consts.MIN_GENERATIONS
            + "_chance-" + Consts.SECOND_CHANCE + ".csv";
}
