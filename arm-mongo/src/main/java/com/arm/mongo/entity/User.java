package com.arm.mongo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author z-ewa
 */
@Data
public class User implements Serializable {
    private String id;

    private String name;

    private int age;

    private List<Curriculum> curriculums;


    public static User newZhangSan() {
        User user = new User();
        user.setAge(18);
        user.setName("张三");
        ArrayList<Curriculum> curriculums = new ArrayList<>();
        curriculums.add(new Curriculum("123", "语文", 12.2, 101));
        curriculums.add(new Curriculum("1234", "数学", 12.9, 100));
        user.setCurriculums(curriculums);
        return user;
    }


    public static User newLiSi() {
        User user = new User();
        user.setAge(20);
        user.setName("李四");
        ArrayList<Curriculum> curriculums = new ArrayList<>();
        curriculums.add(new Curriculum("12345", "英语", 19.9, 1000));
        curriculums.add(new Curriculum("1234", "数学", 12.9, 100));
        user.setCurriculums(curriculums);
        return user;
    }
}
