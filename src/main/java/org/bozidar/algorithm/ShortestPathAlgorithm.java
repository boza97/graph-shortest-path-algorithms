package org.bozidar.algorithm;

import org.bozidar.model.Graph;

public interface ShortestPathAlgorithm {
    int[] compute(Graph graph, int source);
}
