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

public class TrainerPokemonService {

    private DataSourceConnectionSource connectionSource = null;

    private void closeConn() {
        try
        {
            connectionSource.close();
        } catch (Exception ex)
        {
            Logger.getLogger(TrainerPokemonService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Dao<TrainerPokemon, String> trainerPokemonDao;    

    private Dao<TrainerPokemon, String> getDao() {
        try
        {
            return DaoManager.createDao(connectionSource, TrainerPokemon.class);
        } catch (SQLException ex)
        {
            Logger.getLogger(PokemonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
       
    public TrainerPokemonService(DataSourceConnectionSource conn){
        connectionSource = conn;
    }
     
    public void create(TrainerPokemon entity) {
        try
        {            
            getDao().createIfNotExists(entity);            
            
            closeConn();            
        } catch (SQLException ex)
        {
            Logger.getLogger(TrainerService.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
  
    public Pokemon read(String trainer_id, String pokemon_id) {
        try
        {
            QueryBuilder<TrainerPokemon, String> qb = getDao().queryBuilder();
            Where where = qb.where();
                
            where.eq(TrainerPokemon.POKEMON_ID_FIELD_NAME, pokemon_id);
            where.and();
            where.eq(TrainerPokemon.TRAINER_ID_FIELD_NAME, trainer_id);            
            
            TrainerPokemon trainerPokemon = getDao().queryForFirst(qb.prepare());                                  
            
            closeConn();  
            
            return trainerPokemon.getPokemon();
        } catch (SQLException ex)
        {
            Logger.getLogger(PokemonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }   
}
