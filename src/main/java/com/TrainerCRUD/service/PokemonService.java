package com.TrainerCRUD.service;

import com.TrainerCRUD.model.Pokemon;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.google.gson.Gson;
import com.squareup.okhttp.ResponseBody;

public class PokemonService {

    private static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/";

    public static Pokemon getPokemonPorID(int id) throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(POKEAPI_BASE_URL + "pokemon/" + id)
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful())
        {
            ResponseBody responseBody = response.body();
            String json = responseBody.string();

            Gson gson = new Gson();
            Pokemon pokemon = gson.fromJson(json, Pokemon.class);

            return pokemon;
        } else
        {
            throw new Exception("Erro ao consumir a PokéAPI");
        }
    }

    public static Pokemon getPokemonPorNome(String nome) throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(POKEAPI_BASE_URL + "pokemon/" + nome)
                .build();

        Response response = client.newCall(request).execute();

        if (response.isSuccessful())
        {
            ResponseBody responseBody = response.body();
            String json = responseBody.string();

            Gson gson = new Gson();
            Pokemon pokemon = gson.fromJson(json, Pokemon.class);

            return pokemon;
        } else
        {
            throw new Exception("Erro ao consumir a PokéAPI");
        }
    }
}
