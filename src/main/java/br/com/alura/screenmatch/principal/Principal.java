package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private Scanner scan = new Scanner(System.in);

    private ConsumoApi consumoApi = new ConsumoApi();

    private ConverteDados converteDados = new ConverteDados();

    public void exibeMenu() {

        System.out.println("Digite o nome da série para busca");
        var serie = scan.nextLine();

        var endereco = ENDERECO + serie.replaceAll(" ", "+") + API_KEY;
        var json = consumoApi.obterDados(endereco);

        var dadosSerie = converteDados.obterDados(json, DadosSerie.class);

        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<DadosTemporada>();

        for (int i = 1; i < dadosSerie.temporadas(); i++) {
            var jobj = consumoApi.obterDados(ENDERECO + serie.replaceAll(" ", "+") + "&season=" + i + API_KEY);
            var dadosTemporada = converteDados.obterDados(jobj, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }

        temporadas.forEach(System.out::println);

        System.out.println("------------EPISODIOS------------");
        temporadas.forEach(t -> {
            t.episodios().forEach(e -> {
                System.out.println(e.titulo());
            });
        });


        


    }

}
