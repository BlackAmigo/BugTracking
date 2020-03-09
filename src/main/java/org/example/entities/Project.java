package org.example.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Objects;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank(message = "Not blank value is required")
    private String name;
    @NotBlank(message = "Not blank value is required")
    private String textDescription;
    private final Date createdDate;
    private Date lastModifiedDate;

    public Project() {
        Date date = new Date();
        createdDate = date;
        lastModifiedDate = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id &&
                name.equals(project.name) &&
                Objects.equals(textDescription, project.textDescription) &&
                createdDate.equals(project.createdDate) &&
                lastModifiedDate.equals(project.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, textDescription, createdDate, lastModifiedDate);
    }
}
