package com.sp.store.controller;

import com.sp.store.controller.ex.*;
import com.sp.store.entity.User;
import com.sp.store.service.IUserService;
import com.sp.store.service.ex.InsertException;
import com.sp.store.service.ex.UsernameDuplicatedException;
import com.sp.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.security.util.Password;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * @author sp
 * @date: 2022.09.28 15:42
 */
//@Controller
@RestController //@Controller + @ResponseBody
@RequestMapping("users")
public class UserController extends BaseController{
    @Autowired
    private IUserService userService;



    /*
    @RequestMapping("reg")
    //@ResponseBody 表示此方法的响应结果以Json格式数据响应到前端
    public JsonResult<Void> reg(User user) {
        //创建相应结果对象
        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("用户注册成功");
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        } catch (InsertException e) {
            result.setState(5000);
            result.setMessage("注册时产生位置的异常");
        }
        return result;
    }
    */


    /**
     * 约定大于配置: 开发思想来完成, 省略大量的配置甚至注解的编写
     * 1. 接收数据方式: 请求处理方法的参数列表设置为pojo类型来接收前端的数据
     *  SpringBoot会将前端的url地址中的参数名和pojo类的属性进行比较, 如果这两个名称相同,
     *  则将插入到pojo类中对应的属性上
     * @param user
     * @return
     */
    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<>(OK);
    }

    /**
     * 2. 接收数据方式: 请求处理方法的参数列表设置为非pojo类型
     * SpringBoot会直接将请求的参数名和方法的参数名直接进行比较, 如果名称相同则自动完成依赖的注入
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("login")
    public JsonResult<User>login(String username, String password, HttpSession session) {
        User data = userService.login(username, password);
        //向session对象中完成数据的绑定(session全局)
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
        //获取session中的绑定的数据
        System.out.println(getUidFromSession(session));
        System.out.println(getUsernameFromSession(session));
        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username, oldPassword, newPassword);
        return new JsonResult<>(OK);
    }

    @GetMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        //从HttpSession对象中获取uid
        Integer uid = getUidFromSession(session);
        //调用方法查询对应的用户数据
        User data = userService.getByUid(uid);
        //响应成功 还有 数据
        return new JsonResult<User>(OK, data);
    }

    @RequestMapping("change_info")
    public JsonResult<Void> changeInfo(User user, HttpSession session) {
        //从HttpSession对象中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        //调用业务对象执行修改用户资料
        userService.changeInfo(uid, username, user);
        //响应成功
        return new JsonResult<Void>(OK);

    }

    /**
     * 设置上传文件的最大值
     */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;

    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bpm");
        AVATAR_TYPE.add("image/gif");
    }

    /**
     * MultipartFile接口是SpringMVC提供的一个接口, 这个接口为我们包装了获取文件类型的数据(任何类型的file都可以接收),
     * SpringBoot整合了SpringMVC, 只需要在处理请求的方法参数列表上声明一个参数类型为MultipartFile的参数,
     * 然后SpringBoot会自动将传递给服务的文件数据赋值给这个参数.
     * @RequestParam 表示请求中的参数, 将请求中的参数注入请求处理方法的某个参数上, 如果名称不一致则可以使用@RequestParam注解进行标记和映射
     * @param session
     * @param file
     * @return
     */
    @RequestMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                           MultipartFile file) {
        //判断文件是否为空
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件超出限制");
        }
        //判断文件的类型是否是我们规定的后缀类型
        String contentType = file.getContentType();
        //如果集合包含某个元素则返回值为true
        if (!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件类型不支持");
        }
        //上传的文件.../upload/文件.png
        String parent = session.getServletContext().getRealPath("upload");
        //File对象指向这个路径, File是否存在
        File dir = new File(parent);
        if (!dir.exists()) {//检测目录是否存在
            dir.mkdirs();//创建当前的目录
        }
        //获取到这个文件名称, 使用UUID工具来生成一个新的字符串作为文件名, 防止重名
        String originalFilename = file.getOriginalFilename();
        System.out.println("OriginalFilename = " + originalFilename);
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);//获取文件的后缀(例如png)
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;
        File dest = new File(dir, filename);//此文件目前是空文件
        //参数file中数据写入到这个空文件中
        try {
            file.transferTo(dest);//将file文件中的数据写入到dest文件中
        } catch (FileStateException e){
          throw new FileStateException("文件状态异常");
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        //返回头像的路径/upload/test.png
        String avatar = "/upload/" + filename;
        userService.changeAvatar(uid, avatar, username);
        //返回用户头像的路径给前端页面, 将来用于头像展示使用
        return new JsonResult<>(OK, avatar);
    }
}
