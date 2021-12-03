package fr.rk.aoc.challenge;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Day3 {

    public static long calculateSubmarineConsumption(List<String> input) {
        StringBuilder epsilon = new StringBuilder();
        StringBuilder gamma = new StringBuilder();
        IntStream.range(0, input.get(0).length()).forEach(i -> {
            long nb1 = input.stream().filter(measure -> measure.charAt(i) == '1').count();
            if (nb1 > (input.size() / 2)) {
                epsilon.append("1");
                gamma.append("0");
            } else {
                epsilon.append("0");
                gamma.append("1");
            }
        });
        return Integer.parseInt(epsilon.toString(),2) * Integer.parseInt(gamma.toString(), 2);
    }

    public static long calculateSubmarineLifeSupport(List<String> input) {
        String oxygen = getOxygenOrC02Value(input, true, 0);
        String co2 = getOxygenOrC02Value(input, false, 0);
        return Integer.parseInt(oxygen,2) * Integer.parseInt(co2, 2);
    }

    private static String getOxygenOrC02Value(List<String> input, boolean isOxygen, int currPos) {
        long nb1 = input.stream().filter(measure -> measure.charAt(currPos) == '1').count();
        long nb0 = input.size() - nb1;
        char criteria;
        if(isOxygen) {
            criteria = nb1 >= nb0 ? '1' : '0';
        } else {
            criteria = nb0 <= nb1 ? '0' : '1';
        }
        List<String> subList = input.stream().filter(measure -> measure.charAt(currPos) == criteria).collect(Collectors.toList());
        if(subList.size() == 1) {
            return subList.get(0);
        } else {
            return getOxygenOrC02Value(subList, isOxygen, currPos + 1);
        }
    }
}
