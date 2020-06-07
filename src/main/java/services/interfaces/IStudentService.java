package services.interfaces;

import domain.models.Student;

import java.util.List;

public interface IStudentService {
    Student getStudentByID(int id);
    List<Student> getAll();
    long getNumber();
    void addStudent(Student student);
    void updateStudent(Student student);
    void deleteStudent(Student student);
}
