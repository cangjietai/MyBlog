package com.lst.vo;

/**
 * @Author: Solitude
 * @Data: 2021/8/22 12:35
 * @Description:
 * 将BlogList要查询的参数：
 * 标题，分类，推荐
 * 封装成一个单独的查询对象
 */
public class BlogQuery {

    private String title;
    private Long typeId;
    private boolean recommend;

    public BlogQuery() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
}
