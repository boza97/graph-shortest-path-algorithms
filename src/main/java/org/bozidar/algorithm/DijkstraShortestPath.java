package org.bozidar.algorithm;

import org.bozidar.model.Edge;
import org.bozidar.model.Graph;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class DijkstraShortestPath implements ShortestPathAlgorithm {

    @Override
    public int[] compute(Graph graph, int source) {
        int V = graph.getVertices();
        int[] result = new int[V];
        Arrays.fill(result, Integer.MAX_VALUE);
        result[source] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{source, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int d = current[1];

            if (d > result[node]) {
                continue;
            }

            for (Edge edge : graph.neighbors(node)) {
                int temp = result[node] + edge.getWeight();
                if (temp < result[edge.getTo()]) {
                    result[edge.getTo()] = temp;
                    pq.offer(new int[]{edge.getTo(), temp});
                }
            }
        }

        return result;
    }
}
