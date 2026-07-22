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

    private static final int RAIO_DOS_FILMES = 330;
    private static final int RAIO_DOS_DIRETORES = 90;
    private static final int RAIO_DOS_ATORES = 160;
    private static final int RAIO_DOS_GENEROS = 230;

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

        setPreferredSize(new Dimension(ALTURA, LARGURA));
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

    private void calcularPosicoes() {
        int centroX = LARGURA / 2;
        int centroY = ALTURA / 2;

        distribuirEmCirculos(idsDosFilmes(), centroX, centroY, RAIO_DOS_FILMES);
        distribuirEmCirculos(new ArrayList<>(diretoresComMaisDeUmFilme.keySet()), centroX, centroY, RAIO_DOS_DIRETORES);
        distribuirEmCirculos(new ArrayList<>(atoresComMaisDeUmFilme.keySet()), centroX, centroY, RAIO_DOS_ATORES);
        distribuirEmCirculos(new ArrayList<>(generosComMaisDeUmFilme.keySet()), centroX, centroY, RAIO_DOS_GENEROS);
    }

    private void distribuirEmCirculos(List<String> ids, int centroX, int centroY, int raio) {
        for (int i = 0; i < ids.size(); i++) {
            double angulo = (2 * Math.PI * i) / ids.size();

            int x = centroX + (int) (raio * Math.cos(angulo));
            int y = centroY + (int) (raio * Math.sin(angulo));

            posicoes.put(ids.get(i), new Point(x, y));
        }
    }

    private List<String> idsDosFilmes() {
        List<String> ids = new ArrayList<>();
        for (Movie movie : movies) {
            ids.add(movie.getId());
        }

        return ids;
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
            g2.fillRect(p.x - TAMANHO_NO_VIRTUAL, p.y - TAMANHO_NO_VIRTUAL, TAMANHO_NO_VIRTUAL * 2, TAMANHO_NO_VIRTUAL * 2);

            g2.setColor(Color.BLACK);
            g2.drawRect(p.x - TAMANHO_NO_VIRTUAL, p.y - TAMANHO_NO_VIRTUAL, TAMANHO_NO_VIRTUAL * 2, TAMANHO_NO_VIRTUAL * 2);
        }
    }

    private void desenharFilmes(Graphics2D g2) {
        for(Movie movie : movies) {
            Point p = posicoes.get(movie.getId());

            g2.setColor(new Color(230, 150, 20));
            g2.fillOval(p.x - TAMANHO_NO_FILME, p.y - TAMANHO_NO_FILME, TAMANHO_NO_FILME * 2, TAMANHO_NO_FILME * 2);

            g2.setColor(Color.BLACK);
            g2.fillOval(p.x - TAMANHO_NO_FILME, p.y - TAMANHO_NO_FILME, TAMANHO_NO_FILME * 2, TAMANHO_NO_FILME * 2);

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
