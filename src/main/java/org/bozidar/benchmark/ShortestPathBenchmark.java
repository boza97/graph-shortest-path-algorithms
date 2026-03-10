package org.bozidar.benchmark;

import org.bozidar.algorithm.ShortestPathAlgorithm;
import org.bozidar.algorithm.factory.ShortestPathFactory;
import org.bozidar.algorithm.model.AlgorithmType;
import org.bozidar.generator.GraphGenerator;
import org.bozidar.model.Graph;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@Fork(2)
public class ShortestPathBenchmark {

    @Param({"BFS", "DIJKSTRA", "BELLMAN_FORD"})
    public AlgorithmType algorithmType;

    @Param({"10000", "20000", "40000"})
    public int vertices;

    private Graph graph;
    private ShortestPathAlgorithm algorithm;

    @Setup(Level.Trial)
    public void setup() {
        int edges = vertices * 3;
        graph = GraphGenerator.generateDAG(
                vertices,
                edges,
                algorithmType.requiresWeightedGraph(),
                algorithmType.shouldAllowNegativeWeights(),
                12345L
        );
        algorithm = ShortestPathFactory.create(algorithmType);
    }

    @Benchmark
    public int[] shortestPath() {
        return algorithm.compute(graph, 0);
    }
}
