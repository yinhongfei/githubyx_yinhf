package cn.baizhi.service;

import cn.baizhi.dao.AdminDao;
import cn.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{
    @Autowired
    private AdminDao ad;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> login(String username, String password) {
        //这里存储的是 登录信息
        Map<String,Object> map = new HashMap<>();
        //根据名字查询用户信息
        Admin admin = ad.queryByUserName(username);
        if(admin != null){
            //用户存在
            if(admin.getPassword().equals(password)){
                //登录成功
                map.put("flag", true);
                map.put("msg", "登录成功");
            }else {
                //密码错误
                map.put("flag", false);
                map.put("msg", "密码错误");
            }
        }else{
            //用户不存在
            map.put("flag", false);
            map.put("msg", "用户名不存在");
        }
        return map;
    }
}
