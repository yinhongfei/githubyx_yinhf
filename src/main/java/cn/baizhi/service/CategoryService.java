package cn.baizhi.service;

import cn.baizhi.entity.Category;

import java.util.List;

public interface CategoryService {
    //根据级别  查  类别  的业务
    List<Category> queryByLevels(int levels);

    //根据父项 id查询二级类别
    List<Category> queryByParentId(String id);
    //添加类别
    void save(Category category);
    //根据id删除类别
    void delete(String id);

}
