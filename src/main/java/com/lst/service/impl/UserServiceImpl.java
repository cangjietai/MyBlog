package com.lst.service.impl;

import com.lst.dao.UserRepository;
import com.lst.pojo.User;
import com.lst.service.UserService;
import com.lst.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Solitude
 * @Data: 2021/8/18 13:22
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    public User checkUser(String username, String password){
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
