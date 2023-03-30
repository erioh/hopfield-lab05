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

        normalize(learningMatrix, length);
        write(DATA_RESULT_TXT, learningMatrix);
    }

    private void normalize(double[][] learningMatrix, int length) {
        for (int i = 0; i < learningMatrix.length; i++) {
            for (int j = 0; j < learningMatrix[i].length; j++) {
                learningMatrix[i][j] /= length;
            }
        }
    }

    private void train(double[][] learningMatrix, double[] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                if (i != j) {
                    learningMatrix[i][j] += convert(data[i]) * convert(data[j]);
                }
            }
        }
    }

    double convert(double input) {
        return input > 0 ? 1 : -1;
    }

    public void check(double[][] dataToBeChecked, double[][] ideal) throws IOException {
        double[][] learningMatrix = read(DATA_RESULT_TXT);

        for (double[] data : dataToBeChecked) {
            guess(data, learningMatrix, ideal, 0);
        }

    }

    private void guess(double[] data, double[][] learningMatrix, double[][] ideal, int count) {
        if (count < 10) {
            double[] result = new double[data.length];
            for (int i = 0; i < learningMatrix.length; i++) {
                for (int j = 0; j < learningMatrix[i].length; j++) {
                    result[i] += convert(data[j]) * learningMatrix[i][j];
                }
            }
            for (int i = 0; i < result.length; i++) {
                result[i] = result[i] > 0 ? 1 : 0;
            }
            boolean done = false;
            for (double[] row : ideal) {
                if (Arrays.equals(row, result)) {
                    System.out.println("It's " + Arrays.toString(row));
                    System.out.printf("Number of used iterations is %d%n", count);
                    done = true;
                    break;
                }
            }
            if (!done) {
                guess(result, learningMatrix, ideal, ++count);
            }
        } else {
            System.out.println("Unable to find correct result");
        }

    }
}
