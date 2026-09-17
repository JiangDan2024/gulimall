package com.gulimall.thirdparty;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class GulimallThirdPartyApplicationTests {

    @Test
    void contextLoads() {
    }


    @Autowired
    private OSS ossClient;

    @Test
    public void uploadFile() {
        String objectName = "exampledir/fe215589ed6500f4.jpg";
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
        String filePath= "D:\\project\\fe215589ed6500f4.jpg";
        // 使用 putObject 进行上传
        ossClient.putObject(new PutObjectRequest("jd-gulimall", objectName, new File(filePath)));
        System.out.println("上传成功");
    }

}
