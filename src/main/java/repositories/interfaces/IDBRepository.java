package repositories.interfaces;

import java.sql.Connection;

public interface IDBRepository {
    static Connection getConnection() {
        return null;
    }
}
