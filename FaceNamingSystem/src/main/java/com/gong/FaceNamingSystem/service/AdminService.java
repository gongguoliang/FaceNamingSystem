package com.gong.FaceNamingSystem.service;

import com.gong.FaceNamingSystem.model.Admin;
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
public class AdminService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public List<Admin> getList(){
        String sql = "SELECT username, password FROM gong.public.admin ";
        return (List<Admin>) jdbcTemplate.query(sql, new RowMapper<Admin>(){
            @Override
            public Admin mapRow(ResultSet rs, int rowNum) throws SQLException {
                Admin admin = new Admin();
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                return  admin;
            }

        });
    }
}
