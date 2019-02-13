package services;

import java.io.Serializable;

public interface BaseService<T> {

    T save(T t);

    T get(Serializable id);

    T update(T t) throws Exception;

    void delete(T t);
}
