package com.lucasbarros.visual;

import com.lucasbarros.graph.ConnectionType;
import com.lucasbarros.graph.Edge;
import com.lucasbarros.graph.MovieGraph;
import com.lucasbarros.model.Movie;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GraphPanel extends JPanel {

    private static final int LARGURA = 900;
    private static final int ALTURA = 700;
    private static final int RAIO_DO_CIRCULO = 260;
    private static final int RAIO_DO_NO = 10;

    private MovieGraph graph;
    private List<Movie> movies;
    private Map<String, Point> posicoes = new HashMap<>();

    public GraphPanel(MovieGraph graph) {
        this.graph = graph;
        this.movies = graph.getAllMovies();

        calcularPosicoes();

        setPreferredSize(new Dimension(ALTURA, LARGURA));
        setBackground(Color.WHITE);
    }

    private void calcularPosicoes() {
        int centroX = LARGURA / 2;
        int centroY = ALTURA / 2;

        for (int i = 0; i < movies.size(); i++) {
            double angulo = (2 * Math.PI * i) / movies.size();

            int x = centroX + (int) (RAIO_DO_CIRCULO * Math.cos(angulo));
            int y = centroY + (int) (RAIO_DO_CIRCULO * Math.sin(angulo));

            posicoes.put(movies.get(i).getId(), new Point(x, y));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        desenharArestas(g2);
        desenharNos(g2);
        desenharLegenda(g2);
    }

    private void desenharArestas(Graphics2D g2) {
        Set<String> arestasJaDesenhadas = new HashSet<>();

        for(Movie movie : movies) {
            List<Edge> conexoes = graph.getConnections(movie);

            for(Edge edge : conexoes) {
                Movie target = edge.getTarget();
                String chave = chaveDaAresta(movie.getId(), target.getId(), edge.getType());

                if(arestasJaDesenhadas.contains(chave)) continue;

                arestasJaDesenhadas.add(chave);

                Point p1 = posicoes.get(movie.getId());
                Point p2 = posicoes.get(target.getId());

                g2.setColor(corDoTipo((edge.getType())));
                float espessura = (float) (edge.getWeight() *  3.0);
                g2.setStroke(new BasicStroke(espessura));
                g2.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }

    private void desenharLegenda(Graphics2D g2) {
        int x = 20;
        int y = 20;
        int alturaLinha = 20;

        g2.setColor(Color.BLACK);
        g2.drawString("Legenda:", x, y);

        for(ConnectionType tipo : ConnectionType.values()) {
            y = y + alturaLinha;

            g2.setColor(corDoTipo(tipo));
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(x, y - 4, x + 30, y -4);

            g2.setColor(Color.BLACK);
            g2.drawString(tipo.getDescription(), x + 40, y);
        }
    }

    private String chaveDaAresta(String id1, String id2, ConnectionType tipo) {
        if(id1.compareTo(id2) < 0) {
            return id1 + "|" + id2 + "|" + tipo;
        } else {
            return id2 + "|" + id1 + "|" + tipo;
        }
    }

    private void desenharNos(Graphics2D g2) {
        g2.setStroke(new BasicStroke(1));

        for(Movie movie : movies) {
            Point p = posicoes.get(movie.getId());

            g2.setColor(new Color(30, 90, 120));
            g2.fillOval(p.x - RAIO_DO_NO, p.y - RAIO_DO_NO, RAIO_DO_NO * 2, RAIO_DO_NO * 2);

            g2.setColor(Color.BLACK);
            g2.drawOval(p.x - RAIO_DO_NO, p.y - RAIO_DO_NO, RAIO_DO_NO * 2, RAIO_DO_NO * 2);

            String texto = movie.getTitle();
            FontMetrics metrics = g2.getFontMetrics();
            int larguraTexto = metrics.stringWidth(texto);

            g2.drawString(texto, p.x - (larguraTexto / 2), p.y + RAIO_DO_NO + 15);
        }
    }

    private Color corDoTipo(ConnectionType tipo) {
        if(tipo == ConnectionType.DIRECTOR) {
            return new Color(200, 30, 30);
        }
        if(tipo == ConnectionType.ACTOR) {
            return new Color(30, 150, 60);
        }
        if(tipo == ConnectionType.GENRE) {
            return new Color(40, 100, 220);
        }
        if(tipo == ConnectionType.FRANCHISE) {
            return new Color(160, 40, 180);
        }

        // era
        return new Color(150, 150, 150);
    }
}
