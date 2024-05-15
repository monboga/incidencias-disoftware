package src.javasqlriskmanager.models;

public class Rol {
    private Long ID;
    private String Name;

    public Rol(Long ID, String Name){
        this.ID = ID;
        this.Name = Name;
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

    @Override
    public String toString() {
        return "Rol{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                '}';
    }
}

