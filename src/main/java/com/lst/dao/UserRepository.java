package com.lst.dao;

import com.lst.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Solitude
 * @Data: 2021/8/18 13:23
 * @Description:
 */

public interface UserRepository extends JpaRepository<User,Long> {

        User findByUsernameAndPassword(String username, String password);
}
