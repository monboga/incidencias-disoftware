package src.javasqlriskmanager.models;

public class Usuario {
    public Usuario(Long ID, String Name, String Email, String Position, Long ID_Role,Long ID_Department, String Password) {
        this.ID = ID;
        this.Name = Name;
        this.Email = Email;
        this.Position = Position;
        this.ID_Role = ID_Role;
        this.ID_Department = ID_Department;
        this.Password = Password;
    }

    Long ID;
    String Name;
    String Email;
    String Position;
    Long ID_Role;
    Long ID_Department;
    String Password;

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

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public Long getID_Role() {
        return ID_Role;
    }

    public void setID_Role(Long ID_Role) {
        this.ID_Role = ID_Role;
    }

    public Long getID_Department() {
        return ID_Department;
    }

    public void setID_Department(Long ID_Department) {
        this.ID_Department = ID_Department;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Position='" + Position + '\'' +
                ", ID_Role=" + ID_Role +
                ", ID_Department=" + ID_Department +
                ", Password='" + Password + '\'' +
                '}';
    }
}
