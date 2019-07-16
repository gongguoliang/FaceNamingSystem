package com.gong.FaceNamingSystem.service;

import com.gong.FaceNamingSystem.model.Course;
import com.gong.FaceNamingSystem.model.Student;
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
public class Attendance_listService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Student> getStuByCourseId(Integer course_id){
        String sql = "SELECT student.id,student.name FROM attendance_list INNER JOIN student ON student.id = attendance_list.stu_id WHERE attendance_list.course_id="+course_id.toString();
        return (List<Student>) jdbcTemplate.query(sql, new RowMapper<Student>(){
            @Override
            public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
                Student student= new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                return  student;
            }

        });
    }
    public List<Course> getCourseByStudentId(Integer stu_id){
        String sql = "SELECT course.course_id,course_name,teacher_name,classroom,time FROM attendance_list INNER JOIN course ON attendance_list.course_id= course.course_id WHERE attendance_list.stu_id ="+stu_id.toString();

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
