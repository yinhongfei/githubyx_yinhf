package cn.baizhi.dao;

import cn.baizhi.entity.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao {
    Admin queryByUserName(String username);
}
