package src.javasqlriskmanager.models;

public class Server {
    
    long ID;
    String Server;
    String Description;

    public Server() {
    }

    public Server(long ID, String server, String description) {
        this.ID = ID;
        Server = server;
        Description = description;
    }

    public long getID() {
            return ID;
        }

        public void setID(long ID) {
            this.ID = ID;
        }

        public String getServer() {
            return Server;
        }

        public void setServer(String server) {
            Server = server;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }


}
