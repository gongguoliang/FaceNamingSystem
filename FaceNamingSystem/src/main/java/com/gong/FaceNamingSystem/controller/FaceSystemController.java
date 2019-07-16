package com.gong.FaceNamingSystem.controller;

import com.gong.FaceNamingSystem.data.dao.CMseetaFaceDao;
import com.gong.FaceNamingSystem.data.dao.UserDao;
import com.gong.FaceNamingSystem.data.entity.CMseetaFaceEntiy;
import com.gong.FaceNamingSystem.data.entity.UserEntity;
import io.swagger.annotations.ApiOperation;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import seetaface.CMSeetaFace;
import seetaface.SeetaFace;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.bytedeco.javacpp.opencv_core.cvReleaseImage;

/**
 * Created by Gavinggl on 2018/5/13.
 */
@RestController
@RequestMapping("/face")
public class FaceSystemController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private CMseetaFaceDao cmseetafaceDao;
    static BufferedImage bImage;
    private static int width,height;
    SeetaFace tSeetaFace = new SeetaFace();

    @ApiOperation(value="初始化人脸识别系统模型", notes="输入人脸识别系统模型")
    @RequestMapping(value="/init", method= RequestMethod.PUT)
    public void init() {
        tSeetaFace.initModelPath("C:\\Users\\Gavinggl\\Documents\\Visual Studio 2013\\Projects\\seetaface\\model");
    }

    @ApiOperation(value="根据学生id增加学生的人脸图片", notes="")
    @RequestMapping(value="/extract/{id}/{pic_path}", method=RequestMethod.PUT)
    public void extract(@PathVariable("id") int id ) {
        tSeetaFace.initModelPath("C:\\Users\\Gavinggl\\Documents\\Visual Studio 2013\\Projects\\seetaface\\model");
        CMSeetaFace[] tFaces1 = tSeetaFace.DetectFacesPath("d:\\test.jpg");
        CMseetaFaceEntiy face = new CMseetaFaceEntiy();
        face.setUserId(Integer.toString(id));
        float[] feature = tFaces1[0].features;
        face.setFeatures(feature);
        cmseetafaceDao.insertIntoCollection(face, Integer.toString(id)+"face");
    }

    @ApiOperation(value="提取图片中人脸截图", notes="")
    @RequestMapping(value="/extract_face", method=RequestMethod.PUT)
    public void extract_face() {
        try {
        File file = new File("D:\\FaceNamingSystem\\heying4.jpg");
        tSeetaFace.initModelPath("C:\\Users\\Gavinggl\\Documents\\Visual Studio 2013\\Projects\\seetaface\\model");
        CMSeetaFace[] tFaces1 = tSeetaFace.DetectFacesPath(file.getAbsolutePath());
        for(int i=0;i<tFaces1.length;i++){
            BufferedImage input = ImageIO.read(file);
            BufferedImage output = input.getSubimage(tFaces1[i].left,tFaces1[i].top,tFaces1[i].right-tFaces1[i].left,tFaces1[i].bottom-tFaces1[i].top);
            ImageIO.write(output,"jpg",new File("d:\\FaceNamingSystem\\output"+i+".jpg"));
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @ApiOperation(value="提取视频中人脸截图", notes="")
    @RequestMapping(value="/extract_vidoe_face", method=RequestMethod.PUT)
    public void extract_vidoe_face() {
        try {
            File file = new File("D:\\FaceNamingSystem\\heying4.jpg");
            tSeetaFace.initModelPath("C:\\Users\\Gavinggl\\Documents\\Visual Studio 2013\\Projects\\seetaface\\model");

            FFmpegFrameGrabber ff = new FFmpegFrameGrabber("D:\\FaceNamingSystem\\test.MOV");
            ff.start();
            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();//转换器
            Frame grabframe = null;
            IplImage grabbedImage =null;
            int f=1;
            while ( ( grabframe = ff.grabFrame()) != null ) {
                System.out.printf("第%d帧 \n",f);
                grabbedImage = converter.convert(grabframe);
                // 将IplImage转为Frame
                Frame convertFrame2 = converter.convert(grabbedImage);
                //将图片指针转为二进制byte[]  start
                Java2DFrameConverter java2dFrameConverter = new Java2DFrameConverter();
                BufferedImage bufferedImage= java2dFrameConverter.convert(convertFrame2);
                CMSeetaFace[] tFaces1 = tSeetaFace.DetectFacesImage(bufferedImage);
                if(tFaces1!=null){
                    for (int i = 0; i < tFaces1.length; i++) {
                        BufferedImage input = ImageIO.read(file);
                        BufferedImage output = input.getSubimage(tFaces1[i].left,tFaces1[i].top,tFaces1[i].right-tFaces1[i].left,tFaces1[i].bottom-tFaces1[i].top);
                        ImageIO.write(output,"jpg",new File("d:\\FaceNamingSystem\\face\\frame"+f+"output"+i+".jpg"));
                    }
                }
                f++;
            }
            cvReleaseImage(grabbedImage);
            ff.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value="根据图片(单张人脸)匹配数据库中人脸，返回最相似学生Id", notes="")
    @RequestMapping(value="/matching_single", method=RequestMethod.PUT)
    public String matching_single(MultipartFile pic_singleface ) throws IOException {
        File file = new File(pic_singleface.getOriginalFilename());
        tSeetaFace.initModelPath("C:\\Users\\Gavinggl\\Documents\\Visual Studio 2013\\Projects\\seetaface\\model");
        long start1 = System.currentTimeMillis();
        CMSeetaFace[] Face_goal = tSeetaFace.DetectFacesPath(file.getAbsolutePath());
        long end1 = System.currentTimeMillis();
        if(Face_goal!=null){
            System.out.println("detect face number:"+Face_goal.length);
        }
        else
            System.out.println("no face");
        System.out.println("detect time:"+(end1-start1));
       // List<Integer> sims = new ArrayList();
        List<UserEntity> users= userDao.getAll();
        long start = System.currentTimeMillis();
        float max_sim=0;
        String max_sim_id =null;
        if(Face_goal!=null){
            for (int i = 0; i < Face_goal.length; i++) {
                for(int j=0;j<users.size();j++){
                    java.util.List<CMseetaFaceEntiy> cmfaces= cmseetafaceDao.findByUserId(users.get(j).getUserID(),users.get(j).getCollectionName());
                    //for(int k=0;k<cmfaces.size();k++){
                        float tSim = tSeetaFace.CalcSimilarity(Face_goal[i].features,cmfaces.get(0).getFeatures());
                       // System.out.printf("目标图像中人脸与数据库图片（faceID:%s usrID为%s） 相似度值为%f \n",cmfaces.get(j).getFaceId(),cmfaces.get(j).getUserId(),tSim);
                        System.out.printf("目标图像中人脸与数据库图片（usrID为%s,name为%s） 相似度值为%f \n",users.get(j).getUserID(),users.get(j).getUserName(),tSim);
                        if(tSim > max_sim){
                            max_sim = tSim;
                            max_sim_id = users.get(j).getUserID();
                            //sims.add(i,Integer.parseInt(users.get(j).getUserID()));
                        }
                    //}
                }
                System.out.printf("目标图像中人脸%d与数据库(id:%s)最大相似度%f",i,max_sim_id, max_sim);
            }
        }
        float spendtime=(System.currentTimeMillis() - start)/1000;
        System.out.printf("目标图像单张人脸消耗时间：%fs",spendtime);
        return max_sim_id;
    }

    @ApiOperation(value="根据图片(多张人脸)匹配数据库中人脸，返回最相似学生Id", notes="")
    @RequestMapping(value="/matching_double", method=RequestMethod.PUT)
    public void matching_double( ) throws IOException{
       // File file = new File(pic_singleface.getOriginalFilename());
        tSeetaFace.initModelPath("C:\\Users\\Gavinggl\\Documents\\Visual Studio 2013\\Projects\\seetaface\\model");
        CMSeetaFace[] Face_goal = tSeetaFace.DetectFacesPath("D:\\FaceNamingSystem\\heying4.jpg");
        List<UserEntity> users= userDao.getAll();
        if(Face_goal!=null){
            List<Float> max_sim = new ArrayList<Float>(Face_goal.length);
            List<String> max_sim_id = new ArrayList<String>(Face_goal.length);
            List<String> max_sim_name = new ArrayList<String>(Face_goal.length);

            long start_total = System.currentTimeMillis();
            for (int i = 0; i < Face_goal.length; i++) {
                max_sim.add(i,0f);
                max_sim_id.add(i,null) ;
                max_sim_name.add(i,null) ;
                long start = System.currentTimeMillis();
                for(int j=0;j<users.size();j++){
                    java.util.List<CMseetaFaceEntiy> cmfaces= cmseetafaceDao.findByUserId(users.get(j).getUserID(),users.get(j).getCollectionName());
                    float tSim = tSeetaFace.CalcSimilarity(Face_goal[i].features,cmfaces.get(0).getFeatures());
                    //System.out.printf("目标图像中人脸与数据库图片（usrID为%s,name为%s） 相似度值为%f \n",users.get(j).getUserID(),users.get(j).getUserName(),tSim);
                    if(tSim > max_sim.get(i)){
                        max_sim.set(i,tSim) ;
                        max_sim_id.set(i,users.get(j).getUserID()) ;
                        max_sim_name.set(i,users.get(j).getUserName().trim()) ;
                    }
                }
                System.out.printf("目标图像中人脸%d与数据库(id:%s name:%s)最大相似度%f\n",i,max_sim_id.get(i),max_sim_name.get(i),max_sim.get(i));
                float spendtime=(System.currentTimeMillis() - start)/1000;
                System.out.printf("匹配图片中一个人脸消耗时间：%fs\n",spendtime);
            }
            float spendtime2=(System.currentTimeMillis() - start_total)/1000;
            System.out.printf("匹配图片中所有人脸消耗时间：%fs",spendtime2);
        }
    }


    @ApiOperation(value="根据视频流实时匹配数据库中人脸，修改数据库中出勤表信息", notes="")
    @RequestMapping(value="/matching_video", method=RequestMethod.PUT)
    public void matching_video() throws IOException {
        try{
            //File file = new File(video.getOriginalFilename());
            tSeetaFace.initModelPath("C:\\Users\\Gavinggl\\Documents\\Visual Studio 2013\\Projects\\seetaface\\model");
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber("D:\\FaceNamingSystem\\test3.MOV");
            ff.start();
            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();//转换器
            Frame grabframe = null;
            IplImage grabbedImage =null;
            int f=1;
            while ( ( grabframe = ff.grabFrame()) != null ) {
                System.out.printf("第%d帧 \n",f);
                grabbedImage = converter.convert(grabframe);
                // 将IplImage转为Frame
                Frame convertFrame2 = converter.convert(grabbedImage);
                //将图片指针转为二进制byte[]  start
                Java2DFrameConverter java2dFrameConverter = new Java2DFrameConverter();
                BufferedImage bufferedImage= java2dFrameConverter.convert(convertFrame2);
                long start1 = System.currentTimeMillis();
                CMSeetaFace[] Face_goal = tSeetaFace.DetectFacesImage(bufferedImage);
                long end1 = System.currentTimeMillis();
                if(Face_goal!=null){
                System.out.println("detect face number:"+Face_goal.length);
                }
                else
                    System.out.println("no face");
                System.out.println("detect time:"+(end1-start1));
                if(Face_goal!=null){
                    List<Float> max_sim = new ArrayList<Float>(Face_goal.length);
                    List<String> max_sim_id = new ArrayList<String>(Face_goal.length);
                    List<String> max_sim_name = new ArrayList<String>(Face_goal.length);
                    List<UserEntity> users= userDao.getAll();

                    for (int i = 0; i < Face_goal.length; i++) {
                        max_sim.add(i,0f);
                        max_sim_id.add(i,null) ;
                        max_sim_name.add(i,null);
                        long start2 = System.currentTimeMillis();
                        for(int j=0;j<users.size();j++){
                            java.util.List<CMseetaFaceEntiy> cmfaces= cmseetafaceDao.findByUserId(users.get(j).getUserID(),users.get(j).getCollectionName());
                            float tSim = tSeetaFace.CalcSimilarity(Face_goal[i].features,cmfaces.get(0).getFeatures());
                            //System.out.printf("目标图像中人脸与数据库图片（usrID为%s,name为%s） 相似度值为%f \n",users.get(j).getUserID(),users.get(j).getUserName(),tSim);
                            if(tSim > max_sim.get(i)){
                                max_sim.set(i,tSim) ;
                                max_sim_id.set(i,users.get(j).getUserID()) ;
                                max_sim_name.set(i,users.get(j).getUserName().trim()) ;
                            }
                        }
                        System.out.printf("目标图像中人脸%d与数据库(id:%s name:%s)最大相似度%f\n",i,max_sim_id.get(i),max_sim_name.get(i),max_sim.get(i));
                        long end2 = System.currentTimeMillis();
                        System.out.println("face matching number:"+users.size());
                        System.out.println("face matching time:"+(end2-start2));
                    }


                }
                f++;
            }
            cvReleaseImage(grabbedImage);
            ff.stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ApiOperation(value="根据视频流实时匹配数据库中人脸，修改数据库中出勤表信息", notes="")
    @RequestMapping(value="/extract_video", method=RequestMethod.PUT)
    public void extract_video() throws IOException {
        try{
            tSeetaFace.initModelPath("C:\\Users\\Gavinggl\\Documents\\Visual Studio 2013\\Projects\\seetaface\\model");
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber("D:\\FaceNamingSystem\\vidoe_test3.mp4");
            ff.start();
            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();//转换器
            Frame grabframe = null;
            IplImage grabbedImage =null;
            int f=1;
            while ( ( grabframe = ff.grabFrame()) != null ) {
                if(f>40){
                    grabbedImage = converter.convert(grabframe);
                    // 将IplImage转为Frame
                    Frame convertFrame2 = converter.convert(grabbedImage);
                    //将图片指针转为二进制byte[]  start
                    Java2DFrameConverter java2dFrameConverter = new Java2DFrameConverter();
                    BufferedImage bufferedImage= java2dFrameConverter.convert(convertFrame2);
                    CMSeetaFace[] Face_goal = tSeetaFace.DetectFacesImage(bufferedImage);
                    if(Face_goal!=null){
                        System.out.printf("第%d帧 提取%d个人脸\n",f,Face_goal.length);
                        for(int i=0;i<Face_goal.length;i++){
                            CMseetaFaceEntiy face=new CMseetaFaceEntiy();
                            face.setUserId("Test");
                            face.setFeatures(Face_goal[i].features);
                            cmseetafaceDao.insertIntoCollection(face,"Test");
                        }
                    }
                    else {
                        System.out.printf("第%d帧 没有人脸 \n",f);
                    }
                    f++;
                }
                else
                    f++;

            }
            cvReleaseImage(grabbedImage);
            ff.stop();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
