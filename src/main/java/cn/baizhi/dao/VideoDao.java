package cn.baizhi.dao;

import cn.baizhi.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoDao {

    //查询所有条数
    int queryAllCount();
    //分页查
    List<Video> queryByPage(@Param("start") int start,@Param("end") int end);
    //添加
    void save(Video video);
    //根据id查一个
    Video queryById(String id);
    //根据Id删除
    void deleteById(String id);
}
