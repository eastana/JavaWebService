package domain.models;

import java.sql.Date;

public class Lecturer extends Person {
    private Subject subject;
    private String degree;
    private int salary;
    private String office;
    private int user_id;
    private String username;

    public Lecturer(){}

    public Lecturer(int id, String name, String surname, Date birthday,
                    Subject subject, String degree, int salary, String office, int user_id) {
        super(id, name, surname, birthday);
        setSubject(subject);
        setDegree(degree);
        setSalary(salary);
        setOffice(office);
        setUser_id(user_id);
    }

    public Lecturer(int id, String name, String surname, Date birthday,
                    Subject subject, String degree, int salary, String office, String username) {
        super(id, name, surname, birthday);
        setSubject(subject);
        setDegree(degree);
        setSalary(salary);
        setOffice(office);
        setUsername(username);
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
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
