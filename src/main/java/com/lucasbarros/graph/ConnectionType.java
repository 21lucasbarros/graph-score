package com.lucasbarros.graph;

public enum ConnectionType {
    // aqui a gente pode mexer depois nos pesos
    FRANCHISE(1.2, "mesma franquia/saga"),
    DIRECTOR(1.0, "mesmo diretor"),
    ACTOR(0.8, "elenco em comum"),
    GENRE(0.5, "mesmo gênero"),
    ERA(0.3, "mesma época de lançamento");

    private final double baseWeight;
    private final String description;

    ConnectionType(double baseWeight, String description) {
        this.baseWeight = baseWeight;
        this.description = description;
    }

    public double getBaseWeight() {
        return baseWeight;
    }

    public String getDescription() {
        return description;
    }
}
