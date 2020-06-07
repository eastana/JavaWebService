package repositories.entities;

import domain.models.Group;
import domain.models.Specialization;
import domain.models.Student;
import domain.models.User;
import repositories.db.PostgresRepository;
import repositories.interfaces.IUniversityMemberRepository;
import repositories.interfaces.IUserRepository;

import javax.ws.rs.BadRequestException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class StudentRepository implements IUniversityMemberRepository<Student> {

    private Connection postgresConn;
    private String getStudentSql;

    public StudentRepository() {
        postgresConn = PostgresRepository.getConnection();
        getStudentSql = "SELECT s.id, s.name, s.surname, s.birthday, spec.*, gn.*, " +
                "s.major, s.gpa, u.username " +
                "FROM students s " +
                "INNER JOIN specialization spec " +
                "ON s.specialization_id = spec.specialization_id " +
                "INNER JOIN groupnum gn " +
                "ON s.group_id = gn.group_id "+
                "INNER JOIN users u " +
                "ON s.user_id = u.id";;
    }

    @Override
    public Student getMemberByID(int id) {
        String sql = getStudentSql + " WHERE s.id = " + id + " LIMIT 1";
        return queryOne(sql);
    }

    @Override
    public List<Student> getAll() {
        return query(getStudentSql);
    }

    @Override
    public long getNumber() {
        try {
            String sql = "SELECT COUNT(*) as number_of_students FROM students";
            ResultSet rs = postgresConn.createStatement().executeQuery(sql);
            if (rs.next())
                return rs.getLong("number_of_students");
        } catch (SQLException e) {
            throw new BadRequestException();
        }
        return 0;
    }

    @Override
    public void add(Student student) {
        try {
            String sql = "INSERT INTO students(" +
                    "name, surname, birthday, specialization_id, group_id, major, gpa, user_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = postgresConn.prepareStatement(sql);
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getSurname());
            stmt.setDate(3, student.getBirthday());
            stmt.setInt(4, student.getSpecialization().getId());
            stmt.setInt(5, student.getGroup().getId());
            stmt.setString(6, student.getMajor());
            stmt.setFloat(7, student.getGpa());
            stmt.setInt(8, getLastUser().getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new BadRequestException();
        }
    }

    private User getLastUser(){
        String sql = "SELECT * FROM users ORDER BY id DESC LIMIT 1";
        IUserRepository userRepository = new UserRepository();
        return userRepository.queryOne(sql);
    }

    @Override
    public void remove(Student student) {
        try {
            String sql = "DELETE FROM students WHERE id = " + student.getId();
            postgresConn.createStatement().execute(sql);
        } catch (SQLException ex) {
            throw new BadRequestException("Cannot run SQL statement: " + ex.getMessage());
        }
    }

    @Override
    public void update(Student student) {
        String sql = "UPDATE students SET ";

        if (student.getName() != null) {
            sql += "name = ?,";
        }
        if (student.getSurname() != null) {
            sql += "surname = ?,";
        }

        if (student.getBirthday() != null) {
            sql += "birthday = ?,";
        }

        if (student.getSpecialization() != null) {
            sql += "specialization_id = ?,";
        }

        if (student.getGroup() != null) {
            sql += "group_id = ?,";
        }

        if (student.getMajor() != null) {
            sql += "major = ?,";
        }

        if (student.getGpa() != 0) {
            sql += "gpa = ?,";
        }

        sql = sql.substring(0, sql.length() - 1);

        sql += " WHERE id = " + student.getId();

        try {
            int i = 1;
            PreparedStatement stmt = postgresConn.prepareStatement(sql);
            if (student.getName() != null) {
                stmt.setString(i++, student.getName());
            }

            if (student.getSurname() != null) {
                stmt.setString(i++, student.getSurname());
            }

            if (student.getBirthday() != null) {
                stmt.setDate(i++, student.getBirthday());
            }

            if (student.getSpecialization() != null) {
                stmt.setInt(i++, student.getSpecialization().getId());
            }

            if (student.getGroup() != null) {
                stmt.setInt(i++, student.getGroup().getId());
            }

            if (student.getMajor() != null) {
                stmt.setString(i++, student.getMajor());
            }

            if (student.getGpa() != 0) {
                stmt.setFloat(i++, student.getGpa());
            }

            stmt.execute();
        } catch (SQLException ex) {
            throw new BadRequestException("Cannot run SQL statement: " + ex.getMessage());
        }

    }

    @Override
    public Student queryOne(String sql) {
        try {
            Statement stmt = postgresConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getDate("birthday"),
                        new Specialization(rs.getInt("specialization_id"),
                                rs.getString("specialization_name")),
                        new Group(rs.getInt("group_id"),
                                rs.getInt("group_name")),
                        rs.getString("major"),
                        rs.getFloat("gpa"),
                        rs.getString("username")
                );
            }
        } catch (SQLException e) {
            throw new BadRequestException("Cannot run SQL statement " + e.getSQLState());
        }
        return null;
    }

    @Override
    public List<Student> query(String sql) {
        try {
            Statement stmt = postgresConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            LinkedList<Student> students = new LinkedList<>();
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getDate("birthday"),
                        new Specialization(rs.getInt("specialization_id"),
                                rs.getString("specialization_name")),
                        new Group(rs.getInt("group_id"),
                                rs.getInt("group_name")),
                        rs.getString("major"),
                        rs.getFloat("gpa"),
                        rs.getString("username")
                ));
            }
            return students;
        } catch (SQLException e) {
            throw new BadRequestException();
        }
    }
}
