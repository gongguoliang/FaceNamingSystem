package com.gong.FaceNamingSystem.Mapper;

import com.gong.FaceNamingSystem.model.Course;
import org.springframework.stereotype.Component;

@Component(value = "CourseMapper")
public interface CourseMapper {
    int deleteByPrimaryKey(Integer courseId);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(Integer courseId);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);
}