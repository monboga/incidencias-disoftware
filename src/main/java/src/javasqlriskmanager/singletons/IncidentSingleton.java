package src.javasqlriskmanager.singletons;

import src.javasqlriskmanager.models.Incident;

public class IncidentSingleton {

    public static final IncidentSingleton instance = new IncidentSingleton();

    private Incident incident;

    public static IncidentSingleton getInstance() {
        return instance;
    }

    public Incident getIncident() {
        return incident;
    }

    public void setIncident(Incident incident) {
        this.incident = incident;
    }
}
