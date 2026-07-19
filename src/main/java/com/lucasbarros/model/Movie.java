package com.lucasbarros.model;

import java.util.List;
import java.util.Set;

public class Movie {
    private String id;
    private String title;
    private String director;
    private Set<String> genres;
    private List<CastMember> cast;
    private String franchise; // pode ficar null se o filme não faz parte de nenhuma saga
    private int releaseYear;
    private double rating; // nota do filme (não usada no nosso cálculo, só informativa)

    public Movie(String id, String title, String director, Set<String> genres, List<CastMember> cast, String franchise, double rating, int releaseYear) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.genres = genres;
        this.cast = cast;
        this.franchise = franchise;
        this.rating = rating;
        this.releaseYear = releaseYear;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public List<CastMember> getCast() {
        return cast;
    }

    public String getFranchise() {
        return franchise;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getRating() {
        return rating;
    }

    public int getEra() {
        return (releaseYear / 10) * 10;
    }
}
