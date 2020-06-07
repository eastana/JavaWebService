package services.interfaces;

import domain.models.Group;

public interface IGroupService {
    Group groupInfo(int spec, int group);
    void addGroup(Group group);
}
