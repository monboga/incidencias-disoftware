package src.javasqlriskmanager.models;

import java.sql.Date;

public class Department {

    Long ID;
    String Name;
    String Email;
    String Phone;

    public Department() {
    }

    public Department(Long ID, String name, String email, String phone) {
        this.ID = ID;
        Name = name;
        Email = email;
        Phone = phone;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    @Override
    public String toString() {
        return "Department{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Phone='" + Phone + '\'' +
                '}';
    }
}
