package domain.models;

import java.sql.Date;

public class Person {
    private int id;
    private String name;
    private String surname;
    private Date birthday;

    public Person(){}

    public Person(int id, String name, String surname, Date birthday) {
        setId(id);
        setName(name);
        setSurname(surname);
        setBirthday(birthday);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
