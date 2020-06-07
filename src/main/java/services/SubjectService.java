package services;

import domain.models.Subject;
import repositories.entities.SubjectRepository;
import repositories.interfaces.ISubjectRepository;
import services.interfaces.ISubjectService;

public class SubjectService implements ISubjectService {
    private ISubjectRepository subRepo;

    public SubjectService(){
        subRepo = new SubjectRepository();
    }

    @Override
    public void addSubject(Subject subject) {
        subRepo.addSubject(subject);
    }
}
