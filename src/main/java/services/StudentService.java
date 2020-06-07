package services;

import domain.PasswordCreator;
import domain.models.Student;
import domain.models.User;
import repositories.entities.StudentRepository;
import repositories.entities.UserRepository;
import repositories.interfaces.IUniversityMemberRepository;
import repositories.interfaces.IUserRepository;
import services.interfaces.IStudentService;

import java.util.List;

public class StudentService implements IStudentService {
    private IUniversityMemberRepository<Student> studentRepo;
    private IUserRepository userRepo;

    public StudentService() {
        studentRepo = new StudentRepository();
        userRepo = new UserRepository();
    }

    @Override
    public Student getStudentByID(int id) {
        return studentRepo.getMemberByID(id);
    }

    @Override
    public List<Student> getAll() {
        return studentRepo.getAll();
    }

    @Override
    public long getNumber() {
        return studentRepo.getNumber();
    }

    @Override
    public void addStudent(Student student) {

        User user = new User();
        user.setUsername((student.getName().charAt(0) + student.getSurname()).toLowerCase());
        user.setPassword(PasswordCreator.createPassword());
        user.setRole("student");
        userRepo.add(user);

        studentRepo.add(student);
    }

    @Override
    public void updateStudent(Student student) {
        studentRepo.update(student);
    }

    @Override
    public void deleteStudent(Student student) {
        studentRepo.remove(student);
    }
}
