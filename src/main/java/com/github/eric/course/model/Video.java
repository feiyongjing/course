package com.github.eric.course.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "video", schema = "public")
public class Video extends BaseEntity {
    private String name;
    private String description;
    private Course course;
    private String video_url;
    private Status status;

    public Video(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "video_url")
    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public Video() {
    }

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
