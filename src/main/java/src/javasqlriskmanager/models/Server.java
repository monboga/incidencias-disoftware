package src.javasqlriskmanager.models;

public class Server {
    
        long ID;
        String Server;
        String Description;
        String Price;
        long Warranty;
        String TotalCost;
    
        public Server(long ID, String Server, String Description, String Price, long Warranty, String TotalCost) {
            this.ID = ID;
            this.Server = Server;
            this.Description = Description;
            this.Price = Price;
            this.Warranty = Warranty;
            this.TotalCost = TotalCost;
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
            return Warranty;
        }

        public void setWarranty(long Warranty) {
            this.Warranty = Warranty;
        }

        public String getTotalCost() {
            return TotalCost;
        }

        public void setTotalCost(String TotalCost) {
            this.TotalCost = TotalCost;
        }
}
