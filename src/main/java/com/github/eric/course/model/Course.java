package com.github.eric.course.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "course", schema = "public")
public class Course extends BaseEntity {
    private String name;
    private String description;
    private String teacher_name;
    private String teacher_description;
    private BigDecimal price;
    private List<Video> videos;

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

    //    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "video",
//            joinColumns = @JoinColumn(name = "course_id"),
//            inverseJoinColumns = @JoinColumn(name = "id")
//    )
//    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", teacher_name='" + teacher_name + '\'' +
                ", teacher_description='" + teacher_description + '\'' +
                ", price=" + price +
                ", videos=" + videos +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name) &&
                Objects.equals(description, course.description) &&
                Objects.equals(teacher_name, course.teacher_name) &&
                Objects.equals(teacher_description, course.teacher_description) &&
                Objects.equals(price, course.price) &&
                Objects.equals(videos, course.videos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, teacher_name, teacher_description, price, videos);
    }
}
