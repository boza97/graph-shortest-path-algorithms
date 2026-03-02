package org.bozidar.benchmark;

import org.bozidar.algorithm.BFSShortestPath;
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

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 2)
@Measurement(iterations = 3)
@Fork(1)
public class BFSBenchmark {

    @Param({"10000", "20000", "40000"})
    public int vertices;

    public int edges;

    private Graph graph;
    private BFSShortestPath bfs;

    @Setup(Level.Trial)
    public void setup() {
        edges = vertices * 3;
        graph = GraphGenerator.randomDirectedUnweighted(vertices, edges, 12345L);
        bfs = new BFSShortestPath();
    }

    @Benchmark
    public int[] bfsShortestPath() {
        return bfs.compute(graph, 0);
    }
}