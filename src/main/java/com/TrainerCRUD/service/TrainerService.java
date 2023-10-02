package com.TrainerCRUD.service;

import com.TrainerCRUD.model.Trainer;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;

public class TrainerService implements CRUDService<Trainer> {
    private DataSourceConnectionSource connectionSource = null;
    
    private void closeConn() {
        try
        {
            connectionSource.close();
        } catch (Exception ex)
        {
            Logger.getLogger(TrainerService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Dao<Trainer, String> trainerDao;    

    private Dao<Trainer, String> getDao() {
        try
        {           
            return DaoManager.createDao(connectionSource, Trainer.class);
        } catch (SQLException ex)
        {
            Logger.getLogger(TrainerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public TrainerService(DataSourceConnectionSource conn){
        connectionSource = conn;
    }
    
    /**
     * Cria um novo treinador.
     *
     * @param entity
     * @param nada;
     * @return O treinador criado.
     */
    @Override
    public Trainer create(Trainer entity) {
        try
        {
            trainerDao = getDao();
            trainerDao.create(entity);
            Trainer trainer = trainerDao.queryForId(String.valueOf(entity.getId()));
            
            closeConn();
            
            return trainer;
        } catch (SQLException ex)
        {
            Logger.getLogger(TrainerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Recupera um treinador pelo seu ID.
     *
     * @param id O ID do treinador a ser recuperado.
     * @return O treinador recuperado.
     */
    public Trainer read(String id) {
        try
        {
            Trainer trainer = getDao().queryForId(id);      
            
            closeConn();
            
            return trainer;
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
            List<Trainer> trainers =  getDao().queryForAll();         
            
            closeConn();
            
            return trainers;
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
        try
        {
            Trainer existingTrainer = read(String.valueOf(entity.getId()));
            if (existingTrainer == null)
            {
                throw new IllegalArgumentException("O treinador com o ID " + entity.getId() + " não existe.");
            }
            
            trainerDao = getDao();
            
            trainerDao.update(entity);            
            Trainer trainer = trainerDao.queryForId(entity.getId());        
            
            closeConn();
            
            return trainer;
        } catch (SQLException ex)
        {
            Logger.getLogger(TrainerService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }    
}
