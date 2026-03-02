package org.bozidar.generator;

import org.bozidar.model.Graph;

import java.util.Random;

public class GraphGenerator {

    public static Graph randomDirectedUnweighted(int vertices, int edges, long seed) {
        if (edges < 0) {
            throw new IllegalArgumentException("edges must be >= 0");
        }

        Graph g = new Graph(vertices, true);
        Random rnd = new Random(seed);

        for (int i = 0; i < edges; i += 1) {
            int from = rnd.nextInt(vertices);
            int to = rnd.nextInt(vertices);
            if (from == to) {
                i -= 1;
                continue;
            }
            g.addEdge(from, to, 1);
        }
        return g;
    }
}
