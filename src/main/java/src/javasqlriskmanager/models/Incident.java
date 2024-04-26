package src.javasqlriskmanager.models;

import java.sql.Date;

public class Incident {

    public Incident(String title, Long id, String description, Date createdAt, Date updateDate, Long id_status, Long id_severity, Long id_creatorUser, Long id_assignedUser, Long id_department) {
        this.title = title;
        this.id = id;
        this.description = description;
        this.createdAt = createdAt;
        this.updateDate = updateDate;
        this.id_status = id_status;
        this.id_severity = id_severity;
        this.id_creatorUser = id_creatorUser;
        this.id_assignedUser = id_assignedUser;
        this.id_department = id_department;
    }

    String title;
    Long id;
    String description;
    Date createdAt;
    Date updateDate;
    Long id_status;
    Long id_severity;
    Long id_creatorUser;
    Long id_assignedUser;
    Long id_department;

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

    public Long getId_status() {
        return id_status;
    }

    public void setId_status(Long id_status) {
        this.id_status = id_status;
    }

    public Long getId_severity() {
        return id_severity;
    }

    public void setId_severity(Long id_severity) {
        this.id_severity = id_severity;
    }

    public Long getId_creatorUser() {
        return id_creatorUser;
    }

    public void setId_creatorUser(Long id_creatorUser) {
        this.id_creatorUser = id_creatorUser;
    }

    public Long getId_assignedUser() {
        return id_assignedUser;
    }

    public void setId_assignedUser(Long id_assignedUser) {
        this.id_assignedUser = id_assignedUser;
    }

    public Long getId_department() {
        return id_department;
    }

    public void setId_department(Long id_department) {
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
                ", id_creatorUser=" + id_creatorUser +
                ", id_assignedUser=" + id_assignedUser +
                ", id_department=" + id_department +
                '}';
    }
}
