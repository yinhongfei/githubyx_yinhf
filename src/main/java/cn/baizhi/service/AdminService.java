package cn.baizhi.service;

import java.util.Map;

public interface AdminService {
    Map<String,Object> login(String username, String password);
}
