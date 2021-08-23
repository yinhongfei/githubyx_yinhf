package cn.baizhi.controller;

import cn.baizhi.entity.User;
import cn.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService us;

    @RequestMapping("/queryByPage")
    public Map<String, Object> queryByPage(int page){
        int size = 3;
        return us.queryByPage(page, size);
    }

    @RequestMapping("/updateStatus")
    public void updateStatus(String id,int status){
        us.updateStatus(id, status);
    }

    @RequestMapping("/add")
    public void add(MultipartFile photo,String username,String phone,String brief){
        System.out.println(photo.getOriginalFilename());
        System.out.println(username);

        User user = new User(null, username, phone, null, brief, null, null, null);
        us.save(photo, user);
    }
    @RequestMapping("/delete")
    public void delete(String id,String headimgurl) {
        us.delete(id,headimgurl);
    }

    @RequestMapping("/download")
    public void download(){
        us.downloadUser();
    }

    @RequestMapping("/registCount")
    public Map<String,Object> registCount(){
        //System.out.println("执行了");
        return us.queryUserSexCount();
    }
}
