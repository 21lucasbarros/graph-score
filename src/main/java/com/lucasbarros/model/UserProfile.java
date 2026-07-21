package com.lucasbarros.model;

import com.lucasbarros.graph.ConnectionType;

import java.util.*;

public class UserProfile {

    private String name;
    private List<Movie> likedMovies = new ArrayList<>();
    private Set<String> favoritedDirectors = new HashSet<>();
    private Set<String> favoriteActors = new HashSet<>();
    private Map<ConnectionType, Double> connectionTypeMultipliers = new HashMap<>();

    private static final double FAVORITE_BOOST = 1.6;

    public UserProfile(String name) {
        this.name = name;

        for(ConnectionType type : ConnectionType.values()) {
            connectionTypeMultipliers.put(type, 1.0);
        }
    }

    public String getName() {
        return name;
    }

    public void addLikedMovie(Movie movie) {
        if(!likedMovies.contains(movie)) {
            likedMovies.add(movie);
        }
    }

    public void addFavoriteDirector(String director) {
        favoritedDirectors.add(director.toLowerCase());
    }

    public void setConnectionTypeMultiplier(ConnectionType type, double multiplier) {
        connectionTypeMultipliers.put(type, multiplier);
    }

    public List<Movie> getLikedMovies() {
        return likedMovies;
    }

    public boolean isFavoriteDirector(String director) {
        if(director == null) return false;

        return favoritedDirectors.contains(director.toLowerCase());
    }

    public boolean isFavoriteActor(String actor) {
        if(actor == null) return false;

        return favoriteActors.contains(actor.toLowerCase());
    }

    public double getConnectionTypeMultiplier(ConnectionType type) {
        if(connectionTypeMultipliers.containsKey(type)) {
            return connectionTypeMultipliers.get(type);
        }
        return 1.0;
    }

    public double getFavoriteBoost() {
        return FAVORITE_BOOST;
    }
}
