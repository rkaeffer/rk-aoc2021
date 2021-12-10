package fr.rk.aoc.challenge;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public final class Day10 {

    private static final Map<Character, Long> corruptedCharScore = new HashMap<>();
    static {
        corruptedCharScore.put(')', 3L);
        corruptedCharScore.put(']', 57L);
        corruptedCharScore.put('}', 1197L);
        corruptedCharScore.put('>', 25137L);
    }

    private static final Map<Character, Character> closingChar = new HashMap<>();
    static {
        closingChar.put('(', ')');
        closingChar.put('[', ']');
        closingChar.put('{', '}');
        closingChar.put('<', '>');
    }

    private static final Map<Character, Long> closingCharScore = new HashMap<>();
    static {
        closingCharScore.put(')', 1L);
        closingCharScore.put(']', 2L);
        closingCharScore.put('}', 3L);
        closingCharScore.put('>', 4L);
    }

    //What is a corrupted line ?
    //A corrupted line must have at least one close character when correct chunk have been remove
    /**
     * Calculate corrupted lines score
     *
     * @param inputLines corrupted and incomplete lines
     * @return corrupted lines score
     */
    public static long getCorruptedScore(List<String> inputLines) {
        return inputLines.stream()
                //Cleaning correct chunk
                .map(Day10::cleanLine)
                // Identify corrupt line by checking if has any close character
                .filter(line -> line.contains(">") || line.contains("}") || line.contains("]") || line.contains(")"))
                //Identify first bad closing  char and get corresponding score
                .mapToLong(Day10::getFirstBadEnclosing)
                //Return sum of each corrupted line score
                .sum();
    }

    //What is an incomplete line ? Not a corrupted line
    /**
     * Calculate incomplete lines score
     *
     * @param inputLines corrupted and incomplete lines
     * @return incomplete lines score
     */
    public static long getIncompleteScore(List<String> inputLines) {
        List<Long> scores = inputLines.stream()
                //Cleaning correct chunk
                .map(Day10::cleanLine)
                // Identify incomplete line by checking if has any close character
                .filter(line -> !(line.contains(">") || line.contains("}") || line.contains("]") || line.contains(")")))
                //Identify closing sequence needed
                .map(Day10::getClosingSequence)
                //Calculate closing sequence score
                .map(Day10::calculateClosingSequenceScore)
                //Sort result
                .sorted()
                .collect(Collectors.toList());
        //Return median result
        return scores.get((scores.size() / 2));
    }

    /**
     * For a closing sequence of incomplete line, calculate associated score
     *
     * @param closingSequence closing sequence of incomplete line
     * @return closing sequence score
     */
    private static long calculateClosingSequenceScore(String closingSequence) {
        long score = 0;
        for(char c : closingSequence.toCharArray()) {
            score *= 5;
            score += closingCharScore.get(c);
        }
        return score;
    }

    /**
     * For a clean incomplete line, evaluate the closing sequence needed
     *
     * @param cleanIncomplete  clean incomplete line
     * @return closing sequence needed
     */
    private static String getClosingSequence(String cleanIncomplete) {
        StringBuilder closingSequence = new StringBuilder();
        for(int i = cleanIncomplete.length() - 1; i >=0 ; i--) {
            closingSequence.append(closingChar.get(cleanIncomplete.charAt(i)));
        }
        return closingSequence.toString();
    }

    /**
     * For a clean corrupted line, identify the score of first bad closing character
     *
     * @param cleanLine clean corrupted line
     * @return score associated to first bad closing character
     */
    private static long getFirstBadEnclosing(String cleanLine) {
        for(int i =0; i<cleanLine.length(); i++) {
            if(corruptedCharScore.get(cleanLine.charAt(i)) != null) {
                return corruptedCharScore.get(cleanLine.charAt(i));
            }
        }
        return 0L;
    }

    /**
     * For a corrupted or incomplete line, clean the sequence by removing correct chunk
     *
     * @param line corrupted or incomplete line
     * @return line without correct chunk (clean line)
     */
    private static String cleanLine(String line) {
        boolean fixSize = false;
        String newLine = line;
        while(!fixSize) {
            int initLineLength = newLine.length();
            newLine = newLine.replaceAll("\\(\\)","");
            newLine = newLine.replaceAll("\\[]","");
            newLine = newLine.replaceAll("<>","");
            newLine = newLine.replaceAll("\\{}","");
            fixSize = newLine.length() == initLineLength;
        }
        return newLine;
    }
}
