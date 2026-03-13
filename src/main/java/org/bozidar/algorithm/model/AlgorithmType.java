package org.bozidar.algorithm.model;

public enum AlgorithmType {
    BFS(false, false),
    DIJKSTRA(true, false),
    BELLMAN_FORD(true, true),
    DAG(true, true);

    private final boolean weighted;
    private final boolean allowNegativeWeights;

    AlgorithmType(boolean weighted, boolean allowNegativeWeights) {
        this.weighted = weighted;
        this.allowNegativeWeights = allowNegativeWeights;
    }

    public boolean requiresWeightedGraph() {
        return this.weighted;
    }

    public boolean shouldAllowNegativeWeights() {
        return this.allowNegativeWeights;
    }
}
