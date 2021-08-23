package com.lst.dao;

import com.lst.pojo.Type;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Solitude
 * @Data: 2021/8/18 17:09
 * @Description:
 */
public interface TypeRepository extends JpaRepository<Type,Long> {


    Type findByName(String name);
}
