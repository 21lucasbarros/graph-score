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
                generos("Ficção Cientifica", "Ação"),
                elenco(new CastMember("Leonardo DiCaprio", RoleType.PROTAGONIST),
                        new CastMember("Michael Caine", RoleType.CAMEO)),
        null, 8.8, 2010
        ));

        filmes.add(new Movie("m2", "Interstellar", "Christopher Nolan",
                generos("Ficção Cientifica", "Drama"),
                elenco(new CastMember("Matthew McConaughey", RoleType.PROTAGONIST),
                        new CastMember("Michael Caine", RoleType.SUPPORTING)),
                null, 8.6, 2010
        ));

        filmes.add(new Movie("m3", "Titanic", "James Camerpn",
                generos("Drama", "Romance"),
                elenco(new CastMember("Leonardo DiCaprio", RoleType.PROTAGONIST)),
                null, 7.9, 1997
        ));

        filmes.add(new Movie("m4", "Duna", "Dennis Villeneuve",
                generos("Ficção Cientifica", "Aventura"),
                elenco(new CastMember("Timothée Chalamet", RoleType.PROTAGONIST)),
                "Duna", 8.0, 2021
        ));

        filmes.add(new Movie("m5", "Duna: Parte Dois", "Dennis Villeneuve",
                generos("Ficção Cientifica", "Aventura"),
                elenco(new CastMember("Timothée Chalamet", RoleType.PROTAGONIST)),
                "Duna", 8.7, 2024
        ));

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