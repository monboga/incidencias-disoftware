package src.javasqlriskmanager.models;

public class Server {
    
        long ID;
        String Server;
        String Description;
        String Price;
        long warranty;
    
        public Server(long ID, String Server, String Description, String Price, long warranty) {
            this.ID = ID;
            this.Server = Server;
            this.Description = Description;
            this.Price = Price;
            this.warranty = warranty;
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

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }

        public long getWarranty() {
            return warranty;
        }

        public void setWarranty(long warranty) {
            this.warranty = warranty;
        }

}
