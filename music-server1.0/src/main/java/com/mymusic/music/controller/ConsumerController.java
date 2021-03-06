package com.mymusic.music.controller;

import com.alibaba.fastjson.JSONObject;
import com.mymusic.music.domain.Consumer;
import com.mymusic.music.service.ConsumerService;
import com.mymusic.music.utils.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName ConsumerController
 * @Description TODO
 * @Author 86183
 * @Date2021-07-2914:28
 * @Version 1.0
 **/
@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    @Autowired
    ConsumerService consumerService;

    @GetMapping("/getSingleUser")
    public Consumer getSingleUser(@RequestParam("userId") Integer userId){
        return consumerService.selectByPrimaryKey(userId);
    }
    @GetMapping("/updateUser")
    public Boolean updateUser(@RequestBody Consumer consumer){
        return consumerService.updateConsumer(consumer);
    }
    @PostMapping("/add")
    public Object addConsumer(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String username =  request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        Byte sex =Byte.parseByte( request.getParameter("sex").trim());
        System.out.println("123");
        System.out.println(sex);
        System.out.println(request.getParameter("sex"));
        String phoneNum = request.getParameter("phoneNum").trim();
        String email = request.getParameter("email").trim();
        String location = request.getParameter("location").trim();
        String introduction = request.getParameter("introduction").trim();
        String avator = "/img/avatorImg/hhh.jpg";
        String birth = request.getParameter("birth").trim();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Consumer consumer = new Consumer();
        consumer.setAvator(avator);
        consumer.setSex(sex);
        consumer.setBirth(birthDate);
        consumer.setEmail(email);
        consumer.setIntroduction(introduction);
        consumer.setPassword(password);
        consumer.setPhoneNum(phoneNum);
        consumer.setUsername(username);
        consumer.setLocation(location);
        Boolean flag = consumerService.addConsumer(consumer);
        if(flag==true){
            jsonObject.put(Consts.CODE,1 );
            jsonObject.put(Consts.MSG,"????????????" );
        }else {
            jsonObject.put(Consts.CODE,0 );
            jsonObject.put(Consts.MSG,"????????????" );
        }
        return jsonObject;
    }
    @PostMapping("/update")
    public Object updateConsumer(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Integer id = Integer.parseInt(request.getParameter("id"));
        String username =  request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        Byte sex =Byte.parseByte( request.getParameter("sex").trim());
        String phoneNum = request.getParameter("phoneNum").trim();
        String email = request.getParameter("email").trim();
        String birth = request.getParameter("birth").trim();
        String location = request.getParameter("location");
        String introduction = request.getParameter("introduction");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = new Date();
        try {
            birthDate = dateFormat.parse(birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Consumer consumer = new Consumer();
        consumer.setId(id);
        consumer.setSex(sex);
        consumer.setBirth(birthDate);
        consumer.setEmail(email);
        consumer.setIntroduction(introduction);
        consumer.setPassword(password);
        consumer.setPhoneNum(phoneNum);
        consumer.setUsername(username);
        consumer.setLocation(location);
        Boolean flag = consumerService.updateConsumer(consumer);;
        if(flag==true){
            jsonObject.put(Consts.CODE,1 );
            jsonObject.put(Consts.MSG,"????????????" );
        }else {
            jsonObject.put(Consts.CODE,0 );
            jsonObject.put(Consts.MSG,"????????????" );
        }
        return jsonObject;
    }
    @GetMapping("/delete")
    public Object deleteConsumerById(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        Boolean flag = consumerService.deleteConsumerById(Integer.parseInt(request.getParameter("id")));
        if(flag==true){
            jsonObject.put(Consts.CODE,1 );
            jsonObject.put(Consts.MSG,"????????????" );
        }else {
            jsonObject.put(Consts.CODE,0 );
            jsonObject.put(Consts.MSG,"????????????" );
        }
        return jsonObject;
    }

    @GetMapping("/detail")
    public Object getAllData(HttpServletRequest request){
        return consumerService.getAllConsumer();
    }


    /**
     * ??????????????????
     */
    @RequestMapping(value = "updateConsumerPic",method = RequestMethod.POST)
    public Object updateConsumerPic(@RequestParam("file") MultipartFile avatorFile, @RequestParam("id")Integer id){
        JSONObject jsonObject = new JSONObject();
        //????????????????????????
        if(avatorFile.isEmpty()){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"????????????");
        }
        String fileName = System.currentTimeMillis()+avatorFile.getOriginalFilename();
        //????????????
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+System.getProperty("file.separator")+"img"+System.getProperty("file.separator")+"avatorImg";
        //?????????????????????????????????????????????
        File file1  = new File(filePath);
        if(!file1.exists()){
            file1.mkdir();
        }
        File dest = new File(filePath+System.getProperty("file.separator")+fileName);
        //??????????????????????????????????????????
        String storeAvatorPath = "/img/avatorImg/"+fileName;
        try {
            avatorFile.transferTo(dest);
            Consumer consumer = new Consumer();
            consumer.setAvator(storeAvatorPath);
            consumer.setId(id);
            boolean flag = consumerService.updateConsumer(consumer);
            if(flag){
                jsonObject.put(Consts.CODE,1);
                jsonObject.put(Consts.MSG,"????????????");
                jsonObject.put("avator",storeAvatorPath);
                return jsonObject;
            }
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"????????????");
            return jsonObject;
        } catch (IOException e) {
            jsonObject.put(Consts.CODE,0 );
            jsonObject.put(Consts.MSG,"????????????" );
            return jsonObject;
        }
        finally {
            return jsonObject;
        }
    }



    /**
     * ??????????????????????????????
     */
    @RequestMapping(value = "/selectByPrimaryKey",method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request){
        String id = request.getParameter("id").trim();          //??????
        return consumerService.selectByPrimaryKey(Integer.parseInt(id));
    }
    /**
     * ??????????????????
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Object login(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String username = request.getParameter("username").trim();     //??????
        String password = request.getParameter("password").trim();     //??????
        if(username==null||username.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"?????????????????????");
            return jsonObject;
        }
        if(password==null||password.equals("")){
            jsonObject.put(Consts.CODE,0);
            jsonObject.put(Consts.MSG,"??????????????????");
            return jsonObject;
        }

        //?????????????????????????????????
        Consumer consumer = new Consumer();
        consumer.setUsername(username);
        consumer.setPassword(password);
        boolean flag = consumerService.verifyPassword(username,password);
        if(flag){   //????????????
            jsonObject.put(Consts.CODE,1);
            jsonObject.put(Consts.MSG,"????????????");
            jsonObject.put("userMsg",consumerService.getByUsername(username));
            return jsonObject;
        }
        jsonObject.put(Consts.CODE,0);
        jsonObject.put(Consts.MSG,"????????????????????????");
        return jsonObject;
    }


}
