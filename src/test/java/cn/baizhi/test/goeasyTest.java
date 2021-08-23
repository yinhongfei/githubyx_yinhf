package cn.baizhi.test;

import io.goeasy.GoEasy;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class goeasyTest {
    //goeasy  向频道中发消息
    @Test
    public void test(){
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-48c84eb5810d4f20b0ab85b8b9a10df0");
        goEasy.publish("my_channel", "hello,GoEasy!");
    }
}
