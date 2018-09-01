package utilities;

public final class Consts {
    public static final int MAX_ITEM_WEIGHT = 50;
    public static final int MIN_ITEM_WEIGHT = 2;
    public static final int MAX_ITEM_VALUE = 500;
    public static final int MIN_ITEM_VALUE = 5;
    public static final int ITEM_NUM = 1500;

    public static final int KNAPSACK_CAPACITY = 19000;
    public static final int MIN_GENERATIONS = 10000;
    public static final int POPULATION_SIZE = 100;
    public static final String SELECTION = "tournament";

    public static final boolean SECOND_CHANCE = false;
    public static final boolean OVERWEIGHT_REPLACEMENT = false;
    public static final boolean ELITISM = true;
    public static final boolean OUTPUT_TEST_DATA = true;
    public static final boolean BIASED_CREATION = false;
    public static final boolean REPAIR = true;

    public static final String DATA_FILE_NAME = "C:\\development\\knapsackOptimisation_GA\\knapsack-genAlg" +
            "\\TestCases\\testCase1500.txt";
    public static final String TEST_FILE_NAME = "C:\\development\\knapsackOptimisation_GA\\knapsack-genAlg\\TestResults\\"
            + SELECTION + "_pop" + POPULATION_SIZE + "_gen" + MIN_GENERATIONS
            + "_chance-" + SECOND_CHANCE + "_repair-" + REPAIR + ".csv";
}
