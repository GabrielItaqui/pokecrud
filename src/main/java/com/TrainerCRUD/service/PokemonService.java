package com.TrainerCRUD.service;

import com.TrainerCRUD.model.Pokemon;
import com.TrainerCRUD.model.Trainer;
import com.TrainerCRUD.model.TrainerPokemon;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.squareup.okhttp.ResponseBody;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PokemonService {

    private DataSourceConnectionSource connectionSource = null;

    private void closeConn() {
        try
        {
            connectionSource.close();
        } catch (Exception ex)
        {
            Logger.getLogger(PokemonService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Dao<Pokemon, String> getPokemonDao() {
        try
        {
            return DaoManager.createDao(connectionSource, Pokemon.class);
        } catch (SQLException ex)
        {
            Logger.getLogger(PokemonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Dao<TrainerPokemon, String> getTrainerPokemonDao() {
        try
        {
            return DaoManager.createDao(connectionSource, TrainerPokemon.class);
        } catch (SQLException ex)
        {
            Logger.getLogger(PokemonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public PokemonService() {        
    }

    public PokemonService(DataSourceConnectionSource conn) {
        connectionSource = conn;
    }

    private static final String POKEAPI_BASE_URL = "https://pokeapi.co/api/v2/pokemon/";
    private static final String errorMsg = "Erro ao consumir a PokéAPI";

    private PreparedQuery<Pokemon> pokemonsForTrainerQuery = null;

    public static Object getPokemonPorID(int id) throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(POKEAPI_BASE_URL + id)
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful())
        {
            throw new Exception("Erro ao consumir a PokéAPI");
        }
        ResponseBody responseBody = response.body();
        String json = responseBody.string();

        return json;
    }

    public static Pokemon getPokemonPorNome(String nome) throws Exception {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(POKEAPI_BASE_URL + nome)
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful())
        {
            throw new Exception(errorMsg);
        }
        ResponseBody responseBody = response.body();
        String json = responseBody.string();

        Gson gson = new Gson();

        return gson.fromJson(json, Pokemon.class);
    }

    public void create(Pokemon entity) {
        try
        {            
            getPokemonDao().createIfNotExists(entity);
            
            closeConn();        
        } catch (SQLException ex)
        {
            Logger.getLogger(TrainerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public List<Pokemon> lookupPokemonsForTrainer(Trainer trainer) throws Exception {
        pokemonsForTrainerQuery = makePokemonsForTrainerQuery();   
        pokemonsForTrainerQuery.setArgumentHolderValue(0, trainer);
        List<Pokemon> pokemons = getPokemonDao().query(pokemonsForTrainerQuery);

        closeConn();

        return pokemons;
    }

    private PreparedQuery<Pokemon> makePokemonsForTrainerQuery() throws SQLException {
        QueryBuilder<TrainerPokemon, String> trainerPokemonQb = getTrainerPokemonDao().queryBuilder();

        trainerPokemonQb.selectColumns(TrainerPokemon.POKEMON_ID_FIELD_NAME);
        SelectArg trainerSelectArg = new SelectArg();

        trainerPokemonQb.where().eq(TrainerPokemon.TRAINER_ID_FIELD_NAME, trainerSelectArg);

        QueryBuilder<Pokemon, String> pokemonQb = getPokemonDao().queryBuilder();

        pokemonQb.where().in("id", trainerPokemonQb);
        return pokemonQb.prepare();
    }

    public void delete(String trainer_id, String pokemon_id) {
        try
        {
            QueryBuilder<TrainerPokemon, String> qb = getTrainerPokemonDao().queryBuilder();
            Where where = qb.where();
                
            where.eq(TrainerPokemon.POKEMON_ID_FIELD_NAME, pokemon_id);
            where.and();
            where.eq(TrainerPokemon.TRAINER_ID_FIELD_NAME, trainer_id);            
            
            TrainerPokemon trainerPokemon = getTrainerPokemonDao().queryForFirst(qb.prepare());
                                      
            getTrainerPokemonDao().delete(trainerPokemon);
            
            closeConn();  
        } catch (SQLException ex)
        {
            Logger.getLogger(PokemonService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
