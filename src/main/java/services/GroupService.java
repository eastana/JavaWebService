package services;

import domain.models.Group;
import repositories.entities.GroupRepository;
import repositories.interfaces.IGroupRepository;
import services.interfaces.IGroupService;

public class GroupService implements IGroupService {

    private IGroupRepository groupRepo;

    public GroupService(){
        groupRepo = new GroupRepository();
    }

    @Override
    public Group groupInfo(int spec, int group) {
        return groupRepo.groupInfo(spec, group);
    }

    @Override
    public void addGroup(Group group) {
        groupRepo.addGroup(group);
    }
}
