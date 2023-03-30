package org.demenkov.labs.networks.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.function.Predicate;

public final class FileModificator {

    private FileModificator() {

    }

    public static double[][] read(String path) throws IOException {
        String stringData = Files.readString(Path.of(path));
        return Arrays.stream(stringData.split("\r\n"))
                .map(vector -> Arrays.stream(vector.split(","))
                        .map(String::trim)
                        .filter(Predicate.not(String::isBlank))
                        .mapToDouble(Double::parseDouble)
                        .toArray())
                .toArray(double[][]::new);
    }

    public static void write(String path, double[][] data) throws IOException {
        StringJoiner joiner = new StringJoiner("\r\n");
        for (double[] row : data) {
            StringJoiner innerJoiner = new StringJoiner(",");
            for (double value : row) {
                innerJoiner.add(String.valueOf(value));
            }
            joiner.add(innerJoiner.toString());
        }
        Files.writeString(Path.of(path), joiner.toString());
    }
}
