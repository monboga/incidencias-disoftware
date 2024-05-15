package src.javasqlriskmanager.singletons;

import src.javasqlriskmanager.models.Server;

public class ServerSingleton {

    public static final ServerSingleton instance = new ServerSingleton();

    private Server server;

    public static ServerSingleton getInstance() {
        return instance;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
    
}
