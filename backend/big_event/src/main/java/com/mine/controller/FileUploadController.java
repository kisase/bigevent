package com.mine.controller;

import com.mine.entity.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@CrossOrigin //跨域
public class FileUploadController {

    // 或者使用@Value方式注入
    @Value("${server.port}")
    private int port;

    @ResponseBody
    @PostMapping("/upload")
    //文件上传步骤
    //1.用MultipartFile 类型来接收前端发送的文件
    public Result<String> upload(MultipartFile file) throws IOException {
        //获取到文件的全名
        String filename = file.getOriginalFilename();
        //给文件一个全新的名字
        //String newfilename = UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));
        String newfilename = System.currentTimeMillis()+filename.substring(filename.lastIndexOf("."));
        String path = "D:\\person\\images\\";
        //把这个目录文件创建出来,ps:普通文件是文件，目录也是文件.注意这里只是创建出来个File java类对象.此时还没有目录
        File Nfile = new File(path);
        //判断这个目录文件是否存在,不存在就创建出来.此时这个目录就出现了
        if (!Nfile.exists()) {
            Nfile.mkdirs();
        }
        //设置文件要存储的目录
        file.transferTo(new File(path + newfilename));
        String uploadPath = "http://localhost:" +port+ "/uploads/images/" + newfilename; // 文件上传后的访问网址
        return Result.success(uploadPath);
    }
//    public String upload(MultipartFile file){
//        String filename = file.getOriginalFilename();
//        String newname = System.currentTimeMillis()+filename.substring(filename.lastIndexOf("."));
//        String path = "C:\\Users\\Administrator\\Pictures\\Saved Pictures\\";
//        File newpath = new File(path);
//        if (!newpath.exists()) {
//            newpath.mkdirs();
//        }
//        try{
//            File newfile = new File(newpath,newname);//路径
//            file.transferTo(newfile);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println(path+newname);
//        return path+newname;
//    }
}
