package com.gong.FaceNamingSystem.Mapper;

import com.gong.FaceNamingSystem.model.Student;
import org.springframework.stereotype.Component;

@Component(value = "StudentMapper")
public interface StudentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);
}