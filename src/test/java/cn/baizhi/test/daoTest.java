package cn.baizhi.test;

import cn.baizhi.dao.AdminDao;
import cn.baizhi.dao.UserDao;
import cn.baizhi.entity.Admin;
import cn.baizhi.entity.User;
import cn.baizhi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class daoTest {

    @Autowired
    private AdminDao adminDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService us;

    @Test
    public void test1(){
        System.out.println(adminDao);
    }

    @Test
    public void adminDaoTest(){
        Admin admin = adminDao.queryByUserName("2101");
        System.out.println(admin);
    }
    @Test
    public void test4(){
        List<User> users = userDao.queryRange(1, 2);
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println(userDao);
    }
    @Test
    public void test5(){
        int i = userDao.queryAllCount();
        System.out.println(i);

    }
}
