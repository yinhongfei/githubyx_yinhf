package cn.baizhi.service;

import cn.baizhi.config.ALiYunConfig;
import cn.baizhi.dao.VideoDao;
import cn.baizhi.entity.Video;
import cn.baizhi.util.ALiYun;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.*;

@Service
@Transactional
public class VideoServiceImpl implements VideoService{
    @Autowired
    private VideoDao vd;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryByPage(int page, int size) {
        Map<String,Object> map = new HashMap<>();

        List<Video> list = vd.queryByPage((page - 1) * size, size);
        //总页数
        //总条数
        int i = vd.queryAllCount();
        //定义总页数变量
        int pages;
        if(i%size==0){
            pages=i/size;
        }else{
            pages=(i/size)+1;
        }
        //当前页数

        //将分页查的数据存到map中
        map.put("data", list);
        //总页数存在map中
        map.put("pages", pages);
        //存储当前页数
        map.put("page", page);
        return map;
    }

    @Override
    public void save(MultipartFile file, Video video1) {
        System.out.println(file);
        String fileName = file.getOriginalFilename();
        Date date = new Date();
        long time = date.getTime();
        String finalName = time+fileName;
        ALiYun.uploadByBytes(file, "video/"+finalName);

        String endpoint = ALiYunConfig.ENDPOINT;
        String accessKeyId = ALiYunConfig.ACCESS_KEY_ID;
        String accessKeySecret = ALiYunConfig.ACCESS_KEY_SECRET;
        String bucketName="yinhf";
        String objectName="video/"+finalName;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String style="video/snapshot,t_5000,f_jpg,w_800,h_600";
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10);
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);
        InputStream inputStream = null;
        try {
            inputStream = new URL(signedUrl.toString()).openStream();
        }catch (Exception e){
            e.printStackTrace();
        }
        String[] split = finalName.split("\\.");

        ossClient.putObject("yinhf", "video/" + split[0] + ".jpg", inputStream);

        ossClient.shutdown();

        video1.setId(UUID.randomUUID().toString());
        video1.setCoverPath("http://yinhf.oss-cn-beijing.aliyuncs.com/video/"+"video/" + split[0] + ".jpg");
        String videoPath = "http://yinhf.oss-cn-beijing.aliyuncs.com/video/"+finalName;
        video1.setVideoPath(videoPath);
        video1.setCreateDate(new Date());
        vd.save(video1);
    }

    @Override
    public void delete(String id) {
        Video video = vd.queryById(id);
        String coverPath = video.getCoverPath();
        String[] split = coverPath.split("//");
        ALiYun.deleteFile("video/"+split[split.length-1]);
        String videoPath = video.getVideoPath();
        String[] split1 = videoPath.split("//");
        ALiYun.deleteFile("video/"+split1[split1.length-1]);
        vd.deleteById(id);
    }

}
