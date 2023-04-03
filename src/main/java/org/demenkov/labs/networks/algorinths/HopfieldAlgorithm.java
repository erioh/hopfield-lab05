package org.demenkov.labs.networks.algorinths;

import java.io.IOException;
import java.util.Arrays;

import static org.demenkov.labs.networks.files.FileModificator.read;
import static org.demenkov.labs.networks.files.FileModificator.write;

public class HopfieldAlgorithm {

    public static final String DATA_RESULT_TXT = "data/result.txt";

    public void learn(double[][] trainingData) throws IOException {
        if (trainingData.length == 0) {
            throw new IllegalArgumentException("Empty training data");
        }
        int length = trainingData[0].length;
        double[][] learningMatrix = new double[length][length];

        for (double[] data : trainingData) {
            train(learningMatrix, data);
        }
        write(DATA_RESULT_TXT, learningMatrix);
    }

    private void train(double[][] learningMatrix, double[] data) {
        double[] convertedData = convert(data);
        for (int i = 0; i < convertedData.length; i++) {
            for (int j = 0; j < convertedData.length; j++) {
                if (i != j) {
                    learningMatrix[i][j] += convertedData[i] * convertedData[j];
                }
            }
        }
    }

    private double[] convert(double[] input) {
        double[] result = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            result[i] = input[i] > 0 ? 1 : -1;
        }
        return result;
    }

    public void check(double[][] dataToBeChecked) throws IOException {
        double[][] learningMatrix = read(DATA_RESULT_TXT);

        for (double[] data : dataToBeChecked) {
            guess(data, learningMatrix, null, 0);
        }
    }

    private void guess(double[] data, double[][] learningMatrix, double[] previousGuess, int count) {
        if (count < 10) {
            double[] currentGuess = new double[data.length];
            double[] convertedData = convert(data);
            for (int i = 0; i < learningMatrix.length; i++) {
                for (int j = 0; j < learningMatrix[i].length; j++) {
                    currentGuess[i] += convertedData[j] * learningMatrix[i][j];
                }
            }
            for (int i = 0; i < currentGuess.length; i++) {
                currentGuess[i] = currentGuess[i] > 0 ? 1 : 0;
            }
            boolean done = false;
            if (Arrays.equals(currentGuess, previousGuess)) {
                done = true;
                System.out.println("It's " + Arrays.toString(currentGuess));
                System.out.printf("Number of used iterations is %d%n", count);
            }
            if (!done) {
                guess(currentGuess, learningMatrix, currentGuess, ++count);
            }
        } else {
            System.out.println("Unable to find correct result");
        }

    }
}
