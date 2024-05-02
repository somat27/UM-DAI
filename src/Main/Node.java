/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class Node<T> implements Comparable<Node<T>> {

    private final T name;
    private Integer distance = Integer.MAX_VALUE;
    private List<Node<T>> shortestPath = new LinkedList<>();
    private final Map<Node<T>, Map<String, Integer>> adjacentNodes = new HashMap<>(); // Mapeia o nó adjacente para as linhas e distâncias

    public void addAdjacentNode(Node<T> node, String line, int distance) {
        adjacentNodes.computeIfAbsent(node, k -> new HashMap<>()).put(line, distance);
    }
    
    public Map<Node<T>, Map<String, Integer>> getAdjacentNodes() {
        return adjacentNodes;
    }

    @Override
    public int compareTo(Node node) {
        return Integer.compare(this.distance, node.getDistance());
    }

    public T getData() {
        return name;
    }
    
    public int getDistance() {
        return distance;
    }
}
