package com.lucasbarros;

import com.lucasbarros.graph.ConnectionType;
import com.lucasbarros.graph.MovieGraph;
import com.lucasbarros.model.CastMember;
import com.lucasbarros.model.Movie;
import com.lucasbarros.model.RoleType;
import com.lucasbarros.model.UserProfile;
import com.lucasbarros.recommendation.RecommendationEngine;
import com.lucasbarros.recommendation.RecommendationResult;
import com.lucasbarros.visual.GraphPanel;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        List<Movie> filmes = montadorDeFilmesDeTeste();
        MovieGraph graph = new MovieGraph(filmes);

        UserProfile user = buildUserProfile(filmes);

        RecommendationEngine engine = new RecommendationEngine(graph);
        List<RecommendationResult> recommendations = engine.recommend(user, 5);

        printReport(user, recommendations);

        SwingUtilities.invokeLater(() -> abrirJanela(graph));
    }

    private static void abrirJanela(MovieGraph graph) {
        JFrame frame = new JFrame("Erik, te amoooooo <3");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GraphPanel painel = new GraphPanel(graph);

        frame.add(new JScrollPane(painel), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static List<Movie> montadorDeFilmesDeTeste() {
        List<Movie> filmes = new ArrayList<>();

        filmes.add(new Movie("m1", "Inception", "Christopher Nolan",
                generos("Ficção Científica", "Ação"),
                elenco(
                        new CastMember("Leonardo DiCaprio", RoleType.PROTAGONIST),
                        new CastMember("Tom Hardy", RoleType.SUPPORTING),
                        new CastMember("Michael Caine", RoleType.CAMEO)
                ), null, 8.8, 2010));

        filmes.add(new Movie("m2", "The Dark Knight", "Christopher Nolan",
                generos("Ação", "Drama"),
                elenco(
                        new CastMember("Christian Bale", RoleType.PROTAGONIST),
                        new CastMember("Heath Ledger", RoleType.PROTAGONIST),
                        new CastMember("Michael Caine", RoleType.SUPPORTING)
                ), "The Dark Knight Trilogy", 9.0, 2008));

        filmes.add(new Movie("m3", "Interstellar", "Christopher Nolan",
                generos("Ficção Científica", "Drama"),
                elenco(
                        new CastMember("Matthew McConaughey", RoleType.PROTAGONIST),
                        new CastMember("Anne Hathaway", RoleType.SUPPORTING),
                        new CastMember("Michael Caine", RoleType.SUPPORTING)
                ), null, 8.6, 2014));

        filmes.add(new Movie("m4", "Oppenheimer", "Christopher Nolan",
                generos("Drama", "História"),
                elenco(
                        new CastMember("Cillian Murphy", RoleType.PROTAGONIST),
                        new CastMember("Robert Downey Jr.", RoleType.SUPPORTING)
                ), null, 9.0, 2023));

        filmes.add(new Movie("m5", "Dune", "Denis Villeneuve",
                generos("Ficção Científica", "Aventura"),
                elenco(
                        new CastMember("Timothée Chalamet", RoleType.PROTAGONIST),
                        new CastMember("Zendaya", RoleType.SUPPORTING),
                        new CastMember("Josh Brolin", RoleType.SUPPORTING)
                ), "Dune", 8.0, 2021));

        filmes.add(new Movie("m6", "Dune: Part Two", "Denis Villeneuve",
                generos("Ficção Científica", "Aventura"),
                elenco(
                        new CastMember("Timothée Chalamet", RoleType.PROTAGONIST),
                        new CastMember("Zendaya", RoleType.PROTAGONIST),
                        new CastMember("Josh Brolin", RoleType.SUPPORTING)
                ), "Dune", 8.7, 2024));

        filmes.add(new Movie("m7", "Blade Runner 2049", "Denis Villeneuve",
                generos("Ficção Científica", "Drama"),
                elenco(
                        new CastMember("Ryan Gosling", RoleType.PROTAGONIST),
                        new CastMember("Harrison Ford", RoleType.SUPPORTING)
                ), null, 8.7, 2017));

        filmes.add(new Movie("m8", "Alien", "Ridley Scott",
                generos("Ficção Científica", "Terror"),
                elenco(
                        new CastMember("Sigourney Weaver", RoleType.PROTAGONIST),
                        new CastMember("Tom Skerritt", RoleType.SUPPORTING)
                ), "Alien", 8.5, 1979));

        filmes.add(new Movie("m9", "Tenet", "Christopher Nolan",
                generos("Ficção Científica", "Ação"),
                elenco(
                        new CastMember("John David Washington", RoleType.PROTAGONIST),
                        new CastMember("Robert Pattinson", RoleType.SUPPORTING)
                ), null, 7.3, 2020));

        filmes.add(new Movie("m10", "Whiplash", "Damien Chazelle",
                generos("Drama", "Música"),
                elenco(
                        new CastMember("Miles Teller", RoleType.PROTAGONIST),
                        new CastMember("J.K. Simmons", RoleType.SUPPORTING)
                ), null, 8.5, 2014));

        filmes.add(new Movie("m11", "A Rede Social", "David Fincher",
                generos("Drama", "Biografia"),
                elenco(
                        new CastMember("Jesse Eisenberg", RoleType.PROTAGONIST),
                        new CastMember("Andrew Garfield", RoleType.SUPPORTING)
                ), null, 7.8, 2010));

        filmes.add(new Movie("m12", "La La Land", "Damien Chazelle",
                generos("Drama", "Romance"),
                elenco(
                        new CastMember("Ryan Gosling", RoleType.PROTAGONIST),
                        new CastMember("Emma Stone", RoleType.PROTAGONIST)
                ), null, 8.0, 2016));

        filmes.add(new Movie("m13", "Se7en", "David Fincher",
                generos("Crime", "Suspense"),
                elenco(
                        new CastMember("Brad Pitt", RoleType.PROTAGONIST),
                        new CastMember("Morgan Freeman", RoleType.PROTAGONIST),
                        new CastMember("Kevin Spacey", RoleType.SUPPORTING)
                ), null, 8.6, 1995));

        filmes.add(new Movie("m14", "Zodiac", "David Fincher",
                generos("Crime", "Suspense"),
                elenco(
                        new CastMember("Jake Gyllenhaal", RoleType.PROTAGONIST),
                        new CastMember("Mark Ruffalo", RoleType.SUPPORTING),
                        new CastMember("Robert Downey Jr.", RoleType.SUPPORTING)
                ), null, 7.7, 2007));

        filmes.add(new Movie("m15", "Titanic", "James Cameron",
                generos("Drama", "Romance"),
                elenco(
                        new CastMember("Leonardo DiCaprio", RoleType.PROTAGONIST),
                        new CastMember("Kate Winslet", RoleType.PROTAGONIST)
                ), null, 7.9, 1997));

        filmes.add(new Movie("m16", "Avatar", "James Cameron",
                generos("Ficção Científica", "Aventura"),
                elenco(
                        new CastMember("Sam Worthington", RoleType.PROTAGONIST),
                        new CastMember("Zoe Saldana", RoleType.PROTAGONIST)
                ), "Avatar", 7.9, 2009));

        filmes.add(new Movie("m17", "Parasite", "Bong Joon-ho",
                generos("Drama", "Suspense"),
                elenco(
                        new CastMember("Song Kang-ho", RoleType.PROTAGONIST)
                ), null, 8.5, 2019));

        return filmes;
    }

    private static Set<String> generos(String... nomes) {
        Set<String> set = new HashSet<>();
        for(String nome : nomes) {
            set.add(nome);
        }
        return set;
    }

    private static List<CastMember> elenco(CastMember... membros) {
        List<CastMember> lista = new ArrayList<>();
        for(CastMember membro : membros) {
            lista.add(membro);
        }
        return lista;
    }

    private static UserProfile buildUserProfile(List<Movie> movies) {
        Movie inception = findById(movies, "m1");
        Movie darkKnight = findById(movies, "m2");
        Movie dune = findById(movies, "m5");

        UserProfile user = new UserProfile("Erik");
        user.addLikedMovie(inception);
        user.addLikedMovie(darkKnight);
        user.addLikedMovie(dune);

        user.addFavoriteDirector("Christopher Nolan");

        user.setConnectionTypeMultiplier(ConnectionType.GENRE, 1.2);

        return user;
    }

    private static Movie findById(List<Movie> movies, String id) {
        for(Movie movie : movies) {
            if(movie.getId().equals(id)) return movie;
        }
        throw new IllegalArgumentException("Filme não encontrado: " + id);
    }

    private static void printReport(UserProfile user, List<RecommendationResult> recommendations) {
        System.out.println("===================================================");
        System.out.println(" Sistema de Recomendação de Filmes baseado em Grafo");
        System.out.println("===================================================");
        System.out.println();
        System.out.println("Usuário: " + user.getName());
        System.out.println("Filmes curtidos:");

        for(Movie m : user.getLikedMovies()) {
            System.out.println("   - " + m);
        }
        System.out.println();
        System.out.println("===================================================");
        System.out.println("Recomendações:");
        System.out.println("===================================================");

        if(recommendations.isEmpty()) {
            System.out.println("Nenhuma recomendação encontrada com os dados atuais.");
            return;
        }

        int rank = 1;
        for(RecommendationResult result : recommendations) {
            System.out.println(rank + ". " + result.getMovie() + " - compatibilidade: " +  result.getScore());
            System.out.println("    Motivos:");
            for(String reason : result.getReasons()) {
                System.out.println("    - " + reason);
            }
            System.out.println();
            rank++;
        }
    }
}