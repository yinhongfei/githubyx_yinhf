package cn.baizhi.service;

import cn.baizhi.dao.CategoryDao;
import cn.baizhi.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao cd;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryByLevels(int levels) {
        return cd.queryByLevels(levels);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryByParentId(String id) {
        return cd.queryByParentId(id);
    }

    @Override
    public void save(Category category) {
        category.setId(UUID.randomUUID().toString());
        cd.save(category);
    }

    @Override
    public void delete(String id) {
        cd.delete(id);
    }
}
