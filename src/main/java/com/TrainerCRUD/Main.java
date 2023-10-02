package com.TrainerCRUD;

import com.TrainerCRUD.model.Trainer;
import com.TrainerCRUD.model.Pokemon;
import com.TrainerCRUD.model.TrainerPokemon;
import com.TrainerCRUD.service.PokemonService;
import com.TrainerCRUD.service.TrainerPokemonService;
import com.TrainerCRUD.service.TrainerService;
import com.google.gson.Gson;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.impl.GenericObjectPool;
import static spark.Spark.*;

//Author: Gabriel Itaqui
public class Main {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:32768/app";

    private static DataSource createDataSource() {
        ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(DATABASE_URL, "app", "app");
        PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, null);
        GenericObjectPool connectionPool = new GenericObjectPool(poolableConnectionFactory);
        poolableConnectionFactory.setPool(connectionPool);
        return new PoolingDataSource(connectionPool);
    }
    
    private static DataSourceConnectionSource getConn() {        
        try {    
            return new DataSourceConnectionSource(createDataSource(), DATABASE_URL);    
        } catch (SQLException ex)
        {
            Logger.getLogger(TrainerService.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
    
    private static void setupDatabase() {
        try
        {
            DataSourceConnectionSource conn = getConn();
            
            TableUtils.createTableIfNotExists(conn, Trainer.class);
            TableUtils.createTableIfNotExists(conn, Pokemon.class);
            TableUtils.createTableIfNotExists(conn, TrainerPokemon.class);
            
            try
            {
                conn.close();
            } catch (Exception ex)
            {
                Logger.getLogger(TrainerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(TrainerService.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public static void main(String[] args) {        
        setupDatabase();
       
        port(8080); // Spark will run on port 8080       

        before((req, res) ->
        {            
            res.type("application/json");
        });

        get("/trainer", (request, response) ->
        {
            TrainerService trainerService = new TrainerService(getConn());
            Gson gson = new Gson();
            List<Trainer> trainers = trainerService.readAll();
            List<Object> listaTrainers = new ArrayList<>();

            for (Trainer trainer : trainers)
            {               
                listaTrainers.add(gson.toJson(trainer));               
            }
            
            response.status(200);
            return listaTrainers;
        });
        get("/trainer/:id", (request, response) ->
        {
            TrainerService trainerService = new TrainerService(getConn());            
            Trainer trainer = trainerService.read(request.params(":id"));
            
            response.status(200);
            
            Gson gson = new Gson();
            return gson.toJson(trainer);
        });
        post("/trainer", (request, response) ->
        {
            TrainerService trainerService = new TrainerService(getConn());
            Gson gson = new Gson();
            Trainer trainer = gson.fromJson(request.body(), Trainer.class);

            trainer = trainerService.create(trainer);
            
            response.status(201);
            return gson.toJson(trainer);
        });
        put("/trainer", (request, response) ->
        {
            TrainerService trainerService = new TrainerService(getConn());
            Gson gson = new Gson();
            Trainer trainer = gson.fromJson(request.body(), Trainer.class);

            trainer = trainerService.update(trainer);
            
            response.status(200);
            return gson.toJson(trainer);
        });
        
        get("/trainer/:id/pokemon", (request, response) ->
        {
            TrainerService trainerService = new TrainerService(getConn());            
            PokemonService pokemonService = new PokemonService(getConn());         
            
            Trainer trainer = trainerService.read(request.params(":id"));
            List<Pokemon> pokemons = pokemonService.lookupPokemonsForTrainer(trainer);
            
            Gson gson = new Gson();
            List<Object> listaPokemons = new ArrayList<>();

            for (Pokemon pokemon : pokemons)
            {               
                listaPokemons.add(gson.toJson(pokemon));               
            }
            
            response.status(200);            
            
            return listaPokemons;
        });
        post("/trainer/:id/pokemon", (request, response) ->
        {
            DataSourceConnectionSource conn = getConn();
                    
            TrainerService trainerService = new TrainerService(conn);            
            Trainer trainer = trainerService.read(request.params(":id"));
            PokemonService pokemonService = new PokemonService(conn);
            TrainerPokemonService trainerPokemonService = new TrainerPokemonService(conn);
            
            Gson gson = new Gson();
            Pokemon pokemon = gson.fromJson(request.body(), Pokemon.class);
            
            pokemon = pokemonService.getPokemonPorNome(pokemon.getName());
            
            pokemonService.create(pokemon);
            
            TrainerPokemon trainerPokemon = new TrainerPokemon(trainer, pokemon);
            trainerPokemonService.create(trainerPokemon);
                    
            response.status(200);            
            
            return "";
        });
        get("/trainer/:id/pokemon/:pokemon_id", (request, response) ->
        {
            TrainerPokemonService trainerPokemonService = new TrainerPokemonService(getConn());     
            PokemonService pokemonService = new PokemonService();
            Pokemon pokemon = trainerPokemonService.read(request.params(":id"),request.params(":pokemon_id"));
            
            response.status(200);
            
            return pokemonService.getPokemonPorID(pokemon.getId());
        });
        delete("/trainer/:id/pokemon/:pokemon_id", (request, response) ->
        {
            PokemonService pokemonService = new PokemonService(getConn());            
            pokemonService.delete(request.params(":id"),request.params(":pokemon_id"));
            
            response.status(204);
                        
            return "";
        });
    }
}