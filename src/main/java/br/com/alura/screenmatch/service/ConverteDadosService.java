package br.com.alura.screenmatch.service;

public interface ConverteDadosService {

    <T> T obterDados(String json, Class<T> classe);

}
