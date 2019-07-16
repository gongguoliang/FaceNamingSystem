package com.gong.FaceNamingSystem.service;

import com.gong.FaceNamingSystem.model.Admin;
import com.gong.FaceNamingSystem.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Gavinggl on 2018/5/13.
 */
@Service
public class CourseService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Course> getList(){
        String sql = "SELECT course_id,course_name,teacher_name,classroom ,time FROM gong.public.course ";
        return (List<Course>) jdbcTemplate.query(sql, new RowMapper<Course>(){
            @Override
            public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
                Course course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
                course.setTeacherName(rs.getString("teacher_name"));
                course.setClassroom(rs.getString("classroom"));
                course.setTime(rs.getString("time"));
                return  course;
            }

        });
    }
}
