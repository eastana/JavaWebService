package domain.DTOs;

import domain.models.Person;

import java.sql.Date;

public class GroupStudentDTO extends Person {
    private String major;
    private float gpa;

    public GroupStudentDTO(int id, String name, String surname, Date birthday) {
        super(id, name, surname, birthday);
    }

    public GroupStudentDTO(int id, String name, String surname, Date birthday, String major, float gpa){
        this(id, name, surname, birthday);
        setMajor(major);
        setGpa(gpa);
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }
}
