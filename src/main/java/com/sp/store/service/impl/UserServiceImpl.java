package com.sp.store.service.impl;

import com.sp.store.entity.User;
import com.sp.store.mapper.UserMapper;
import com.sp.store.service.IUserService;
import com.sp.store.service.ex.*;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**
 * 用户模块业务层的实现类
 * @author sp
 * @date: 2022.09.28 14:16
 */
@Service //@Service注解: 将当前类的对象交给Spring来管理, 自动创建对象以及对象的维护
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        //通过user参数来获取传递过来的username
        String username = user.getUsername();
        //调用findByUsername(username)判断用户的是否被注册过
        User result = userMapper.findByUsername(username);
        //判断结果集是否部位null, 则抛出用户名被占用的异常
        if (result != null) {
            //如果用户名已经被占用, 则抛出UsernameDuplicateException异常
            throw new UsernameDuplicatedException("尝试注册的用户名[" + username +"]已经被占用");
        }

        //创建当前的时间对象
        Date date = new Date();
        //补全数据: 加密后的密码
        String salt = UUID.randomUUID().toString().toUpperCase();
        //将密码和盐值作为一个整体进行加密处理, 忽略了原来密码的强度, 提升了数据的安全性
        String md5Password = getMD5Password(user.getPassword(), salt);
        //将加密之后的密码重新补全设置到user对象中
        user.setPassword(md5Password);
        //补全数据: 盐值
        user.setSalt(salt);
        //补全数据: is_delete设置为0
        user.setIsDelete(0);
        //补全数据: 4个日志字段信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        //执行注册业务功能的实现(rows == 1 时执行成功)
        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new InsertException("在用户注册过程中产生了未知的异常");
        }
    }

    /**
     *
     * @param username 用户名
     * @param password 用户密码
     * @return
     */
    @Override
    public User login(String username, String password) {
        //根据用户名来查询用户的数据是或否存在, 如果不存在则抛出异常
        User result = userMapper.findByUsername(username);
        if (result == null) {
            throw new UserNotFoundException("用户数据不存在");
        }
        //检测用户的密码是否匹配
        //1. 先获取到数据库中的加密之后的密码
        String oldPassword = result.getPassword();
        //2. 和用户传递过来的额密码进行比较
        //2.1 获取盐值
        String salt = result.getSalt();
        //2.2 将用户的密码按照相同的md5算法规则进行加密
        String newMd5Password = getMD5Password(password, salt);
        //3. 将密码进行比较
        if (!newMd5Password.equals(oldPassword)) {
            throw new PasswordNotMatchException("密码错误");
        }

        //判断is_delete 字段的值是否为1, 表示被标记为删除
        if (result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        //用户信息脱敏操作, 返回的信息少, 提升了系统性能和安全性
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        //将用户的信息返回
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        //原始密码和数据库中的密码进行比较
        String oldMD5Password = getMD5Password(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldMD5Password)) {
            throw new PasswordNotMatchException("密码错误");
        }
        //将新密码设置到数据库中, 将新的密码进行加密再去更新
        String newMD5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMD5Password, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据产生未知的异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        //判断查询结果是否为null
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        //判断查询结果中的isDelete是否为1
        if (result.getIsDelete().equals(1)) {
            throw new UserNotFoundException("用户数据不存在");
        }
        //创建新的用户对象
        User user = new User();
        //将查询结果中的username/phone/email/gender封装到新的User对象中
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        //返回新的User对象
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        //判断查询结果是否为null
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        //判断查询结果中的isDelete是否为1
        if (result.getIsDelete().equals(1)) {
            throw new UserNotFoundException("用户数据不存在");
        }

        //向参数user中补全数据: uid
        user.setUid(uid);
        //向参数user中补全数据: modifiedUser(username)
        user.setModifiedUser(username);
        //向参数user中补全数据: modifiedTime(new Date())
        user.setModifiedTime(new Date());
        //调用userMapper的updateInfoByUid(User user)方法执行修改, 并获取返回值
        Integer rows = userMapper.updateInfoByUid(user);
        //判断以上返回的受影响的行数不为1
        if (rows != 1) {
            //是 则 抛出UpdateException异常
            throw new UpdateException("更新用户数据时出现未知错误, 请联系系统管理员");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        //查询当前的用户数据是否存在
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete().equals(1)) {
            throw new UserNotFoundException("用户数据不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新用户头像产生未知的异常");
        }
    }

    /**
     * 密码加密函数
     * @param password 原始密码
     * @param salt 盐值
     * @return 加密后的密文
     */
    private String getMD5Password(String password, String salt) {
        for (int i = 0; i < 3; i++) {
            /*
             * 加密规则：
             * 1、无视原始密码的强度
             * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
             * 3、MD5加密算法的调用(循环加密三次)
             */
            password = DigestUtils.md5DigestAsHex((salt + password +salt).getBytes()).toUpperCase();
        }
        //返回加密之后的密码
        return password;
    }
}
