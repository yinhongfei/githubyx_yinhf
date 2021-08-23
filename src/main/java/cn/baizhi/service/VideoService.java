package cn.baizhi.service;

import cn.baizhi.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface VideoService {
    //分页查询业务
    Map<String,Object> queryByPage(int page, int size);
    //添加业务
    void save(MultipartFile file, Video video);
    //删除视频业务
    void delete(String id);
}
