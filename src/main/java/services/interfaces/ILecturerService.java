package services.interfaces;

import domain.models.Lecturer;

import java.util.List;

public interface ILecturerService {
    List<Lecturer> getAll();
    Lecturer getLecturerByID(int id);
    long getNumber();
    void addLecturer(Lecturer lecturer);
    void updateLecturer(Lecturer lecturer);
    void deleteLecturer(Lecturer lecturer);
}
