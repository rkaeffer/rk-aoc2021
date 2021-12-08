package fr.rk.aoc.challenge;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public final class Day8 {

    /**
     * Map where index is number of 7-segments turn on and value the digits corresponding
     * Values is -1 if no digits or multiple digits match the number of turned on segments
     */
    public static int[] simpleDigitsMap =  new int[] {-1,-1,1,7,4,-1,-1,8};

    //First we will have automatic guess of 1, 4, 7, 8 configuration
    //We need to guess the 6 other digits
    //0,6,9 which have 6 segments
    //2,3,5 which have 5 segments
    //Next we can determine 0 by wich of 6 length
    // 6 --> common letter between 1,7,8, in 0 & 9 but not in 6, can be improve ...
    // 9 --> contains all letter of 4 in six size digits
    // 0 --> The other one of six size
    // 3 --> contains all letter of 1 in five size digits
    // 5 ->  Has one minus letter than 6 (3 has two, 2 has two)
    // 2 -> The other one of five size

    public static long countSimpleDigitInOutput(List<String> input) {
        long result = 0;
        for(String inOut : input) {
            String output = inOut.split("\\|")[1];
            for(String out : output.trim().split(" ")) {
                if(simpleDigitsMap[out.length()] != -1) {
                    result++;
                }
            }
        }
        return result;
    }

    public static long calculateSumOfOutputDigits(List<String> input) {
        long result = 0;
        for(String inOut : input) {
            result += Day8.determineNumberDisplayOn7Digits(inOut);
        }
        return result;
    }

    private static long determineNumberDisplayOn7Digits(String input) {
        String[] inOut = input.split("\\|");
        Map<String, Integer> digitsMap = Day8.guessDigits(inOut[0]);
        Map<String, Integer> orderDigitsMap = new HashMap<>();
        digitsMap.forEach( (k,v) -> {
            char[] keyArray = k.toCharArray();
            Arrays.sort(keyArray);
            orderDigitsMap.put(new String(keyArray), v);
        });

        StringBuilder displayNumber = new StringBuilder();
        for(String out : inOut[1].trim().split(" ")) {
            char[] outArray = out.toCharArray();
            Arrays.sort(outArray);
            displayNumber.append(orderDigitsMap.get(new String(outArray)));
        }
        return Long.parseLong(displayNumber.toString());
    }

    private static Map<String, Integer> guessDigits(String inputPart) {
        String[] digits = inputPart.trim().split(" ");
        Map<String, Integer> digitsMap = new HashMap<>();
        Map<Integer, String> reverseDigitsMap = new HashMap<>();
        Day8.initDigitsMapsForGuessing(digitsMap, reverseDigitsMap, digits);
        // 6 --> common letter between 1,7,8, in 0 & 9 but not in 6
        String commonSix = Day8.commonLettersBetweenDigits(reverseDigitsMap.get(1), reverseDigitsMap.get(4), reverseDigitsMap.get(7));
        String six = Day8.nonCommonLetterBetweenCommonSequenceAndOtherDigits(commonSix, Arrays.stream(digits).filter(d -> d.length() == 6).collect(Collectors.toList()));
        digitsMap.put(six, 6);
        reverseDigitsMap.put(6, six);
        // 9 --> contains all letter of four in six size
        Optional<String> nine = Arrays.stream(digits).filter(d -> d.length() == 6).filter(d -> Day8.containsAllLetterOf(d, reverseDigitsMap.get(4))).findFirst();
        digitsMap.put(nine.get(), 9);
        reverseDigitsMap.put(9, nine.get());
        //0 --> remaining six digits
        Optional<String> zero = Arrays.stream(digits).filter(d -> d.length() == 6).filter(d -> digitsMap.get(d) == null).findFirst();
        digitsMap.put(zero.get(), 0);
        reverseDigitsMap.put(0, zero.get());
        // 3 --> contains all letter of one of five size
        Optional<String> three = Arrays.stream(digits).filter(d -> d.length() == 5).filter(d -> Day8.containsAllLetterOf(d, reverseDigitsMap.get(1))).findFirst();
        digitsMap.put(three.get(), 3);
        reverseDigitsMap.put(3, three.get());
        // 5 ->  Has one minus letter than 6 (3 has two, 2 has two)
        Optional<String> five = Arrays.stream(digits).filter(d -> d.length() == 5).filter(d -> Day8.numberOfCommonLetter(d, reverseDigitsMap.get(6)) == 5).findFirst();
        digitsMap.put(five.get(), 5);
        reverseDigitsMap.put(5, five.get());
        //2 --> remaining five digits
        Optional<String> two = Arrays.stream(digits).filter(d -> d.length() == 5).filter(d -> digitsMap.get(d) == null).findFirst();
        digitsMap.put(two.get(), 2);
        reverseDigitsMap.put(2, two.get());
        //Day8.displayMapping(inputPart, digitsMap);
        return digitsMap;
    }

    /**
     * Method which determine 1, 4, 7 & 8 digits mapping and set it into digits mapping
     * @param initialMaps digits mapping maps
     * @param reverseMap digits mapping maps reverse
     * @param digits input digits
     */
    private static void initDigitsMapsForGuessing( Map<String, Integer> initialMaps, Map<Integer, String> reverseMap, String[] digits) {
        for(String digit : digits) {
            if(simpleDigitsMap[digit.length()] != -1) {
                initialMaps.put(digit, simpleDigitsMap[digit.length()]);
                reverseMap.put(simpleDigitsMap[digit.length()], digit);
            }
        }
    }

    private static boolean containsAllLetterOf(String search, String reference) {
        boolean containsAll = true;
        for(char c : reference.toCharArray()) {
            containsAll &= search.contains(String.valueOf(c));
        }
        return containsAll;
    }

    private static int numberOfCommonLetter(String search, String reference) {
        int nbMatch = 0;
        for(char c : reference.toCharArray()) {
            if(search.contains(String.valueOf(c))) {
                nbMatch ++;
            };
        }
        return nbMatch;
    }

    private static String nonCommonLetterBetweenCommonSequenceAndOtherDigits(String commonLetter, List<String> digits) {
        for (String digit : digits) {
            boolean allCharsPresent = true;
            for(char c : commonLetter.toCharArray()) {
                allCharsPresent &= digit.contains(String.valueOf(c));
            }
            if(!allCharsPresent) {
                return digit;
            }
        }
        return "";
    }

    private static String commonLettersBetweenDigits(String...digits) {
        String allLetter = "abcdefg";
        StringBuilder commonLetter = new StringBuilder();
        for(char c : allLetter.toCharArray()) {
            boolean common = true;
            for(String digit : digits) {
                common &= digit.contains(String.valueOf(c));
            }
            if(common) {
                commonLetter.append(c);
            }
        }
        return commonLetter.toString();
    }

    private static void displayMapping(String input, Map<String, Integer> digitsMap) {
        log.info("Input is {}", input);
        log.info("Mapping is {}", input);
        digitsMap.forEach( (k,v) -> {
           log.info("For key {} : {}", k , v);
        });
    }

}
