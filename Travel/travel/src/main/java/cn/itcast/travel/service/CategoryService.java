package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 查询首页导航条中的所有分类数据的方法
     * @return List<Category>
     */
    public List<Category> findAll();
}
