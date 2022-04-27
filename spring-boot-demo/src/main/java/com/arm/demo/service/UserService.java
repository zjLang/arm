package com.arm.demo.service;

import com.arm.demo.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhaolangjing
 */
public interface UserService {
    @Transactional(rollbackFor = Exception.class)
    boolean saveUser(User user);

    @Transactional(rollbackFor = Exception.class)
    boolean updateUser(User user1);

    @Transactional(rollbackFor = Exception.class)
    boolean updateUser2(User user1);

    List<User> getUser(int age);

    @Transactional(rollbackFor = Exception.class)
    void delete(String ids);

}
