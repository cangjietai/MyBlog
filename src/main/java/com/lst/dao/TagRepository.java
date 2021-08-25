package com.lst.dao;

import com.lst.pojo.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Solitude
 * @Data: 2021/8/19 20:06
 * @Description:
 */
public interface TagRepository extends JpaRepository<Tag,Long> {


    Tag findByName(String name);

    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
