package repositories.entities;

import domain.models.*;
import repositories.db.PostgresRepository;
import repositories.interfaces.IUniversityMemberRepository;
import repositories.interfaces.IUserRepository;

import javax.ws.rs.BadRequestException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class LecturerRepository implements IUniversityMemberRepository<Lecturer> {
    private Connection postgresConn;
    private String getLecturerSql;

    public LecturerRepository() {
        postgresConn = PostgresRepository.getConnection();
        getLecturerSql = "SELECT l.id, l.name, l.surname, l.birthday, s.subject_name, " +
                "l.degree, l.salary, l.office, u.username " +
                "FROM lecturers l " +
                "INNER JOIN subject s " +
                "ON l.subject_id = s.id " +
                "INNER JOIN users u " +
                "ON l.user_id = u.id";
    }

    @Override
    public void add(Lecturer lecturer) {
        try {
            String sql = "INSERT INTO lecturers(" +
                    "name, surname, birthday, subject_id, degree, salary, office, user_id)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement stmt = postgresConn.prepareStatement(sql);
            stmt.setString(1, lecturer.getName());
            stmt.setString(2, lecturer.getSurname());
            stmt.setDate(3, lecturer.getBirthday());
            stmt.setInt(4, lecturer.getSubject().getId());
            stmt.setString(5, lecturer.getDegree());
            stmt.setInt(6, lecturer.getSalary());
            stmt.setString(7, lecturer.getOffice());
            stmt.setInt(8, getLastUser().getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new BadRequestException();
        }
    }

    private User getLastUser() {
        String sql = "SELECT * FROM users ORDER BY id DESC LIMIT 1";
        IUserRepository userRepository = new UserRepository();
        return userRepository.queryOne(sql);
    }

    @Override
    public void remove(Lecturer lecturer) {
        try {
            String sql = "DELETE FROM lecturers WHERE id = " + lecturer.getId();
            postgresConn.createStatement().execute(sql);
        } catch (SQLException ex) {
            throw new BadRequestException("Cannot run SQL statement: " + ex.getMessage());
        }
    }

    @Override
    public void update(Lecturer lecturer) {
        String sql = "UPDATE lecturers SET ";

        if (lecturer.getName() != null) {
            sql += "name = ?,";
        }
        if (lecturer.getSurname() != null) {
            sql += "surname = ?,";
        }

        if (lecturer.getBirthday() != null) {
            sql += "birthday = ?,";
        }

        if (lecturer.getSubject() != null && lecturer.getSubject().getId() != null) {
            sql += "subject_id = ?,";
        }

        if (lecturer.getDegree() != null) {
            sql += "degree = ?,";
        }

        if (lecturer.getSalary() != 0) {
            sql += "salary = ?,";
        }

        if (lecturer.getOffice() != null) {
            sql += "office = ?,";
        }

        if (lecturer.getUser_id() != 0) {
            sql += "office = ?,";
        }

        sql = sql.substring(0, sql.length() - 1);

        sql += " WHERE id = " + lecturer.getId();

        try {
            int i = 1;
            PreparedStatement stmt = postgresConn.prepareStatement(sql);
            if (lecturer.getName() != null) {
                stmt.setString(i++, lecturer.getName());
            }

            if (lecturer.getSurname() != null) {
                stmt.setString(i++, lecturer.getSurname());
            }

            if (lecturer.getBirthday() != null) {
                stmt.setDate(i++, lecturer.getBirthday());
            }

            if (lecturer.getSubject() != null && lecturer.getSubject().getId() != null) {
                stmt.setInt(i++, lecturer.getSubject().getId());
            }

            if (lecturer.getDegree() != null) {
                stmt.setString(i++, lecturer.getDegree());
            }

            if (lecturer.getSalary() != 0) {
                stmt.setInt(i++, lecturer.getSalary());
            }

            if (lecturer.getOffice() != null) {
                stmt.setString(i++, lecturer.getOffice());
            }

            if (lecturer.getUser_id() != 0) {
                stmt.setInt(i++, lecturer.getUser_id());
            }

            stmt.execute();
        } catch (SQLException ex) {
            throw new BadRequestException("Cannot run SQL statement: " + ex.getMessage());
        }
    }

    @Override
    public Lecturer getMemberByID(int id) {
        return queryOne(getLecturerSql + " WHERE l.id = " + id + " LIMIT 1");
    }

    @Override
    public List<Lecturer> getAll() {
        return query(getLecturerSql);
    }

    @Override
    public Lecturer queryOne(String sql) {
        try {
            Statement stmt = postgresConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return new Lecturer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getDate("birthday"),
                        new Subject(rs.getString("subject_name")),
                        rs.getString("degree"),
                        rs.getInt("salary"),
                        rs.getString("office"),
                        rs.getString("username")
                );
            }
        } catch (SQLException e) {
            throw new BadRequestException("Cannot run SQL statement " + e.getSQLState());
        }
        return null;
    }

    @Override
    public List<Lecturer> query(String sql) {
        try {
            Statement stmt = postgresConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            LinkedList<Lecturer> lecturers = new LinkedList<>();
            while (rs.next()) {
                lecturers.add(new Lecturer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getDate("birthday"),
                        new Subject(rs.getString("subject_name")),
                        rs.getString("degree"),
                        rs.getInt("salary"),
                        rs.getString("office"),
                        rs.getString("username")
                ));
            }
            return lecturers;
        } catch (SQLException e) {
            throw new BadRequestException("Cannot run SQL statement " + e.getSQLState());
        }
    }

    @Override
    public long getNumber() {
        try {
            String sql = "SELECT COUNT(*) as number_of_lecturer FROM lecturers";
            ResultSet rs = postgresConn.createStatement().executeQuery(sql);
            if (rs.next())
                return rs.getLong("number_of_lecturer");
        } catch (SQLException e) {
            throw new BadRequestException();
        }
        return 0;
    }
}
