/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import java.text.DecimalFormat;
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
    
    public Object[] printPaths(List<Node<T>> path) {
        String linhaAnterior = null;
        int distance = 0;
        int totalTrocas = 0;
        StringBuilder sb = new StringBuilder();
        String commonLinesAntiga = null;
        List<String> commonLinesAtual = null;
        List<String> commonLinesProxima = null;
        for (int i = 0; i < path.size(); i++) {
            Node<T> node = path.get(i);
            if (i < path.size() - 1) {
                Node<T> nextNode = path.get(i + 1);
                commonLinesAtual = getCommonLines(node, nextNode);
                Node<T> nextnextNode = null;
                if (i + 2 < path.size()){
                    if (path.get(i + 2)!=null){
                        nextnextNode = path.get(i + 2);
                        commonLinesProxima = getCommonLines(nextNode, nextnextNode);
                    }
                }
                String selectedLine = selectLine(commonLinesAtual, commonLinesAntiga, commonLinesProxima);
                commonLinesAntiga = selectedLine;
                distance = distance + node.getAdjacentNodes().get(nextNode).get(selectedLine);     
                if(linhaAnterior == null || !linhaAnterior.trim().equals(selectedLine.trim())){
                    linhaAnterior = selectedLine;
                    sb.append(node.getName()).append(" -> (").append(selectedLine.trim()).append(") -> ");
                    totalTrocas++;
                }
            }
        }
        sb.append(path.get(path.size()-1).getName());
        
        Object[] valores = {
            sb.toString(),
            distance,
            totalTrocas,
            path.size()
        };
        return valores;
    }

   
    private List<String> getCommonLines(Node<T> node1, Node<T> node2) {
        List<String> commonLines = new ArrayList<>();
        if(node2!=null){
            for (Map.Entry<Node<T>, Map<String, Integer>> entry : node1.getAdjacentNodes().entrySet()) {
                Node<T> adjacentNode = entry.getKey();
                if (adjacentNode.equals(node2)) {
                    commonLines.addAll(entry.getValue().keySet());
                    break;
                }
            }
        }
        return commonLines;
    }


    private String selectLine(List<String> commonLinesAtual, String antigaLinha, List<String> commonLinesProxima) {
        if (!commonLinesAtual.isEmpty()) {
            if (antigaLinha == null){
                for (String linhaAtual : commonLinesAtual) {
                    if(commonLinesProxima!=null){
                        for (String proximaLinha : commonLinesProxima) {
                            if(linhaAtual.equals(proximaLinha)){
                                return linhaAtual;
                            }
                        }
                    }
                }
                return commonLinesAtual.get(0);
            }else{
                for (String linhaAtual : commonLinesAtual) {
                    if(linhaAtual.equals(antigaLinha)){
                        return linhaAtual;
                    }
                }
                for (String linhaAtual : commonLinesAtual) {
                    for (String proximaLinha : commonLinesProxima) {
                        if(linhaAtual.equals(proximaLinha)){
                            return linhaAtual;
                        }
                    }
                }
                return commonLinesAtual.get(0);
            }
        } else {
            return "Sem linha comum";
        }
    }
}
