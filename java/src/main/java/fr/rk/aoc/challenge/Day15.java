package fr.rk.aoc.challenge;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.IntStream;

@Slf4j
public final class Day15 {

    /**
     * Retrieve shortest path from submarine using Dijkstra UCS implementation
     *
     * @param input graph input
     * @return Shortest path weight
     */
    public static long getShortestPathLength(List<String> input, boolean withLargerDimension) {
        return parseGraph(input, withLargerDimension);
    }

    /**
     * Create initial graph and perform uniform cost search
     *
     * @param input graph input as String
     * @param withLargerDimension if the grid size is multiplied by 5
     *
     * @return the shortest path total cost
     */
    private static long parseGraph(List<String> input, boolean withLargerDimension) {
        NodeUcs[][] ucsGraphArray = parseGraphAsNodeUcsArray(input, withLargerDimension);
        int[][] weightArray = parseGraphAsIntArray(input, withLargerDimension);
        for (int i = 0; i < ucsGraphArray.length; i++) {
            for (int j = 0; j < ucsGraphArray[i].length; j++) {
                NodeUcs ucs = ucsGraphArray[i][j];
                //Bottom
                ucs.adjacencies = new ArrayList<>();
                if (i < ucsGraphArray.length - 1) {
                    ucs.adjacencies.add(new Edge(ucsGraphArray[i + 1][j], weightArray[i + 1][j]));
                }
                //Right
                if (j < ucsGraphArray[i].length - 1) {
                    ucs.adjacencies.add(new Edge(ucsGraphArray[i][j + 1], weightArray[i][j + 1]));
                }
                //Left
                if (j > 0) {
                    ucs.adjacencies.add(new Edge(ucsGraphArray[i][j - 1], weightArray[i][j - 1]));
                }
                //Top
                if (i > 0) {
                    ucs.adjacencies.add(new Edge(ucsGraphArray[i - 1][j], weightArray[i - 1][j]));
                }
            }
        }
        UniformCostSearch(ucsGraphArray[0][0],ucsGraphArray[ucsGraphArray.length - 1][ucsGraphArray[0].length - 1]);
        List<NodeUcs> path = printPath(ucsGraphArray[ucsGraphArray.length - 1][ucsGraphArray[0].length - 1]);
        return path.stream()
                .filter(node -> !node.value.equals("0,0"))
                .map(node -> {
                    String[] positions = node.value.split(",");
                    return weightArray[Integer.parseInt(positions[1])][Integer.parseInt(positions[0])];
                })
                .mapToLong(Long::valueOf)
                .sum();
    }

    /**
     * Create an array containing NodeUCS of the graph
     * @param input graph input as String
     * @param withLargerDimension if the grid size is multiplied by 5
     *
     * @return Array of all nodes in the graph (With no adjacencies for the moment)
     */
    private static NodeUcs[][] parseGraphAsNodeUcsArray(List<String> input, boolean withLargerDimension) {
        NodeUcs[][] nodes = new NodeUcs
                [withLargerDimension ? input.size() * 5 : input.size()]
                [withLargerDimension ? input.get(0).length() * 5 : input.get(0).length()];
        IntStream.range(0, withLargerDimension ? input.size() * 5 : input.size()).forEach(i -> {
            IntStream.range(0, withLargerDimension ? input.get(0).length() * 5 : input.get(0).length()).forEach(j -> {
                nodes[i][j] = new NodeUcs(j + "," + i);
            });
        });
        return nodes;
    }

    /**
     * Create an array containing path weight of the graph
     * @param input graph input as String
     * @param withLargerDimension if the grid size is multiplied by 5
     *
     * @return Array of all node weight
     */
    private static int[][] parseGraphAsIntArray(List<String> input, boolean withLargerDimension) {
        int[][] nodes = new int[input.size()][input.get(0).length()];
        IntStream.range(0, input.size()).forEach(i -> {
            char[] lineChar = input.get(i).toCharArray();
            IntStream.range(0, lineChar.length).forEach(j -> {
                nodes[i][j] = lineChar[j] - '0';
            });
        });
        if (!withLargerDimension) {
            return nodes;
        } else {
            int[][] largerNodes = new int[input.size() * 5][input.get(0).length() * 5];
            IntStream.range(0, input.size() * 5).forEach(i -> {
                IntStream.range(0, input.get(0).length() * 5).forEach(j -> {
                    largerNodes[i][j] = (nodes[i % input.size()][j % input.get(0).length()] + j / input.get(0).length() + i / input.size());
                    if (largerNodes[i][j] > 9) {
                        largerNodes[i][j] = 1 + (largerNodes[i][j] % 10);
                    }
                });
            });
            return largerNodes;
        }
    }

    /**
     * Perform a Uniform Cost Search
     *
     * @param source Source Node
     * @param goal Target Node
     */
    public static void UniformCostSearch(NodeUcs source, NodeUcs goal) {
        source.pathCost = 0;
        //override compare method
        PriorityQueue<NodeUcs> queue = new PriorityQueue<NodeUcs>(20,
                (i, j) -> {
                    if (i.pathCost > j.pathCost) {
                        return 1;
                    } else if (i.pathCost < j.pathCost) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
        );
        queue.add(source);
        Set<NodeUcs> explored = new HashSet<>();
        boolean found = false;
        //while frontier is not empty
        do {
            NodeUcs current = queue.poll();
            explored.add(current);
            //end if path is found
            if (current.value.equals(goal.value)) {
                found = true;
            }
            for (Edge e : current.adjacencies) {
                NodeUcs child = e.target;
                double cost = e.cost;
                //add node to queue if node has not been explored
                if (!explored.contains(child) && !queue.contains(child)) {
                    child.pathCost = current.pathCost + cost;
                    child.parent = current;
                    queue.add(child);
                }
                //current path is shorter than previous path found
                else if ((queue.contains(child)) && (child.pathCost > (current.pathCost + cost))) {
                    child.parent = current;
                    child.pathCost = current.pathCost + cost;
                    queue.remove(child);
                    queue.add(child);
                }
            }
        } while (!queue.isEmpty() && (found == false));
    }

    /**
     * Create a List of Node which represents the shortest path
     *
     * @param target target Node
     *
     * @return List of Node which represents the shortest path
     */
    public static List<NodeUcs> printPath(NodeUcs target) {
        List<NodeUcs> path = new ArrayList<NodeUcs>();
        for (NodeUcs node = target; node != null; node = node.parent) {
            path.add(node);
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Class representing a Node in the graph
     */
    static class NodeUcs {
        public final String value;
        public double pathCost;
        public List<Edge> adjacencies;
        public NodeUcs parent;

        public NodeUcs(String val) {
            value = val;
        }

        public String toString() {
            return value;
        }

    }

    /**
     * Class representing an Edge between two nodes
     */
    static class Edge {
        public final double cost;
        public final NodeUcs target;

        public Edge(NodeUcs targetNode, double costVal) {
            cost = costVal;
            target = targetNode;
        }
    }
}
