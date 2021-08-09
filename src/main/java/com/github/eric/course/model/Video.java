package com.github.eric.course.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "video", schema = "public")
public class Video extends BaseEntity {
    public String name;
    public String description;
//    public Integer course_id;
    private Course course;

    @JsonBackReference
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name="course_id")
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

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

//    @Column(name = "course_id")
//    public Integer getCourse_id() {
//        return course_id;
//    }
//
//    public void setCourse_id(Integer course_id) {
//        this.course_id = course_id;
//    }

    @Override
    public String toString() {
        return "Video{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", course=" + course +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return Objects.equals(name, video.name) &&
                Objects.equals(description, video.description) &&
                Objects.equals(course, video.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, course);
    }
}
