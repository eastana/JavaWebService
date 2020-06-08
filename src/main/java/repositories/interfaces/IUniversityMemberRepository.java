package repositories.interfaces;


import java.util.List;

public interface IUniversityMemberRepository<T> extends IEntityRepository<T> {
    T getMemberByID(int id);
    List<T> getAll();
    long getNumber();
}
