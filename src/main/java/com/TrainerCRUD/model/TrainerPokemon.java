package com.TrainerCRUD.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.UUID;

@DatabaseTable(tableName = "trainer_pokemon")
public class TrainerPokemon {

    public final static String TRAINER_ID_FIELD_NAME = "trainer_id";
    public final static String POKEMON_ID_FIELD_NAME = "pokemon_id";

    @DatabaseField(id = true)
    private int id;

    @DatabaseField(foreign = true, columnName = TRAINER_ID_FIELD_NAME)
    Trainer trainer;

    @DatabaseField(foreign = true, columnName = POKEMON_ID_FIELD_NAME)
    Pokemon pokemon;

    TrainerPokemon() {
    }

    public TrainerPokemon(Trainer trainer, Pokemon pokemon) {
        this.id = pokemon.getId();
        this.trainer = trainer;
        this.pokemon = pokemon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

}
