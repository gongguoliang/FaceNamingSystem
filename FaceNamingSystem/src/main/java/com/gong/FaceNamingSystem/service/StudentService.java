package com.gong.FaceNamingSystem.service;

import com.gong.FaceNamingSystem.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Gavinggl on 2018/5/9.
 */
@Service
public class StudentService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Student> getList(){
        String sql = "SELECT id, name, password , sex,email,telephone FROM gong.public.student ";
        return (List<Student>) jdbcTemplate.query(sql, new RowMapper<Student>(){
            @Override
            public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
                Student user = new Student();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setName(rs.getString("sex"));
                user.setPassword(rs.getString("telephone"));
                return user;
            }
        });
    }
}
