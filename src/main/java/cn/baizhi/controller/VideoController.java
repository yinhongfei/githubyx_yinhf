package cn.baizhi.controller;

import cn.baizhi.entity.Category;
import cn.baizhi.entity.Video;
import cn.baizhi.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/video")
@CrossOrigin
public class VideoController {
    @Autowired
    private VideoService vs;

    @RequestMapping("/queryByPage")
    public Map<String, Object> queryByPage(int page){
        int size = 2;
        return vs.queryByPage(page, size);
    }

    @RequestMapping("/add")
    public void add(MultipartFile video,String title,String brief,String id){

//        Video video1 = new Video();
//        Category category = new Category();
//        category.setId(id);
//        video1.setCategory(category);
        Video video1 = new Video(null,title,brief,null,null,null,new Category(id, null, null, null),null,null);
        vs.save(video, video1);

    }
    @RequestMapping("/delete")
    public void delete(String id){
        vs.delete(id);
    }
}
