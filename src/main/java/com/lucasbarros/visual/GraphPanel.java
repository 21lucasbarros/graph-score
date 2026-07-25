package com.lucasbarros.visual;

import com.lucasbarros.graph.MovieGraph;
import com.lucasbarros.model.CastMember;
import com.lucasbarros.model.Movie;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class GraphPanel extends JPanel {

    private static final int LARGURA = 1000;
    private static final int ALTURA = 800;

    private static final int TAMANHO_NO_FILME = 10;
    private static final int TAMANHO_NO_VIRTUAL = 8;

    private List<Movie> movies;

    private Map<String, List<Movie>> diretoresComMaisDeUmFilme = new HashMap<>();
    private Map<String, List<Movie>> atoresComMaisDeUmFilme = new HashMap<>();
    private Map<String, List<Movie>> generosComMaisDeUmFilme = new HashMap<>();

    private Map<String, Point> posicoes = new HashMap<>();

    public GraphPanel(MovieGraph graph) {
        this.movies = graph.getAllMovies();

        montarNosVirtuais();
        calcularPosicoes();

        setPreferredSize(new Dimension(LARGURA, ALTURA));
        setBackground(Color.WHITE);
    }

    private void montarNosVirtuais() {
        Map<String, List<Movie>> todosDiretores = new HashMap<>();
        Map<String, List<Movie>> todosAtores = new HashMap<>();
        Map<String, List<Movie>> todosGeneros = new HashMap<>();

        for (Movie movie : movies) {
            adicionar(todosDiretores, movie.getDirector(), movie);

            for (CastMember membro : movie.getCast()) {
                adicionar(todosAtores, membro.getActorName(), movie);
            }

            for (String genero : movie.getGenres()) {
                adicionar(todosGeneros, genero, movie);
            }
        }

        diretoresComMaisDeUmFilme = filtrarComPeloMenosDoisFilmes(todosDiretores);
        atoresComMaisDeUmFilme = filtrarComPeloMenosDoisFilmes(todosAtores);
        generosComMaisDeUmFilme = filtrarComPeloMenosDoisFilmes(todosGeneros);
    }

    private Map<String, List<Movie>> filtrarComPeloMenosDoisFilmes(Map<String, List<Movie>> mapaOriginal) {
        Map<String, List<Movie>> filtrado = new HashMap<>();
        for (Map.Entry<String, List<Movie>> entry : mapaOriginal.entrySet()) {
            if (entry.getValue().size() >= 2) {
                filtrado.put(entry.getKey(), entry.getValue());
            }
        }
        return filtrado;
    }

    private void adicionar(Map<String, List<Movie>> mapa, String chave, Movie movie) {
        List<Movie> lista = mapa.get(chave);
        if (lista == null) {
            lista = new ArrayList<>();
            mapa.put(chave, lista);
        }
        lista.add(movie);
    }

    private List<String> idsDosFilmes() {
        List<String> ids = new ArrayList<>();
        for (Movie movie : movies) {
            ids.add(movie.getId());
        }
        return ids;
    }

    private void calcularPosicoes() {
        List<String> todosOsNos = new ArrayList<>();
        todosOsNos.addAll(idsDosFilmes());
        todosOsNos.addAll(diretoresComMaisDeUmFilme.keySet());
        todosOsNos.addAll(atoresComMaisDeUmFilme.keySet());
        todosOsNos.addAll(generosComMaisDeUmFilme.keySet());

        List<String[]> arestas = new ArrayList<>();
        for (Map.Entry<String, List<Movie>> entry : diretoresComMaisDeUmFilme.entrySet()) {
            for (Movie m : entry.getValue()) arestas.add(new String[]{entry.getKey(), m.getId()});
        }
        for (Map.Entry<String, List<Movie>> entry : atoresComMaisDeUmFilme.entrySet()) {
            for (Movie m : entry.getValue()) arestas.add(new String[]{entry.getKey(), m.getId()});
        }
        for (Map.Entry<String, List<Movie>> entry : generosComMaisDeUmFilme.entrySet()) {
            for (Movie m : entry.getValue()) arestas.add(new String[]{entry.getKey(), m.getId()});
        }

        Map<String, double[]> pos = new HashMap<>();
        for (String no : todosOsNos) {
            pos.put(no, new double[]{ LARGURA / 2.0 + (Math.random() * 50 - 25), ALTURA / 2.0 + (Math.random() * 50 - 25) });
        }

        for (int iteracao = 0; iteracao < 250; iteracao++) {
            Map<String, double[]> forcas = new HashMap<>();
            for (String no : todosOsNos) forcas.put(no, new double[]{0, 0});

            for (int i = 0; i < todosOsNos.size(); i++) {
                for (int j = i + 1; j < todosOsNos.size(); j++) {
                    String noA = todosOsNos.get(i);
                    String noB = todosOsNos.get(j);
                    double[] pA = pos.get(noA);
                    double[] pB = pos.get(noB);

                    double dx = pA[0] - pB[0];
                    double dy = pA[1] - pB[1];
                    double dist = Math.max(1.0, Math.sqrt(dx * dx + dy * dy));

                    double repulsao = 15000.0 / (dist * dist);
                    double fx = (dx / dist) * repulsao;
                    double fy = (dy / dist) * repulsao;

                    forcas.get(noA)[0] += fx;
                    forcas.get(noA)[1] += fy;
                    forcas.get(noB)[0] -= fx;
                    forcas.get(noB)[1] -= fy;
                }
            }

            for (String[] aresta : arestas) {
                String noA = aresta[0];
                String noB = aresta[1];
                double[] pA = pos.get(noA);
                double[] pB = pos.get(noB);

                double dx = pB[0] - pA[0];
                double dy = pB[1] - pA[1];
                double dist = Math.max(1.0, Math.sqrt(dx * dx + dy * dy));

                double atracao = (dist - 150) * 0.05;
                double fx = (dx / dist) * atracao;
                double fy = (dy / dist) * atracao;

                forcas.get(noA)[0] += fx;
                forcas.get(noA)[1] += fy;
                forcas.get(noB)[0] -= fx;
                forcas.get(noB)[1] -= fy;
            }

            for (String no : todosOsNos) {
                double[] p = pos.get(no);
                double[] f = forcas.get(no);

                f[0] += (LARGURA / 2.0 - p[0]) * 0.01;
                f[1] += (ALTURA / 2.0 - p[1]) * 0.01;

                p[0] += f[0];
                p[1] += f[1];
            }
        }

        for (String no : todosOsNos) {
            posicoes.put(no, new Point((int) pos.get(no)[0], (int) pos.get(no)[1]));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        desenharLigacoesComDiretores(g2);
        desenharLigacoesComAtores(g2);
        desenharLigacoesComGeneros(g2);

        desenharNosVirtuais(g2, diretoresComMaisDeUmFilme, new Color(200, 30, 30));
        desenharNosVirtuais(g2, atoresComMaisDeUmFilme, new Color(30, 150, 60));
        desenharNosVirtuais(g2, generosComMaisDeUmFilme, new Color(40, 100, 220));

        desenharFilmes(g2);
        desenharLegenda(g2);
    }

    private void desenharLigacoesComDiretores(Graphics2D g2) {
        g2.setColor(new Color(200, 30, 30));
        g2.setStroke(new BasicStroke(2));

        for(Map.Entry<String, List<Movie>> entry : diretoresComMaisDeUmFilme.entrySet()) {
            Point posicaoDoDiretor = posicoes.get(entry.getKey());
            for(Movie movie : entry.getValue()) {
                Point posicaoDoFilme = posicoes.get(movie.getId());
                g2.drawLine(posicaoDoFilme.x, posicaoDoFilme.y, posicaoDoDiretor.x, posicaoDoDiretor.y);
            }
        }
    }

    private void desenharLigacoesComAtores(Graphics2D g2) {
        g2.setColor(new Color(30, 150, 60));

        for(Map.Entry<String, List<Movie>> entry : atoresComMaisDeUmFilme.entrySet()) {
            Point posicaoDoAtor = posicoes.get(entry.getKey());
            for(Movie movie : entry.getValue()) {
                Point posicaoDoFilme = posicoes.get(movie.getId());

                CastMember membro = movie.findCastMember(entry.getKey());
                float espessura = (float) (membro.getRole().getRelevance() * 3.0);
                g2.setStroke(new BasicStroke(espessura));

                g2.drawLine(posicaoDoFilme.x, posicaoDoFilme.y, posicaoDoAtor.x, posicaoDoAtor.y);
            }
        }
    }

    private void desenharLigacoesComGeneros(Graphics2D g2) {
        g2.setColor(new Color(40, 100, 220));
        g2.setStroke(new BasicStroke(1));

        for(Map.Entry<String, List<Movie>> entry : generosComMaisDeUmFilme.entrySet()) {
            Point posicaoDoGenero = posicoes.get(entry.getKey());
            for(Movie movie : entry.getValue()) {
                Point posicaoDoFilme = posicoes.get(movie.getId());
                g2.drawLine(posicaoDoFilme.x, posicaoDoFilme.y, posicaoDoGenero.x, posicaoDoGenero.y);
            }
        }
    }

    private void desenharNosVirtuais(Graphics2D g2, Map<String, List<Movie>> nos, Color cor) {
        for(String nome : nos.keySet()) {
            Point p = posicoes.get(nome);

            g2.setColor(cor);
            g2.fillOval(p.x - TAMANHO_NO_VIRTUAL, p.y - TAMANHO_NO_VIRTUAL, TAMANHO_NO_VIRTUAL * 2, TAMANHO_NO_VIRTUAL * 2);

            g2.setColor(Color.BLACK);
            g2.drawOval(p.x - TAMANHO_NO_VIRTUAL, p.y - TAMANHO_NO_VIRTUAL, TAMANHO_NO_VIRTUAL * 2, TAMANHO_NO_VIRTUAL * 2);

            desenharTextoCentralizado(g2, nome, p.x, p.y + TAMANHO_NO_VIRTUAL + 15);
        }
    }

    private void desenharFilmes(Graphics2D g2) {
        for(Movie movie : movies) {
            Point p = posicoes.get(movie.getId());

            g2.setColor(new Color(230, 150, 20));
            g2.fillOval(p.x - TAMANHO_NO_FILME, p.y - TAMANHO_NO_FILME, TAMANHO_NO_FILME * 2, TAMANHO_NO_FILME * 2);

            g2.setColor(Color.BLACK);
            g2.drawOval(p.x - TAMANHO_NO_FILME, p.y - TAMANHO_NO_FILME, TAMANHO_NO_FILME * 2, TAMANHO_NO_FILME * 2);

            desenharTextoCentralizado(g2, movie.getTitle(), p.x, p.y + TAMANHO_NO_FILME + 15);
        }
    }

    private void desenharTextoCentralizado(Graphics2D g2, String texto, int x, int y) {
        g2.setColor(Color.BLACK);
        FontMetrics metrics = g2.getFontMetrics();
        int largura = metrics.stringWidth(texto);
        g2.drawString(texto, x - (largura / 2), y);
    }

    private void desenharLegenda(Graphics2D g2) {
        int x = 20;
        int y = 20;

        g2.setColor(Color.BLACK);
        g2.drawString("Legenda:", x, y);

        y = y + 20;
        g2.setColor(new Color(230, 150, 20));
        g2.fillOval(x, y - 10, 12, 12);
        g2.setColor(Color.BLACK);
        g2.drawString("filme", x + 20, y);

        y = y + 20;
        g2.setColor(new Color(200, 30, 30));
        g2.fillOval(x, y - 10, 12, 12);
        g2.setColor(Color.BLACK);
        g2.drawString("diretor", x + 20, y);

        y = y + 20;
        g2.setColor(new Color(30, 150, 60));
        g2.fillOval(x, y - 10, 12, 12);
        g2.setColor(Color.BLACK);
        g2.drawString("ator (linha mais gross = papel mais importante)", x + 20, y);

        y = y + 20;
        g2.setColor(new Color(40, 100, 220));
        g2.fillOval(x, y - 10, 12, 12);
        g2.setColor(Color.BLACK);
        g2.drawString("gênero", x + 20, y);
    }
}