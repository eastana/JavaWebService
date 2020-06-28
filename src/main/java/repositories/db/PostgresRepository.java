package repositories.db;

import repositories.interfaces.IDBRepository;

import javax.ws.rs.InternalServerErrorException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresRepository implements IDBRepository {
    private PostgresRepository(){}

    public static Connection getConnection() {
        try{
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/OOPFINAL",
                    "postgres", "***");
        }catch (SQLException e){
            throw new InternalServerErrorException();
        }
    }
}
