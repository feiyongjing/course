package com.github.eric.course.model;

import javax.persistence.*;

@Entity
@Table(name = "permission", schema = "public")
public class Permission extends BaseEntity{
    private String name;
    private Status status;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
