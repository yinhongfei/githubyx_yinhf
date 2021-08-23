package cn.baizhi.service;

import cn.baizhi.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UserService {

    //分页查询业务
    Map<String,Object> queryByPage(int page,int size);
    //修改状态的业务
    void updateStatus(String id,int status);
    //添加业务
    void save(MultipartFile file, User user);
    //删除 用户 业务 并删除头像
    void delete(String id,String headimgurl);
    //下载用户信息的业务
    void downloadUser();
    //提供用户分析数据业务
    Map<String, Object> queryUserSexCount();
}
