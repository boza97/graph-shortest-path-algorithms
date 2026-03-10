package org.bozidar.algorithm;

import org.bozidar.model.Edge;
import org.bozidar.model.Graph;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class BFSShortestPath implements ShortestPathAlgorithm {

    @Override
    public int[] compute(Graph graph, int source) {
        int V = graph.getVertices();
        if (source < 0 || source >= V) {
            throw new IllegalArgumentException("Source out of range: " + source);
        }

        int[] result = new int[V];
        Arrays.fill(result, -1);

        Queue<Integer> q = new ArrayDeque<>();
        result[source] = 0;
        q.add(source);

        while (!q.isEmpty()) {
            int u = q.poll();
            for (Edge e : graph.neighbors(u)) {
                int v = e.getTo();
                if (result[v] == -1) {
                    result[v] = result[u] + 1;
                    q.add(v);
                }
            }
        }

        return result;
    }
}
