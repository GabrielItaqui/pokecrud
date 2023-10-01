package com.TrainerCRUD;

import com.TrainerCRUD.model.Pokemon;
import com.TrainerCRUD.model.Team;
import com.TrainerCRUD.model.Trainer;
import com.TrainerCRUD.service.CRUDService;
import com.TrainerCRUD.service.PokemonService;
import com.TrainerCRUD.service.TrainerService;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

public class Main {

    public static void main(String[] args) throws Exception {
        
        CRUDService<Trainer> trainerService = new TrainerService();       
        Trainer trainer = new Trainer();
        
        trainer.setId(1);
        trainer.setNickname("ash");
        trainer.setFirst_name("Ash");
        trainer.setLast_name("Ketchum");
        trainer.setEmail("forever13@loser.com");
        trainer.setPassword("123456");
        trainer.setTeam(Team.MYSTIC);
        trainer.setPokemons_owned(6);
        
        trainerService.create(trainer);

        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        
        PokemonService pokemonService = new PokemonService();
        
        Pokemon pokemonId = pokemonService.getPokemonPorID(25);
        Pokemon pokemonNome = pokemonService.getPokemonPorNome("ditto");

        System.out.println("Pesquisar Nome por ID no Pokémon: " + pokemonId.getId());
        System.out.println("Resultado: " + pokemonId.getName());
        System.out.println("Pesquisar Id por Nome no Pokémon: " + pokemonNome.getName());
        System.out.println("Resultado: " + pokemonNome.getId());
    }
}
