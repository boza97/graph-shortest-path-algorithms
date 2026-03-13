package org.bozidar.algorithm;

import org.bozidar.model.Edge;
import org.bozidar.model.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class DAGShortestPath implements ShortestPathAlgorithm {

    @Override
    public int[] compute(Graph graph, int source) {
        int V = graph.getVertices();
        int[] dist = new int[V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        List<Integer> topoOrder = topologicalSort(graph);

        for (int u : topoOrder) {
            if (dist[u] == Integer.MAX_VALUE) {
                continue;
            }

            for (Edge edge : graph.neighbors(u)) {
                int v = edge.getTo();
                int w = edge.getWeight();

                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                }
            }
        }

        return dist;
    }

    private List<Integer> topologicalSort(Graph graph) {
        int V = graph.getVertices();
        int[] indegree = new int[V];

        for (int u = 0; u < V; u += 1) {
            for (Edge e : graph.neighbors(u)) {
                indegree[e.getTo()] += 1;
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < V; i += 1) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        List<Integer> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            order.add(u);

            for (Edge e : graph.neighbors(u)) {
                int v = e.getTo();
                indegree[v] -= 1;

                if (indegree[v] == 0) {
                    queue.add(v);
                }
            }
        }

        return order;
    }
}
