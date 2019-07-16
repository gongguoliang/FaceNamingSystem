package com.gong.FaceNamingSystem.controller;

import com.gong.FaceNamingSystem.Mapper.AdminMapper;

import com.gong.FaceNamingSystem.Mapper.CourseMapper;
import com.gong.FaceNamingSystem.model.Admin;
import com.gong.FaceNamingSystem.model.Course;
import com.gong.FaceNamingSystem.model.Student;
import com.gong.FaceNamingSystem.service.AdminService;
import com.gong.FaceNamingSystem.service.Attendance_listService;
import com.gong.FaceNamingSystem.service.Choose_courseService;
import com.gong.FaceNamingSystem.service.CourseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Gavinggl on 2018/5/10.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private AdminService adminService;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseService courseService;
    @Autowired
    private Attendance_listService attendance_listService;
    @Autowired
    private Choose_courseService choose_courseService;

    @ApiOperation(value="Registered administrator", notes="根据username和password来注册管理员对象")
    @RequestMapping(value="/register", method=RequestMethod.PUT)
    public String register() {
        return  "OK";
    }

    @ApiOperation(value="Login administrator", notes="根据username和password来登录管理员对象")
    @RequestMapping(value="/login", method=RequestMethod.PUT)
    public String login() {
        return  "OK";
    }

    @ApiOperation(value="Get all administrator details")
    @RequestMapping(value="/list", method= RequestMethod.GET)
    public List<Admin> getAdmins() {
         List<Admin> admins=adminService.getList();
        return admins;
      }

    @ApiOperation(value="Get administrator details", notes="根据url的id来获取管理员详细信息")
    @RequestMapping(value="/{String}", method= RequestMethod.GET)
     public Admin  getCourse(@PathVariable("String")  String username) {
        Admin admin =adminMapper.selectByPrimaryKey(username);
        return admin;
    }
    @ApiOperation(value="Create an administrator", notes="根据admin对象创建课程")
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public void save(@RequestBody Admin admin ) {
        adminMapper.insert(admin);
    }

    @ApiOperation(value="Update administrator information", notes="根据传过来的admin信息来更新管理员详细信息")
    @RequestMapping(value="/update/{String}", method=RequestMethod.PUT)
    public void update(@RequestBody Admin admin) {
        adminMapper.updateByPrimaryKey(admin);
    }

    @ApiOperation(value="Remove administrator", notes="根据url的id来指定删除对象")
    @RequestMapping(value="/delete/{String}", method=RequestMethod.DELETE)
    public void delete(@PathVariable("String") String username) {
        adminMapper.deleteByPrimaryKey(username);
    }

    //管理员对选课表操作
    @ApiOperation(value="See all course selection information for a course", notes="根据课程Id来查看学生选课信息")
    @RequestMapping(value="/listChooseCourese/{id}", method=RequestMethod.GET)
    public List<Student> listChooseCourese(@PathVariable("id") int id) {
        List<Student> students=choose_courseService.getStuByCourseId(id);
        return students;
    }

    //管理员对出勤表操作
    @ApiOperation(value="View student attendance information for a course", notes="根据课程Id来查看学生出勤信息")
    @RequestMapping(value="/listAttendance/{id}", method=RequestMethod.GET)
    public List<Student> list_attendacnce(@PathVariable("id") int id) {
        List<Student> students=attendance_listService.getStuByCourseId(id);
        return students;
    }


    //管理员对于课程操作
    @ApiOperation(value="Publishing courses", notes="根据课程信息来创建课程信息")
    @RequestMapping(value="/addCourse", method=RequestMethod.POST)
    public void save(@RequestBody Course course ) {
        courseMapper.insert(course);
    }
    @ApiOperation(value="Delete course", notes="根据课程的id来指定删除对象")
    @RequestMapping(value="/deleteCourse/{Integer}", method=RequestMethod.DELETE)
    public void delete(@PathVariable("Integer") Integer course_id) {
        courseMapper.deleteByPrimaryKey(course_id);
    }

    @ApiOperation(value="Update course", notes="根据传过来的course信息来更新课程详细信息")
    @RequestMapping(value="/updateCourse/{Integer}", method=RequestMethod.PUT)
    public void update(@RequestBody  Course course) {
        courseMapper.updateByPrimaryKey(course);
    }
    @ApiOperation(value="Course list", notes="")
    @RequestMapping(value="/listCourse", method=RequestMethod.GET)
    public List<Course> getCourses() {
        List<Course> courses=courseService.getList();
        return courses;
    }

}
