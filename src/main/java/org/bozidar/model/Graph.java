package org.bozidar.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph {
    private final int vertices;
    private final boolean directed;
    private final List<List<Edge>> adjList;

    public Graph(int vertices, boolean directed) {
        if (vertices <= 0) throw new IllegalArgumentException("vertices must be > 0");
        this.vertices = vertices;
        this.directed = directed;

        this.adjList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i += 1) {
            adjList.add(new ArrayList<>());
        }
    }

    public int getVertices() {
        return vertices;
    }

    public boolean isDirected() {
        return directed;
    }

    public void addEdge(int from, int to, int weight) {
        validateVertex(from);
        validateVertex(to);
        adjList.get(from).add(new Edge(to, weight));
        if (!directed) {
            adjList.get(to).add(new Edge(from, weight));
        }
    }

    public List<Edge> neighbors(int v) {
        validateVertex(v);
        return Collections.unmodifiableList(adjList.get(v));
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= vertices) {
            throw new IllegalArgumentException("Vertex out of range: " + v);
        }
    }
}
