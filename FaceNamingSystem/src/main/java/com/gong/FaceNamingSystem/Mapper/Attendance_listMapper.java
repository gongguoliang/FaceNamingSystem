package com.gong.FaceNamingSystem.Mapper;

import com.gong.FaceNamingSystem.model.Attendance_list;

public interface Attendance_listMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Attendance_list record);

    int insertSelective(Attendance_list record);

    Attendance_list selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Attendance_list record);

    int updateByPrimaryKey(Attendance_list record);
}