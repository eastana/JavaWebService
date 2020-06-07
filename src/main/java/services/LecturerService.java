package services;

import domain.PasswordCreator;
import domain.models.Lecturer;
import domain.models.User;
import repositories.entities.LecturerRepository;
import repositories.entities.UserRepository;
import repositories.interfaces.IUniversityMemberRepository;
import repositories.interfaces.IUserRepository;
import services.interfaces.ILecturerService;

import java.util.List;

public class LecturerService implements ILecturerService {
    private IUniversityMemberRepository<Lecturer> lecturerRepo;
    private IUserRepository userRepo;

    public LecturerService(){
        lecturerRepo = new LecturerRepository();
        userRepo = new UserRepository();
    }

    @Override
    public List<Lecturer> getAll() {
        return lecturerRepo.getAll();
    }

    @Override
    public Lecturer getLecturerByID(int id) {
        return lecturerRepo.getMemberByID(id);
    }

    @Override
    public long getNumber() {
        return lecturerRepo.getNumber();
    }

    @Override
    public void addLecturer(Lecturer lecturer) {

        User user = new User();
        user.setUsername((lecturer.getName().charAt(0) + lecturer.getSurname()).toLowerCase());
        user.setPassword(PasswordCreator.createPassword());
        user.setRole("lecturer");
        userRepo.add(user);

        lecturerRepo.add(lecturer);
    }

    @Override
    public void updateLecturer(Lecturer lecturer) {
        lecturerRepo.update(lecturer);
    }

    @Override
    public void deleteLecturer(Lecturer lecturer) {
        lecturerRepo.remove(lecturer);
    }
}
