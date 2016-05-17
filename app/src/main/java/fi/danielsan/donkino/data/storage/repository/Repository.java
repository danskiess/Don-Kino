package fi.danielsan.donkino.data.storage.repository;

import java.util.List;

import rx.Observable;

public interface Repository<T, Id> {
    void save(T entity);
    void saveBatch(List<T> entities);
    Observable<List<T>> queryAll();
    T findById(Id id);
    void delete(T entity);
}