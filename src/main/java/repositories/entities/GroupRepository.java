package repositories.entities;

import domain.DTOs.GroupStudentDTO;
import domain.models.Group;
import domain.models.Specialization;
import repositories.db.PostgresRepository;
import repositories.interfaces.IGroupRepository;

import javax.ws.rs.BadRequestException;
import java.sql.*;
import java.util.LinkedList;

public class GroupRepository implements IGroupRepository {

    private Connection postgresConn;

    public GroupRepository() {
        postgresConn = PostgresRepository.getConnection();
    }

    public Group groupInfo(int spec, int groupName) {
        try {
            Group group = getGroup(spec, groupName);

            String sql = "SELECT s.id, s.name,s.surname,s.birthday,s.major,s.gpa " +
                    "FROM students s " +
                    "WHERE specialization_id = ? " +
                    "AND group_id = (SELECT group_id FROM groupnum WHERE group_name = ?)";

            PreparedStatement stmt = postgresConn.prepareStatement(sql);
            stmt.setInt(1, spec);
            stmt.setInt(2, groupName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                GroupStudentDTO student = new GroupStudentDTO(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getDate("birthday"),
                        rs.getString("major"),
                        rs.getFloat("gpa")
                );
                group.addStudent(student);
            }
            return group;
        } catch (SQLException e) {
            throw new BadRequestException();
        }
    }

    private Group getGroup(int spec, int name) {
        try {
            String sql = "SELECT * FROM groupnum WHERE group_name = ?";
            PreparedStatement stmt = postgresConn.prepareStatement(sql);
            stmt.setInt(1, name);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) return new Group(
                    rs.getInt("group_id"),
                    rs.getInt("group_name"),
                    getSpecByID(spec),
                    new LinkedList<>());
        } catch (SQLException e) {
            throw new BadRequestException();
        }
        return null;
    }

    private Specialization getSpecByID(int id){
        try {
            String sql = "SELECT * FROM specialization WHERE specialization_id = " + id + " LIMIT 1";
            Statement stmt = postgresConn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return new Specialization(
                        rs.getInt("specialization_id"),
                        rs.getString("specialization_name")
                );
            }
        } catch (SQLException e) {
            throw new BadRequestException("Cannot run SQL statement " + e.getSQLState());
        }
        return null;
    }

    @Override
    public void addGroup(Group group) {
        try {
            String sql = "INSERT INTO groupnum(group_name) VALUES ( ? )";
            PreparedStatement stmt = postgresConn.prepareStatement(sql);
            stmt.setInt(1, group.getName());
            stmt.execute();
        } catch (SQLException e) {
            throw new BadRequestException();
        }
    }
}
