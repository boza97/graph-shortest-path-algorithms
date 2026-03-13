package org.bozidar.algorithm.factory;

import org.bozidar.algorithm.BFSShortestPath;
import org.bozidar.algorithm.BellmanFordShortestPath;
import org.bozidar.algorithm.DAGShortestPath;
import org.bozidar.algorithm.DijkstraShortestPath;
import org.bozidar.algorithm.ShortestPathAlgorithm;
import org.bozidar.algorithm.model.AlgorithmType;

public class ShortestPathFactory {

    public static ShortestPathAlgorithm create(AlgorithmType type) {
        return switch (type) {
            case BFS -> new BFSShortestPath();
            case DIJKSTRA -> new DijkstraShortestPath();
            case BELLMAN_FORD -> new BellmanFordShortestPath();
            case DAG -> new DAGShortestPath();
        };
    }
}
