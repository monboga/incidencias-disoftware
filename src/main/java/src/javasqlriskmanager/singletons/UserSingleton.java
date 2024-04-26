package src.javasqlriskmanager.singletons;

import src.javasqlriskmanager.models.Usuario;

public class UserSingleton {

    public static final UserSingleton instance = new UserSingleton();

    private Usuario Usuario;

    public static UserSingleton getInstance() {
        return instance;
    }

    public Usuario getUsuario() {
        return Usuario;
    }

    public void setUsuario(Usuario Usuario) {
        this.Usuario = Usuario;
    }
}
