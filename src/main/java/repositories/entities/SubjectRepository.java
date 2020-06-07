package repositories.entities;

import domain.models.Subject;
import repositories.db.PostgresRepository;
import repositories.interfaces.ISubjectRepository;

import javax.ws.rs.BadRequestException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SubjectRepository implements ISubjectRepository {

    private Connection postgresConn;

    public SubjectRepository() {
        postgresConn = PostgresRepository.getConnection();
    }

    @Override
    public void addSubject(Subject subject) {
        try {
            String sql = "INSERT INTO subject(subject_name, credits)" +
                    "VALUES (?, ?);";
            PreparedStatement stmt = postgresConn.prepareStatement(sql);
            stmt.setString(1, subject.getName());
            stmt.setInt(2, subject.getCredits());
            stmt.execute();
        }catch (SQLException e){
            throw new BadRequestException();
        }
    }
}
