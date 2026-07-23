package com.lucasbarros.recommendation;

import com.lucasbarros.model.Movie;

import java.util.List;

public class RecommendationResult {
    private Movie movie;
    private double score;
    private List<String> reasons;

    public RecommendationResult(Movie movie, double score, List<String> reasons) {
        this.movie = movie;
        this.score = score;
        this.reasons = reasons;
    }

    public Movie getMovie() {
        return movie;
    }

    public double getScore() {
        return score;
    }

    public List<String> getReasons() {
        return reasons;
    }
}
