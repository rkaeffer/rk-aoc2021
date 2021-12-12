package fr.rk.aoc.challenge;

import java.util.*;
import java.util.stream.Collectors;

public final class Day12 {

    /**
     * Get number of possible path in cave graph
     *
     * @param input graph details
     * @param canVisitTwice if one single small cave can be visit twice
     * @return the number of possible path for our submarine
     */
    public static long getNumberOfPath(List<String> input, boolean canVisitTwice) {
        Graph g = parseInput(input);
        return g.exploreAllPaths("start", "end", canVisitTwice);
    }

    /**
     * Transform graph as String in {@link Graph}
     *
     * @param input graph as String
     * @return graph as Graph
     */
    public static Graph parseInput(List<String> input) {
        //First, determine number of nodes
        Graph graph = new Graph();
        input.forEach(s -> {
            String[] edge = s.split("-");
            graph.addEdge(edge[0], edge[1]);
        });
        return graph;
    }

    /**
     * SUbmarine cave graph representation
     */
    private static class Graph {

        /**
         * Final result
         */
        private long cnt;

        /**
         * Graph representation
         * Key is node
         * Value id list of linked node to this key node
         */
        Map<String, List<String>> adjList;

        public Graph() {
            adjList = new HashMap<>();
            cnt = 0;
        }

        /**
         * Add a link between node u & node v (In both way)
         * Create node if non existent
         *
         * @param u first node
         * @param v second node
         */
        public void addEdge(String u, String v) {
            // Add v to u's list.
            adjList.computeIfAbsent(u, k -> new ArrayList<>());
            adjList.computeIfAbsent(v, k -> new ArrayList<>());
            adjList.get(u).add(v);
            adjList.get(v).add(u);
        }

        /**
         * Explore all node and determines number of available path
         *
         * @param start start node
         * @param end end node
         * @param canVisitTwice if one single small cave can be visit twice
         * @return number of available path
         */
        public long exploreAllPaths(String start, String end, boolean canVisitTwice) {
            HashMap<String, Integer> nbVisited = new HashMap<>();
            HashMap<String, Boolean> isVisited = new HashMap<>();
            ArrayList<String> pathList = new ArrayList<>();
            adjList.forEach((k, v) -> {
                isVisited.put(k, Boolean.FALSE);
                nbVisited.put(k, 0);
            });
            // add source to path[]
            pathList.add(start);
            nbVisited.put(start, 1);
            // Call recursive utility
            exploreNodeAround(start, end, isVisited, pathList, nbVisited, canVisitTwice, 2);
            return cnt;
        }

        /**
         * Explore next nodes
         *
         * @param u new start node
         * @param d end node to reach
         * @param isVisited Map which contins already visited nodes
         * @param localPathList Current calculated path
         */
        private void exploreNodeAround(String u, String d,
                                       HashMap<String, Boolean> isVisited,
                                       List<String> localPathList, HashMap<String, Integer> nbVisited, boolean canVisitTwice, Integer limit) {

            //If match found, we have find a way
            if (u.equals(d)) {
                cnt++;
                return;
            }

            //Twice visit case
            //If we enter a node which has reach visit limit, we stop
            if(canVisitTwice) {
                if(!u.equals("start") && !isAllUpper(u)) {
                    if(nbVisited.get(u).equals(limit)) {
                        return;
                    }
                }
            }

            //Increment number of times we get in this node
            nbVisited.put(u, nbVisited.get(u) + 1);

            // Mark the current node
            if (!isAllUpper(u)) {
                if(canVisitTwice) {
                    if(u.equals("start")) {
                        isVisited.put(u, Boolean.TRUE);
                    } else if(nbVisited.get(u) >= limit) {
                        isVisited.put(u, Boolean.TRUE);
                        if(nbVisited.get(u) == 2 && limit == 2) {
                            limit = 1;
                        }
                    }
                } else {
                    isVisited.put(u, Boolean.TRUE);
                }
            }


            // Explore node around
            for (String i : adjList.get(u)) {
                if (!isVisited.get(i)) {
                    //Deep copy of all state to get unique result
                    ArrayList<String> newLocalPathList = (ArrayList<String>) localPathList.stream().map(String::new).collect(Collectors.toList());
                    newLocalPathList.add(i);
                    HashMap<String, Boolean> newVisited = new HashMap<>();
                    isVisited.forEach(newVisited::put);
                    HashMap<String, Integer> newNbVisited = new HashMap<>();
                    nbVisited.forEach(newNbVisited::put);
                    //Call this function recursively
                    exploreNodeAround(i, d, newVisited, newLocalPathList, newNbVisited, canVisitTwice, limit);
                }
            }
        }
    }

    /**
     * Check if a string is only compose of upper case character
     *
     * @param s String to check
     * @return if a string is only compose of upper case character
     */
    private static boolean isAllUpper(String s) {
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c) && Character.isLowerCase(c)) {
                return false;
            }
        }
        return true;
    }
}
