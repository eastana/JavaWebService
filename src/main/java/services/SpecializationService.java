package services;

import domain.models.Specialization;
import repositories.entities.SpecializationRepository;
import repositories.interfaces.ISpecializationRepository;
import services.interfaces.ISpecializationService;

public class SpecializationService implements ISpecializationService {
    private ISpecializationRepository specRepo;

    public SpecializationService() {
        specRepo = new SpecializationRepository();
    }

    @Override
    public void addSpecialization(Specialization specialization) {
        specRepo.addSpecialization(specialization);
    }
}
