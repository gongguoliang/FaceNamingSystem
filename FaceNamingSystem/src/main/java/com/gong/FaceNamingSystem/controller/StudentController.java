package com.gong.FaceNamingSystem.controller;

import com.gong.FaceNamingSystem.Mapper.Attendance_listMapper;
import com.gong.FaceNamingSystem.Mapper.Choose_courseMapper;
import com.gong.FaceNamingSystem.Mapper.StudentMapper;
import com.gong.FaceNamingSystem.data.dao.CMseetaFaceDao;
import com.gong.FaceNamingSystem.data.dao.UserDao;
import com.gong.FaceNamingSystem.data.entity.CMseetaFaceEntiy;
import com.gong.FaceNamingSystem.data.entity.UserEntity;
import com.gong.FaceNamingSystem.model.Choose_course;
import com.gong.FaceNamingSystem.model.Course;
import com.gong.FaceNamingSystem.model.Student;
import com.gong.FaceNamingSystem.service.Attendance_listService;
import com.gong.FaceNamingSystem.service.Choose_courseService;
import com.gong.FaceNamingSystem.service.CourseService;
import com.gong.FaceNamingSystem.service.StudentService;
import com.gong.FaceNamingSystem.utils.POIUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import seetaface.CMSeetaFace;
import seetaface.SeetaFace;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Gavinggl on 2018/5/8.
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private  StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private Choose_courseMapper choose_courseMapper;
    @Autowired
    private Choose_courseService choose_courseService;
    @Autowired
    private Attendance_listService attendance_listService;
    @Autowired
    private CMseetaFaceDao cmseetafaceDao;
    @Autowired
    private UserDao userDao;

    @ApiOperation(value="Registered", notes="根据基础信息和人脸图片来注册学生对象")
    @RequestMapping(value="/register", method=RequestMethod.PUT)
    public String register() {
        return  "OK";
    }
    @ApiOperation(value="Student Information Entry", notes="根据详细信息来录入学生信息")
    @RequestMapping(value="/InfoInput", method=RequestMethod.PUT)
    public String InfoInput(MultipartFile excelFile) {
        SeetaFace tSeetaFace = new SeetaFace();
        tSeetaFace.initModelPath("C:\\Users\\Gavinggl\\Documents\\Visual Studio 2013\\Projects\\seetaface\\model");
        UserEntity user=new UserEntity();
        CMseetaFaceEntiy face=new CMseetaFaceEntiy();
        String filepath = "D:\\VideoTest\\pic14\\";
        try {
            List<String[]> studentList = POIUtil.readExcel(excelFile);
            for(int i = 0;i<studentList.size();i++){
                String[] students = studentList.get(i);
                Student student = new Student();
                student.setId(Integer.parseInt(students[0].trim()));
                student.setName(students[1].trim());
                studentMapper.insert(student);
                user.setUserID(students[0].trim());
                user.setUserName(students[1].trim());
                user.setCollectionName(students[0].trim()+"face");
                userDao.insert(user);
                CMSeetaFace[] tFaces1 = tSeetaFace.DetectFacesPath(filepath+students[0].trim()+".jpg");
                face.setUserId(students[0].trim());
                face.setFeatures(tFaces1[0].features);
                cmseetafaceDao.insert(face,user.getCollectionName());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  "OK";
    }


    @ApiOperation(value="Get student list", notes="获取全部学生详细信息")
    @RequestMapping(value="/list", method=RequestMethod.GET)
    public List<Student> getStudents() {
        List<Student> students=studentService.getList();
        return students;
    }

   @ApiOperation(value="Get student details", notes="根据url的id来获取学生详细信息")
   @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Student getStudent(@PathVariable int id) {
        Student student =studentMapper.selectByPrimaryKey(id);
        return student;
    }

    @ApiOperation(value="Create students", notes="根据Student对象创建学生")
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public void save(@RequestBody Student  student ) {
        studentMapper.insert(student);
    }

    @ApiOperation(value="Update student details", notes="根据传过来的student信息来更新学生详细信息")
    @RequestMapping(value="/updata/{id}", method=RequestMethod.PUT)
    public void update(@RequestBody Student student) {
        studentMapper.updateByPrimaryKey(student);
    }

    @ApiOperation(value="Delete users", notes="根据url的id来指定删除对象")
    @RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        studentMapper.deleteByPrimaryKey(id);
    }



    //学生对课程操作 后期根据登录后获得学生Id再改
    @ApiOperation(value="Curriculum schedule", notes="")
    @RequestMapping(value="/listCourse", method=RequestMethod.GET)
    public List<Course> getCourses() {
        List<Course> courses=courseService.getList();
        return courses;
    }
    @ApiOperation(value="Select course", notes="根据课程id选课对象创建学生")
    @RequestMapping(value="/selectCourse", method=RequestMethod.POST)
    public void save(@RequestBody Choose_course choose_course) {
        choose_courseMapper.insert(choose_course);
    }

    @ApiOperation(value="See this student's course selection information", notes="查看本学生所有选课信息")
    @RequestMapping(value="/listChooseCourese/{id}", method=RequestMethod.GET)
    public List<Course> listChooseCourese(@PathVariable("id") int id){
        List<Course> courses=choose_courseService.getList(id);
        return courses;
    }
    @ApiOperation(value="View this student's attendance information", notes="查看本学生所有出勤信息")
    @RequestMapping(value="/list_attendacnce/{id}", method=RequestMethod.GET)
    public List<Course> list_attendacnce(@PathVariable("id") int id) {
        List<Course> courses=attendance_listService.getCourseByStudentId(id);
        return courses;
    }

}
