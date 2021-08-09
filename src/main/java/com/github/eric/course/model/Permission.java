package com.github.eric.course.model;

import javax.persistence.*;

@Entity
@Table(name = "permission", schema = "public")
public class Permission extends BaseEntity{
    private String name;

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
