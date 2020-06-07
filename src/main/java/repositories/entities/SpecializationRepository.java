package repositories.entities;

import domain.models.Specialization;
import repositories.db.PostgresRepository;
import repositories.interfaces.ISpecializationRepository;

import javax.ws.rs.BadRequestException;
import java.sql.*;

public class SpecializationRepository implements ISpecializationRepository {

    private Connection postgresConn;

    public SpecializationRepository() {
        postgresConn = PostgresRepository.getConnection();
    }

    @Override
    public void addSpecialization(Specialization specialization) {
        try {
            String sql = "INSERT INTO specialization(specialization_name) VALUES ( ? )";
            PreparedStatement stmt = postgresConn.prepareStatement(sql);
            stmt.setString(1, specialization.getName());
            stmt.execute();
        } catch (SQLException e) {
            throw new BadRequestException();
        }
    }
}
