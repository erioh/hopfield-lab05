package org.demenkov.labs.networks;

import org.demenkov.labs.networks.algorinths.HopfieldAlgorithm;

import java.io.IOException;

import static org.demenkov.labs.networks.files.FileModificator.read;

public class Main {
    public static void main(String[] args) throws IOException {
        HopfieldAlgorithm algorithm = new HopfieldAlgorithm();
        if (args.length > 0 && args[0].equals("train")) {
            double[][] trainingData = read("data/TrainingData.txt");
            algorithm.learn(trainingData);
        } else {
            double[][] dataToBeChecked = read("data/CheckData.txt");
            algorithm.check(dataToBeChecked);
        }
    }
}
