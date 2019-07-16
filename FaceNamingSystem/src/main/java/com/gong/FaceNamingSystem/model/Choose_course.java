package com.gong.FaceNamingSystem.model;

import java.io.Serializable;

public class Choose_course implements Serializable {
    private Integer chooseId;

    private Integer courseId;

    private Integer studentId;

    private static final long serialVersionUID = 1L;

    public Integer getChooseId() {
        return chooseId;
    }

    public void setChooseId(Integer chooseId) {
        this.chooseId = chooseId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}