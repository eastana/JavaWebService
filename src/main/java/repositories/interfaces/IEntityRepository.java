package repositories.interfaces;

import java.util.List;

public interface IEntityRepository<T> {
    void add(T entity);
    void remove(T entity);
    void update(T entity);
    T queryOne(String sql);
    List<T> query(String sql);
}
