package domain.models;

import java.sql.Date;

public class Student extends Person {
    private Specialization specialization;
    private Group group;
    private String major;
    private float gpa;
    private int user_id;
    private String username;

    public Student(){}

    public Student(int id, String name, String surname, Date birthday, Specialization specialization,
                   Group group, String major, float gpa, int user_id) {
        super(id, name, surname, birthday);
        setSpecialization(specialization);
        setGroup(group);
        setMajor(major);
        setGpa(gpa);
        setUser_id(user_id);
    }

    public Student(int id, String name, String surname, Date birthday, Specialization specialization,
                   Group group, String major, float gpa, String username) {
        super(id, name, surname, birthday);
        setSpecialization(specialization);
        setGroup(group);
        setMajor(major);
        setGpa(gpa);
        setUsername(username);
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
