package com.sp.store.service;

import com.sp.store.entity.User;
import com.sp.store.mapper.UserMapper;
import com.sp.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author sp
 * @date: 2022.09.28 12:47
 */
//@SpringBootTest: 表示标注当前的类是一个测试类, 不会随同项目一块打包
//@RunWith: 表示启动这个单元测试类(单元测试类时不能够运行的), 需要传递一个参数, 必须是 SpringRunner的实例类型
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {
    /*直接写这个Mapper属性, 自动装配的时候, 会报红, 出现以下提示:
      Could not autowire. No beans of 'UserMapper' type found.
      idea有检测的功能, 接口是不能够直接创建Bean的,
      MyBatis创建了这个接口的动态代理实现类, 来完成了对象的创建
      可以在Settings -> Editor -> Inspections 中找到 Spring -> SpringCore -> Code -> Autowiring for bean class 将其提示等级从Error改低到Warning就行了
    */
    @Autowired
    private IUserService userService;
    /**
     * 单元测试方法: 可以单独独立运行, 不用启动整个项目, 可以左单元测试, 提升了代码的测试效率
     * 1. 必须被@Test注解修饰
     * 2. 返回值类型必须是void
     * 3. 方法的参数列表不指定任何类型
     * 4. 方法的访问修饰符必须是public
     */
    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("sp02");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("Ok");
        } catch (ServiceException e) {
            //获取类的对象, 再获取类的名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login() {
        User user = userService.login("test01", "123");
        System.out.println(user);

    }

    @Test
    public void changePassword() {
        userService.changePassword(6, "管理员", "123", "321");
    }


    @Test
    public void getByUid() {
        try {
            Integer uid = 9;
            User user = userService.getByUid(uid);
            System.out.println(user);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void changeInfo() {
        try {
            Integer uid = 9;
            String username = "管理员";
            User user = new User();
            user.setPhone("13588888888");
            user.setEmail("admin@qq.com");
            user.setGender(1);
            userService.changeInfo(uid, username, user);
            System.out.println(user);
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void changeAvatar() {
        userService.changeAvatar(2, "/upload/test.png", "管理员");
    }
}
