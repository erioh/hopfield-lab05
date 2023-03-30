package org.demenkov.labs.networks;

import org.demenkov.labs.networks.algorinths.HopfieldAlgorithm;

import java.io.IOException;

import static org.demenkov.labs.networks.files.FileModificator.read;

public class Main {
    public static void main(String[] args) throws IOException {
        double[][] trainingData = read("data/TrainingData.txt");
        HopfieldAlgorithm algorithm = new HopfieldAlgorithm();
        algorithm.learn(trainingData);
        double[][] dataToBeChecked = read("data/CheckData.txt");
        algorithm.check(dataToBeChecked);
    }
}
