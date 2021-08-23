package cn.baizhi.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.baizhi.dao.UserDao;
import cn.baizhi.entity.User;
import cn.baizhi.util.ALiYun;
import cn.baizhi.vo.MonthAndCount;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.ResponseParsers;
import com.aliyun.oss.model.PutObjectRequest;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao ud;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryByPage(int page, int size) {
        Map<String,Object> map = new HashMap<>();
        //总页数
        //总条数
        int i = ud.queryAllCount();
        //定义总页数变量
        int pages;
        if(i%size==0){
            pages=i/size;
        }else{
            pages=(i/size)+1;
        }
        //当前页数
        List<User> list = ud.queryRange((page - 1) * size, size);
            //将分页查的数据存到map中
            map.put("data", list);
            //总页数存在map中
            map.put("pages", pages);
            //存储当前页数
            map.put("page", page);
            return map;
    }

    @Override
    public void updateStatus(String id, int status) {
        ud.updateStatus(id, status);
    }

    @Override
    public void save(MultipartFile file, User user) {
        //头像的上传
        //得到上传是文件的名字
        String fileName = file.getOriginalFilename();
        Date date = new Date();
        long time = date.getTime();
        String finalName = time+fileName;
        ALiYun.uploadByBytes(file, finalName);

        //user 对象中的数据入库
        user.setId(UUID.randomUUID().toString());
        user.setCreate_date(new Date());
        user.setHeadimg("http://yinhf.oss-cn-beijing.aliyuncs.com/"+finalName);
        user.setStatus(1);

        ud.save(user);
    }

    @Override
    public void delete(String id,String headimgurl) {
        //删除图片
        int i = headimgurl.lastIndexOf('/');
        String fileName = headimgurl.substring(i + 1);
        ALiYun.deleteFile(fileName);
        //删除用户信息
        ud.delete(id);
    }

    @Override
    public void downloadUser() {
        List<User> list = ud.queryAll();
        for (User user : list) {
            //将用户头像下载到本地
            String headimg = user.getHeadimg();
            int i = headimg.lastIndexOf('/');
            String fileName = headimg.substring(i + 1);
            ALiYun.download(fileName);

            user.setHeadimg("d:\\bb\\"+fileName);
        }

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户信息", "用户信息表"), User.class, list);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream("d:\\user.xls");
            workbook.write(stream);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                stream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryUserSexCount() {
        List<String> data = new ArrayList<>();
        //得到男生每个月注册人数
        List<Integer> manCount = new ArrayList<>();

        List<MonthAndCount> man = ud.queryMonthCount("男");
        //得到女生每个月注册人数
        List<Integer> womanCount = new ArrayList<>();
        List<MonthAndCount> woman = ud.queryMonthCount("女");


        //向 data 集合中存储 1~12 月
        for (int i=1;i<=12;i++){
            data.add(i+"月");
            //女生
            boolean flag2 = false;
            for (MonthAndCount monthAndCount : woman) {
                if(monthAndCount.getMonth() == i){
                    womanCount.add(monthAndCount.getCount());
                    flag2 = true;
                }
            }
            if(!flag2){
                womanCount.add(0);
            }

            //男生
            boolean flag = false;
            for (MonthAndCount monthAndCount : man) {
                if(monthAndCount.getMonth() == i){
                    manCount.add(monthAndCount.getCount());
                    flag = true;
                }
            }
            if(!flag){
                manCount.add(0);
            }
        }
        //存储了 月份  男生人数 女生人数
        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("manCount", manCount);
        map.put("womanCount", womanCount);

        return map;
    }
}
