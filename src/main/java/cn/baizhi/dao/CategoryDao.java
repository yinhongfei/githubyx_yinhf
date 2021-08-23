package cn.baizhi.dao;

import cn.baizhi.entity.Category;

import java.util.List;

public interface CategoryDao {
    //根据级别 查询类别
    List<Category> queryByLevels(int levels);

    //根据 父项 id 查询所有的二级类别
    List<Category> queryByParentId(String id);

    //根据父项添加二级类别
    void save(Category category);
    //根据id删除类别
    void delete(String id);
}
