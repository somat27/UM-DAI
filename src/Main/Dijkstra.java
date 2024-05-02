/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import java.util.*;

/**
 *
 * @author tomas
 * @param <T>
 */
public class Dijkstra<T> {    
    public List<Node<T>> calculateShortestPath(Node<T> source, Node<T> destination) {
        Map<Node<T>, Node<T>> predecessors = new HashMap<>();
        Map<Node<T>, Integer> distances = new HashMap<>();
        Set<Node<T>> settledNodes = new HashSet<>();
        PriorityQueue<Node<T>> unsettledNodes = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        distances.put(source, 0);
        unsettledNodes.add(source);

        while (!unsettledNodes.isEmpty()) {
            Node<T> currentNode = unsettledNodes.poll();
            if (currentNode.equals(destination)) {
                return getPath(predecessors, destination);
            }
            settledNodes.add(currentNode);
            relaxNeighbors(currentNode, unsettledNodes, settledNodes, distances, predecessors);
        }
        return Collections.emptyList();
    }

    private void relaxNeighbors(Node<T> node, PriorityQueue<Node<T>> unsettledNodes, Set<Node<T>> settledNodes, Map<Node<T>, Integer> distances, Map<Node<T>, Node<T>> predecessors) {
        for (Map.Entry<Node<T>, Map<String, Integer>> entry : node.getAdjacentNodes().entrySet()) {
            Node<T> adjacentNode = entry.getKey();
            if (settledNodes.contains(adjacentNode)) {
                continue;
            }
            Map<String, Integer> linesAndDistance = entry.getValue();
            boolean hasCommonLine = false;
            for (Map.Entry<String, Integer> lineAndDistance : linesAndDistance.entrySet()) {
                String line = lineAndDistance.getKey();
                if (node.getAdjacentNodes().containsKey(adjacentNode) && node.getAdjacentNodes().get(adjacentNode).containsKey(line)) {
                    hasCommonLine = true;
                    break;
                }
            }
            if (!hasCommonLine) {
                continue; 
            }
            int edgeWeight = linesAndDistance.values().stream().findFirst().orElseThrow();
            int newDistance = distances.get(node) + edgeWeight;
            if (!distances.containsKey(adjacentNode) || newDistance < distances.get(adjacentNode)) {
                distances.put(adjacentNode, newDistance);
                predecessors.put(adjacentNode, node);
                unsettledNodes.add(adjacentNode);
            }
        }
    }
    
    private List<Node<T>> getPath(Map<Node<T>, Node<T>> predecessors, Node<T> destination) {
        List<Node<T>> path = new ArrayList<>();
        for (Node<T> node = destination; node != null; node = predecessors.get(node)) {
            path.add(node);
        }
        Collections.reverse(path);
        return path;
    }

    public void printPaths(List<Node<T>> path) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            Node<T> node = path.get(i);
            sb.append(node.getName());
            if (i < path.size() - 1) {
                Node<T> nextNode = path.get(i + 1);
                List<String> commonLines = getCommonLines(node, nextNode);
                String selectedLine = selectLine(commonLines);
                int distance = node.getAdjacentNodes().get(nextNode).get(selectedLine);
                sb.append(" -> (").append(selectedLine.trim()).append(", ").append(distance).append("km) -> ");
            }
        }
        System.out.println(sb.toString());
    }

    
    private List<String> getCommonLines(Node<T> node1, Node<T> node2) {
        List<String> commonLines = new ArrayList<>();
        for (Map.Entry<Node<T>, Map<String, Integer>> entry : node1.getAdjacentNodes().entrySet()) {
            Node<T> adjacentNode = entry.getKey();
            if (adjacentNode.equals(node2)) {
                commonLines.addAll(entry.getValue().keySet());
                break;
            }
        }
        return commonLines;
    }


    private String selectLine(List<String> lines) {
        if (!lines.isEmpty()) {
            return lines.get(0); // Seleciona a primeira linha comum
        } else {
            return "Sem linha comum";
        }
    }
}
