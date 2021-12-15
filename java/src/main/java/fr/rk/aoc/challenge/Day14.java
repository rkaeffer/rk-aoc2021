package fr.rk.aoc.challenge;


import java.math.BigInteger;
import java.util.*;

public final class Day14 {

    /**
     * Calculate the number of maximum element minus the number of minimum element after N step
     *
     * @param input
     * @param nbStep
     * @return
     */
    public static BigInteger getMostCommonElementQuantityMinusMostCommonElement(List<String> input, int nbStep) {
        //Get initial sequence
        String sequence = getElementSequence(input);
        //Get mapping of element
        Map<String, String> elementReference = getElementReference(input);
        //Create a new element mapping for each couple of element. For example, if NN -> B, we map NN to -> [NB, BN]
        Map<String, List<String>> elementMapping = new HashMap<>();
        elementReference.forEach((key, value) -> {
            List<String> list = new ArrayList<>();
            list.add(key.charAt(0) + value);
            list.add(value + key.charAt(1));
            elementMapping.put(key, list);
        });
        //Initialize a map which will contains count in our chain of all couple of element
        Map<String, BigInteger> cntMap = new HashMap<>();
        //Initiate cntMap with initial sequence value
        for(int i=0; i< sequence.length() - 1; i++) {
            cntMap.putIfAbsent(sequence.substring(i, i + 2), BigInteger.ZERO);
            cntMap.put(sequence.substring(i,i+2), cntMap.get(sequence.substring(i,i+2)).add(BigInteger.ONE));
        }
        //Calculate sequence
        for(int i=0; i<nbStep; i++) {
            //Create a new empty cntMap which will contains new element count after a step
            Map<String, BigInteger> newCntMap = new HashMap<>();
            //For each current sequence entry, map to corresponding couple element and fill the new count map
            for (Map.Entry<String, BigInteger> entry : cntMap.entrySet()) {
                newCntMap.putIfAbsent(elementMapping.get(entry.getKey()).get(0), BigInteger.ZERO);
                newCntMap.putIfAbsent(elementMapping.get(entry.getKey()).get(1), BigInteger.ZERO);
                newCntMap.put(elementMapping.get(entry.getKey()).get(0), newCntMap.get(elementMapping.get(entry.getKey()).get(0)).add(entry.getValue()));
                newCntMap.put(elementMapping.get(entry.getKey()).get(1), newCntMap.get(elementMapping.get(entry.getKey()).get(1)).add(entry.getValue()));
            }
            cntMap=newCntMap;
        }
        //Mapping count for each element
        Map<Character, BigInteger> cntElement = new HashMap<>();
        cntMap.forEach((k,v) -> {
            cntElement.putIfAbsent(k.charAt(0), BigInteger.ZERO);
            cntElement.putIfAbsent(k.charAt(1), BigInteger.ZERO);
            cntElement.put(k.charAt(0), cntElement.get(k.charAt(0)).add(v));
            cntElement.put(k.charAt(1), cntElement.get(k.charAt(1)).add(v));
        });
        //Divide all count by 2 (Round up) because count twice with our mapping
        cntElement.forEach((k,v) -> {
            if(v.mod(BigInteger.TWO).equals(BigInteger.ONE)) {
                cntElement.put(k, v.add(BigInteger.ONE));
            }
            cntElement.put(k, cntElement.get(k).divide(BigInteger.TWO));
        });
        //Retrieve count of char that occurs the maximum in the chain
        Optional<BigInteger> max = cntElement.values().stream().max(BigInteger::compareTo);
        //Retrieve count of char that occurs the minimum in the chain
        Optional<BigInteger> min = cntElement.values().stream().min(BigInteger::compareTo);
        //Return the result
        return max.get().subtract(min.get());
    }

    /**
     * Retrieve initial sequence between input
     *
     * @param input input
     * @return initial sequence
     */
    public static String getElementSequence(List<String> input) {
        return input.get(0);
    }

    /**
     * Retrieve element mapping reference
     *
     * @param input input
     * @return element mapping reference
     */
    public static Map<String, String> getElementReference(List<String> input) {
        HashMap<String, String> elementReference = new HashMap<>();
        input.stream().filter(line -> line.length() == 7).forEach(line -> {
            String[] ref = line.split("->");
            elementReference.put(ref[0].trim(), ref[1].trim());
        });
        return elementReference;
    }
}
