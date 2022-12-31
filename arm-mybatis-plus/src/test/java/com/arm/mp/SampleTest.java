package com.arm.mp;

import com.arm.mp.mapper.User;
import com.arm.mp.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assertions.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }


    /**
     * 使用 非table字段的实体类装载数据
     */
    @Test
    public void testCount() {
        QueryWrapper<User> age = Wrappers.query(new User()).select("count(*) as number,age").groupBy("age");
        List<User> userList = userMapper.selectList(age);
        userList.forEach(System.out::println);
    }
}
