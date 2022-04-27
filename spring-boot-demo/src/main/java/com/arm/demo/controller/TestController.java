package com.arm.demo.controller;

import com.arm.demo.entity.User;
import com.arm.demo.service.UserDao;
import com.arm.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zhaolangjing
 */
@Controller
@RequestMapping("test")
public class TestController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @RequestMapping("test")
    @ResponseBody
    public String test() {
        return "hello world  ss";
    }

    @RequestMapping("saveUser")
    @ResponseBody
    public boolean saveUser(User user) {
        return userService.saveUser(user);
    }


    @RequestMapping("getUser")
    @ResponseBody
    public List<User> getUser(int age) {
        return userDao.findByAge(age);
    }


    @RequestMapping("getUserBy")
    @ResponseBody
    public List<User> getUserBy(int age) {
        return userService.getUser(age);
    }


    @RequestMapping("updateBySessionCache")
    @ResponseBody
    public void updateBySessionCache(User user1) {
        userService.updateUser(user1);
    }

    @RequestMapping("update")
    @ResponseBody
    public void update(User user1) {
        userService.updateUser2(user1);
    }

    @RequestMapping("maxAge")
    @ResponseBody
    public int maxAge() {
        int maxAge = userDao.getMaxAge();
        return maxAge;
    }


    @RequestMapping("delete")
    @ResponseBody
    public void delete(String ids) {
        userService.delete(ids);
    }
}



