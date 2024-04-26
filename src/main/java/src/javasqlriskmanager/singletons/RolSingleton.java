package src.javasqlriskmanager.singletons;

import src.javasqlriskmanager.models.Rol;

public class RolSingleton {

    public static final RolSingleton instance = new RolSingleton();

    private Rol rol;

    public static RolSingleton getInstance() {
        return instance;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
