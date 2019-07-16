package com.gong.FaceNamingSystem.Mapper;

import com.gong.FaceNamingSystem.model.Choose_course;
import org.springframework.stereotype.Component;

@Component(value = " Choose_courseMapperr")
public interface Choose_courseMapper {
    int deleteByPrimaryKey(Integer chooseId);

    int insert(Choose_course record);

    int insertSelective(Choose_course record);

    Choose_course selectByPrimaryKey(Integer chooseId);

    int updateByPrimaryKeySelective(Choose_course record);

    int updateByPrimaryKey(Choose_course record);
}