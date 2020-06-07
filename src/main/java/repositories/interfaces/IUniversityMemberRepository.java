package repositories.interfaces;

import domain.UserLoginData;

import java.util.List;

public interface IUniversityMemberRepository<T> extends IEntityRepository<T> {
    T getMemberByID(int id);
    List<T> getAll();
    long getNumber();
}
