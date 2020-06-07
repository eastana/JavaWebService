package repositories.interfaces;

import domain.models.Group;

public interface IGroupRepository {
    Group groupInfo(int spec, int groupName);
    void addGroup(Group group);
}
