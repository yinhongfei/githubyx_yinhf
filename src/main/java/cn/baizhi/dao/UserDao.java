package cn.baizhi.dao;

import cn.baizhi.entity.User;
import cn.baizhi.vo.MonthAndCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    //查询所有条数
    int queryAllCount();
    //范围查询
    List<User> queryRange(@Param("start") int start,@Param("end") int end);
    //修改状态
    void updateStatus(@Param("id") String id,@Param("status") int status);
    //添加
    void save(User user);
    //删除
    void delete(String id);
    //查所有
    List<User> queryAll();
    //根据性别查询每月注册人数
    List<MonthAndCount> queryMonthCount(String sex);
}
