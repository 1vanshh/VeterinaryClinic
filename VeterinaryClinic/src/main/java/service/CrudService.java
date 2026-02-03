package service;

import java.util.List;

public interface CrudService<T, ID> {

    void insert(T entity);
    void update(ID id, T entity);
    void deleteById(ID id);
    T findById(ID id);
    List<T> findAll();
}
