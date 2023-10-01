package com.TrainerCRUD.service;

import java.util.List;

public interface CRUDService<T> {
    T create(T entity);
    
    T read(Long id);
    
    List<T> readAll();
    
    T update(T entity);
    
    void delete(Long id);
}
