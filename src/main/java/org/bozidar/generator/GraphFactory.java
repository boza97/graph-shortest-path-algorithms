package org.bozidar.generator;

import org.bozidar.algorithm.model.AlgorithmType;
import org.bozidar.model.Graph;

public class GraphFactory {

    public static Graph createGraph(
            AlgorithmType algorithmType,
            int vertices,
            int edges,
            long seed
    ) {

        if (algorithmType == AlgorithmType.DAG) {
            return GraphGenerator.generateDAG(
                    vertices,
                    edges,
                    algorithmType.requiresWeightedGraph(),
                    algorithmType.shouldAllowNegativeWeights(),
                    seed
            );
        } else if (algorithmType == AlgorithmType.BELLMAN_FORD) {
            return GraphGenerator.generateGraphNoNegativeCycles(vertices, edges, algorithmType.requiresWeightedGraph(),
                                                                algorithmType.shouldAllowNegativeWeights(), seed);
        }

        return GraphGenerator.generateDirectedGraph(
                vertices,
                edges,
                algorithmType.requiresWeightedGraph(),
                algorithmType.shouldAllowNegativeWeights(),
                seed
        );
    }
}
