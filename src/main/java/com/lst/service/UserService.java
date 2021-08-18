package com.lst.service;

import com.lst.pojo.User;

/**
 * @Author: Solitude
 * @Data: 2021/8/18 13:21
 * @Description:
 */

public interface UserService {

    User checkUser(String username, String password);
}
