package org.bozidar.algorithm;

import org.bozidar.model.Edge;
import org.bozidar.model.Graph;

import java.util.Arrays;

public class BellmanFordShortestPath implements ShortestPathAlgorithm {

    @Override public int[] compute(Graph graph, int source) {

        int V = graph.getVertices();
        int[] result = new int[V];

        Arrays.fill(result, Integer.MAX_VALUE);
        result[source] = 0;

        for (int i = 0; i < V - 1; i += 1) {
            boolean updaated = false;

            for (int u = 0; u < V; u += 1) {
                if (result[u] == Integer.MAX_VALUE) {
                    continue;
                }

                for (Edge edge : graph.neighbors(u)) {
                    int v = edge.getTo();
                    int w = edge.getWeight();

                    if (result[u] + w < result[v]) {
                        result[v] = result[u] + w;
                        updaated = true;
                    }
                }
            }

            if (!updaated) {
                break;
            }
        }

        return result;
    }
}
