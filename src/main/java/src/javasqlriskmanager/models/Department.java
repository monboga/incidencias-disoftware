package src.javasqlriskmanager.models;

import java.sql.Date;

public class Department {
    public Department(Long ID, String Name, String Email, String Phone, Long ID_DepType) {
        this.ID = ID;
        this.Name = Name;
        this.Email = Email;
        this.Phone = Phone;
        this.ID_DepType = ID_DepType;
    }

    Long ID;
    String Name;
    String Email;
    String Phone;
    Long ID_DepType;

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

    public Long getID_DepType() {
        return ID_DepType;
    }

    public void setID_DepType(Long ID_DepType) {
        this.ID_DepType = ID_DepType;
    }

    @Override
    public String toString() {
        return "Department{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Phone='" + Phone + '\'' +
                ", ID_DeptType=" + ID_DepType +
                '}';
    }
}
