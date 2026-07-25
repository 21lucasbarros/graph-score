package com.lucasbarros;

import com.lucasbarros.graph.MovieGraph;
import com.lucasbarros.model.CastMember;
import com.lucasbarros.model.Movie;
import com.lucasbarros.model.RoleType;
import com.lucasbarros.visual.GraphPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        List<Movie> filmes = montadorDeFilmesDeTeste();
        MovieGraph graph = new MovieGraph(filmes);

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

        filmes.add(new Movie("m8", "Iron Man", "Jon Favreau",
                generos("Ação", "Ficção Científica"),
                elenco(
                        new CastMember("Robert Downey Jr.", RoleType.PROTAGONIST)
                ), "Marvel Cinematic Universe", 7.9, 2008));

        filmes.add(new Movie("m9", "The Avengers", "Joss Whedon",
                generos("Ação", "Ficção Científica"),
                elenco(
                        new CastMember("Robert Downey Jr.", RoleType.PROTAGONIST),
                        new CastMember("Chris Evans", RoleType.PROTAGONIST)
                ), "Marvel Cinematic Universe", 8.0, 2012));

        filmes.add(new Movie("m10", "Titanic", "James Cameron",
                generos("Drama", "Romance"),
                elenco(
                        new CastMember("Leonardo DiCaprio", RoleType.PROTAGONIST),
                        new CastMember("Kate Winslet", RoleType.PROTAGONIST)
                ), null, 7.9, 1997));

        filmes.add(new Movie("m11", "Avatar", "James Cameron",
                generos("Ficção Científica", "Aventura"),
                elenco(
                        new CastMember("Sam Worthington", RoleType.PROTAGONIST),
                        new CastMember("Zoe Saldana", RoleType.PROTAGONIST)
                ), "Avatar", 7.9, 2009));

        filmes.add(new Movie("m12", "Parasite", "Bong Joon-ho",
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
}