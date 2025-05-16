package DAO;

import java.util.List;

public interface DAOInterface<T, K> {

    boolean insert(T t);

    boolean update(T t);

    boolean delete(K id);

    T getById(K id);

    List<T> getAll();
}
