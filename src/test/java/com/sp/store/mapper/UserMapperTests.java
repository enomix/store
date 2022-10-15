package com.sp.store.mapper;

import com.sp.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author sp
 * @date: 2022.09.28 12:47
 */
//@SpringBootTest: 表示标注当前的类是一个测试类, 不会随同项目一块打包
//@RunWith: 表示启动这个单元测试类(单元测试类时不能够运行的), 需要传递一个参数, 必须是 SpringRunner的实例类型
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTests {
    /*直接写这个Mapper属性, 自动装配的时候, 会报红, 出现以下提示:
      Could not autowire. No beans of 'UserMapper' type found.
      idea有检测的功能, 接口是不能够直接创建Bean的,
      MyBatis创建了这个接口的动态代理实现类, 来完成了对象的创建
      可以在Settings -> Editor -> Inspections 中找到 Spring -> SpringCore -> Code -> Autowiring for bean class 将其提示等级从Error改低到Warning就行了
    */
    @Autowired
    private UserMapper userMapper;
    /**
     * 单元测试方法: 可以单独独立运行, 不用启动整个项目, 可以左单元测试, 提升了代码的测试效率
     * 1. 必须被@Test注解修饰
     * 2. 返回值类型必须是void
     * 3. 方法的参数列表不指定任何类型
     * 4. 方法的访问修饰符必须是public
     */
    @Test
    public void insert() {
        User user = new User();
        user.setUsername("tim");
        user.setPassword("123");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findUserByName() {
        User user1 = userMapper.findByUsername("tim");
        System.out.println(user1);
    }



    @Test
    public void updatePasswordByUid(){
        userMapper.updatePasswordByUid(5, "321", "管理员", new Date());
    }

    @Test
    public void  findByUid(){
        System.out.println(userMapper.findByUid(5));
    }

    @Test
    public void updateInfoByUid() {
        User user = new User();
        user.setUid(9);
        user.setPhone("13788888888");
        user.setEmail("123456@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }

    @Test
    public void updateAvatarByUid() {
        userMapper.updateAvatarByUid(9, "/upload/avatar1.png", "管理员1", new Date());
    }
}
