package org.bozidar.generator;

import org.bozidar.model.Graph;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GraphGenerator {

    public static Graph generateDAG(
            int vertices,
            int edges,
            boolean weighted,
            boolean allowNegativeWeights,
            long seed
    ) {

        if (edges < vertices - 1) {
            throw new IllegalArgumentException(
                    "Edges must be >= vertices - 1 to keep graph connected");
        }

        if (edges > (long) vertices * (vertices - 1) / 2) {
            throw new IllegalArgumentException(
                    "Too many edges for DAG");
        }

        Random random = new Random(seed);
        Graph graph = new Graph(vertices, true);

        Set<Long> usedEdges = new HashSet<>();

        int created = 0;

        for (int i = 0; i < vertices - 1; i += 1) {

            int weight = weighted
                    ? randomWeight(random, allowNegativeWeights)
                    : 1;

            graph.addEdge(i, i + 1, weight);

            long key = ((long) i << 32) | (i + 1);
            usedEdges.add(key);

            created += 1;
        }

        while (created < edges) {

            int from = random.nextInt(vertices);
            int to = random.nextInt(vertices);

            if (from >= to) {
                continue;
            }

            long key = ((long) from << 32) | to;

            if (usedEdges.contains(key)) {
                continue;
            }

            int weight = weighted
                    ? randomWeight(random, allowNegativeWeights)
                    : 1;

            graph.addEdge(from, to, weight);

            usedEdges.add(key);
            created += 1;
        }

        return graph;
    }

    private static int randomWeight(Random random, boolean allowNegative) {

        if (!allowNegative) {
            return random.nextInt(10) + 1;
        }

        int w = random.nextInt(21) - 10;

        if (w == 0) {
            w = 1;
        }

        return w;
    }
}
