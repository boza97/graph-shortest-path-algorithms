package org.bozidar.model;

public final class Edge {
    private final int to;
    private final int weight;

    public Edge(int to, int weight) {
        if (to < 0) {
            throw new IllegalArgumentException("to must be >= 0");
        }

        this.to = to;
        this.weight = weight;
    }

    public int getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }
}
