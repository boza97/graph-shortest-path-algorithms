package org.bozidar.generator;

import org.bozidar.model.Graph;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GraphGenerator {

    private GraphGenerator() {
    }

    public static Graph generateDAG(
            int vertices,
            int edges,
            boolean weighted,
            boolean allowNegativeWeights,
            long seed
    ) {

        long maxEdges = (long) vertices * (vertices - 1) / 2;

        if (edges < vertices - 1) {
            throw new IllegalArgumentException(
                    "Edges must be >= vertices - 1 to keep graph connected");
        }

        if (edges > maxEdges) {
            throw new IllegalArgumentException("Too many edges for DAG");
        }

        Random random = new Random(seed);
        Graph graph = new Graph(vertices, true);

        for (int i = 0; i < vertices - 1; i += 1) {
            int weight = weighted
                    ? randomWeight(random, allowNegativeWeights)
                    : 1;

            graph.addEdge(i, i + 1, weight);
        }

        int created = vertices - 1;
        double density = (double) edges / maxEdges;

        if (density < 0.6) {

            Set<Long> usedEdges = new HashSet<>();

            for (int i = 0; i < vertices - 1; i += 1) {
                long key = ((long) i << 32) | (i + 1);
                usedEdges.add(key);
            }

            while (created < edges) {

                int u = random.nextInt(vertices);
                int v = random.nextInt(vertices);

                if (u >= v) {
                    continue;
                }

                long key = ((long) u << 32) | v;
                if (usedEdges.contains(key)) {
                    continue;
                }

                int weight = weighted
                        ? randomWeight(random, allowNegativeWeights)
                        : 1;

                graph.addEdge(u, v, weight);
                usedEdges.add(key);
                created++;
            }

            return graph;
        }

        for (int u = 0; u < vertices && created < edges; u += 1) {
            for (int v = u + 1; v < vertices && created < edges; v += 1) {

                if (v == u + 1) {
                    continue;
                }

                int weight = weighted
                        ? randomWeight(random, allowNegativeWeights)
                        : 1;

                graph.addEdge(u, v, weight);
                created += 1;
            }
        }

        return graph;
    }

    public static Graph generateDirectedGraph(
            int vertices,
            int edges,
            boolean weighted,
            boolean allowNegativeWeights,
            long seed
    ) {

        long maxEdges = (long) vertices * (vertices - 1);

        if (edges < vertices - 1) {
            throw new IllegalArgumentException("Edges must be >= V-1");
        }

        if (edges > maxEdges) {
            throw new IllegalArgumentException("Too many edges");
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
        double density = (double) edges / maxEdges;

        if (density < 0.6) {
            while (created < edges) {
                int u = random.nextInt(vertices);
                int v = random.nextInt(vertices);

                if (u == v) {
                    continue;
                }

                long key = ((long) u << 32) | v;
                if (usedEdges.contains(key)) {
                    continue;
                }

                int weight = weighted
                        ? randomWeight(random, allowNegativeWeights)
                        : 1;

                graph.addEdge(u, v, weight);

                usedEdges.add(key);
                created += 1;
            }

            return graph;
        }


        for (int u = 0; u < vertices && created < edges; u += 1) {
            for (int v = 0; v < vertices && created < edges; v += 1) {

                if (u == v) {
                    continue;
                }

                long key = ((long) u << 32) | v;
                if (usedEdges.contains(key)) {
                    continue;
                }

                int weight = weighted
                        ? randomWeight(random, allowNegativeWeights)
                        : 1;

                graph.addEdge(u, v, weight);

                usedEdges.add(key);
                created += 1;
            }
        }

        return graph;
    }

    public static Graph generateGraphNoNegativeCycles(
            int vertices,
            int edges,
            boolean weighted,
            boolean allowNegativeWeights,
            long seed
    ) {

        long maxEdges = (long) vertices * (vertices - 1);

        if (edges < vertices - 1) {
            throw new IllegalArgumentException("Edges must be >= V-1");
        }

        if (edges > maxEdges) {
            throw new IllegalArgumentException("Too many edges");
        }

        Random random = new Random(seed);
        Graph graph = new Graph(vertices, true);

        int[] h = new int[vertices];
        for (int i = 0; i < vertices; i += 1) {
            h[i] = random.nextInt(1000);
        }

        Set<Long> usedEdges = new HashSet<>();
        int created = 0;

        for (int i = 0; i < vertices - 1; i += 1) {

            int weight = computeWeight(random, weighted, allowNegativeWeights, h, i, i + 1);

            graph.addEdge(i, i + 1, weight);

            long key = ((long) i << 32) | (i + 1);
            usedEdges.add(key);

            created += 1;
        }

        double density = (double) edges / maxEdges;

        if (density < 0.6) {

            while (created < edges) {

                int u = random.nextInt(vertices);
                int v = random.nextInt(vertices);

                if (u == v) continue;

                long key = ((long) u << 32) | v;
                if (usedEdges.contains(key)) {
                    continue;
                }

                int weight = computeWeight(random, weighted, allowNegativeWeights, h, u, v);

                graph.addEdge(u, v, weight);

                usedEdges.add(key);
                created += 1;
            }

            return graph;
        }

        for (int u = 0; u < vertices && created < edges; u += 1) {
            for (int v = 0; v < vertices && created < edges; v += 1) {

                if (u == v) {
                    continue;
                }

                long key = ((long) u << 32) | v;
                if (usedEdges.contains(key)) {
                    continue;
                }

                int weight = computeWeight(random, weighted, allowNegativeWeights, h, u, v);

                graph.addEdge(u, v, weight);

                usedEdges.add(key);
                created += 1;
            }
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

    private static int computeWeight(
            Random random,
            boolean weighted,
            boolean allowNegativeWeights,
            int[] h,
            int u,
            int v
    ) {
        if (!weighted) {
            return 1;
        }

        if (!allowNegativeWeights) {
            return random.nextInt(10) + 1;
        }

        int offset = random.nextInt(10);
        return h[v] - h[u] + offset;
    }
}
