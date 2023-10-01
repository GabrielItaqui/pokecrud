package com.TrainerCRUD.service;

import com.TrainerCRUD.model.Trainer;
import java.util.ArrayList;
import java.util.List;

public class TrainerService implements CRUDService<Trainer> {

    private List<Trainer> trainers;

    public TrainerService() {
        this.trainers = new ArrayList<>();
    }

    /**
     * Verifica se um treinador existe.
     *
     * @param id O ID do treinador a ser verificado.
     * @return `true` se o treinador existir, `false` caso contrário.
     */
    public boolean treinadorExiste(int id) {
        return trainers.stream().anyMatch(trainer -> trainer.getId() == id);
    }

    /**
     * Cria um novo treinador.
     *
     * @param trainer O treinador a ser criado.
     * @return O treinador criado.
     */
    @Override
    public Trainer create(Trainer entity) {
        if (entity == null)
        {
            throw new NullPointerException("O treinador não pode ser nulo. Por favor, forneça um treinador válido.");
        }
        if (entity.getId() < 0)
        {
            throw new IllegalArgumentException("O ID do treinador não pode ser negativo. Por favor, forneça um ID do treinador válido.");
        }
        if (treinadorExiste(entity.getId()))
        {
            throw new IllegalArgumentException("O treinador com o ID " + entity.getId() + " já existe. Por favor, forneça um ID do treinador exclusivo.");
        }
        trainers.add(entity);
        return entity;
    }

    /**
     * Recupera um treinador pelo seu ID.
     *
     * @param id O ID do treinador a ser recuperado.
     * @return O treinador recuperado.
     */
    @Override
    public Trainer read(Long id) {
        try
        {
            return trainers.stream().filter(trainer -> trainer.getId() == id).findFirst().orElse(null);
        } catch (Exception e)
        {
            throw new IllegalArgumentException("O treinador com o ID " + id + " não existe. Por favor, forneça um ID do treinador válido.", e);
        }
    }

    /**
     * Recupera todos os treinadores.
     *
     * @return Uma lista de todos os treinadores.
     */
    @Override
    public List<Trainer> readAll() {
        try
        {
            return trainers.stream().toList();
        } catch (Exception e)
        {
            throw new RuntimeException("Falha ao recuperar todos os treinadores", e);
        }
    }

    /**
     * Atualiza um treinador existente.
     *
     * @param id O ID do treinador a ser atualizado.
     * @param trainer O treinador com as alterações a serem aplicadas.
     * @return O treinador atualizado.
     */
    @Override
    public Trainer update(Trainer entity) {
        Trainer existingTrainer = read(Long.valueOf(entity.getId()));
        if (existingTrainer == null)
        {
            throw new IllegalArgumentException("O treinador com o ID " + entity.getId() + " não existe.");
        }

        existingTrainer.setNickname(entity.getNickname());
        existingTrainer.setFirst_name(entity.getFirst_name());
        existingTrainer.setLast_name(entity.getLast_name());
        existingTrainer.setEmail(entity.getEmail());
        existingTrainer.setPassword(entity.getPassword());
        existingTrainer.setTeam(entity.getTeam());
        existingTrainer.setPokemons_owned(entity.getPokemons_owned());

        return entity;
    }

    /**
     * Exclui um treinador.
     *
     * @param id O ID do treinador a ser excluído.
     */
    @Override
    public void delete(Long id) {
        Trainer existingTrainer = read(id);
        if (existingTrainer == null)
        {
            throw new IllegalArgumentException("O treinador com o ID " + id + " não existe.");
        }

        try
        {
            trainers.remove(existingTrainer);
        } catch (Exception e)
        {
            throw new IllegalArgumentException("Falha ao remover o treinador com o ID " + id, e);
        }
    }

}
