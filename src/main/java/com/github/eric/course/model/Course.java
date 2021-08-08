package com.github.eric.course.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "course", schema = "public")
public class Course extends BaseEntity {
    public String name;
    public String description;
    public String teacher_name;
    public String teacher_description;
    public BigDecimal price;
    public List<Video> videos;
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "teacher_name")
    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }
    @Column(name = "teacher_description")
    public String getTeacher_description() {
        return teacher_description;
    }

    public void setTeacher_description(String teacher_description) {
        this.teacher_description = teacher_description;
    }
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @OneToMany
    @JoinTable(
            name = "video",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    @JsonIgnore
    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
