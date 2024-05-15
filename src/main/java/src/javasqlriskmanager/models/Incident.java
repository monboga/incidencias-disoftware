package src.javasqlriskmanager.models;

import java.sql.Date;

public class Incident {

    String title;
    Long id;
    String description;
    Date createdAt;
    Date updateDate;
    String id_status;
    String id_severity;
    String id_department;

    public Incident() {
    }

    public Incident(String title, Long id, String description, Date createdAt, Date updateDate, String id_status, String id_severity, String id_department) {
        this.title = title;
        this.id = id;
        this.description = description;
        this.createdAt = createdAt;
        this.updateDate = updateDate;
        this.id_status = id_status;
        this.id_severity = id_severity;
        this.id_department = id_department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getId_status() {
        return id_status;
    }

    public void setId_status(String id_status) {
        this.id_status = id_status;
    }

    public String getId_severity() {
        return id_severity;
    }

    public void setId_severity(String id_severity) {
        this.id_severity = id_severity;
    }

    public String getId_department() {
        return id_department;
    }

    public void setId_department(String id_department) {
        this.id_department = id_department;
    }

    @Override
    public String toString() {
        return "Incident{" +
                "title='" + title + '\'' +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updateDate=" + updateDate +
                ", id_status=" + id_status +
                ", id_severity=" + id_severity +
                ", id_department=" + id_department +
                '}';
    }
}
