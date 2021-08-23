package com.lst.dao;

import com.lst.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Solitude
 * @Data: 2021/8/19 20:06
 * @Description:
 */
public interface TagRepository extends JpaRepository<Tag,Long> {


    Tag findByName(String name);

}
