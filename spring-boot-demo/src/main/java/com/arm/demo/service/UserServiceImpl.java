package com.arm.demo.service;

import com.arm.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhaolangjing
 */
@Service

public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public boolean saveUser(User user) {
        userDao.save(user);
        User user1 = new User();
        user1.setId("11");
        user1.setUserName("zy");
        userDao.save(user1);
        return true;
    }


    @Override
    public boolean updateUser(User user1) {
        List<User> all = userDao.findAll();
        for (User user : all) {
            user.setAge(20);
        }
        //all.add(user1);
        userDao.saveAll(all);
        return true;
    }


    @Override
    public boolean updateUser2(User user1) {
        userDao.save(user1);
        return true;
    }

    @Override
    public List<User> getUser(int age) {
        List<User> byAge = userDao.findByAge(age);
        return byAge;
    }

    @Override
    public void delete(String ids) {
        userDao.deleteByIdIn(Arrays.asList(ids.split(",")));
    }


}
