package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    /**
     * 查询首页导航条中的所有分类数据的方法
     * @return List<Category>
     */
    List<Category> findAllCategory();
}
