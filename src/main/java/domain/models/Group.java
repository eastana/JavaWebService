package domain.models;

import domain.DTOs.GroupStudentDTO;

import java.util.List;

public class Group {
    private int id;
    private int name;
    private Specialization specialization;
    private List<GroupStudentDTO> students;

    public Group(){

    }

    public Group(int id, int name) {
        setId(id);
        setName(name);
    }

    public Group(int id, int name, Specialization specialization, List<GroupStudentDTO> students) {
        setId(id);
        setName(name);
        setSpecialization(specialization);
        setStudents(students);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public List<GroupStudentDTO> getStudents() {
        return students;
    }

    public void setStudents(List<GroupStudentDTO> students) {
        this.students = students;
    }

    public void addStudent(GroupStudentDTO student){
        students.add(student);
    }

}
