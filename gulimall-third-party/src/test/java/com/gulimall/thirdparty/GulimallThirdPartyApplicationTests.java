package com.gulimall.thirdparty;

import com.alibaba.nacos.common.http.HttpUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.google.gson.Gson;
import com.gulimall.thirdparty.component.SmsComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dypnsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dypnsapi20170525.models.SendSmsVerifyCodeRequest;
import com.aliyun.sdk.service.dypnsapi20170525.models.SendSmsVerifyCodeResponse;
import darabonba.core.client.ClientOverrideConfiguration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
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

    @Value("${alibaba.cloud.access-key}")
    private String accessKey;

    @Value("${alibaba.cloud.secret-key}")
    private String secretKey;

    @Autowired
    SmsComponent smsComponent;

    @Test
    void smsTest() {
        smsComponent.sendSmsCode("18735661882","1234");
    }

    @Test
    void authTest(){
        Credential credential = Credential.builder()
                .accessKeyId(accessKey)
                .accessKeySecret(secretKey)
                .build();
        // Configure Credentials authentication information
        StaticCredentialProvider provider = StaticCredentialProvider.create(credential);

//        DefaultCredentialProvider provider = DefaultCredentialProvider.builder().build();

        // Configure the Client
        try (AsyncClient client = AsyncClient.builder()
                .region("cn-beijing") // Region ID
                .credentialsProvider(provider)
                // Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                // Endpoint 请参考 https://api.aliyun.com/product/Dypnsapi
                                .setEndpointOverride("dypnsapi.aliyuncs.com")
                )
                .build()) {

            // Parameter settings for API request
            SendSmsVerifyCodeRequest sendSmsVerifyCodeRequest = SendSmsVerifyCodeRequest.builder()
                    .signName("恒创联众")
                    .templateCode("100001")
                    .phoneNumber("18735661882")
                    .templateParam("{\"code\":\"778899\",\"min\":\"10\"}")
                    // Request-level configuration rewrite, can set Http request parameters, etc.
                    // .requestConfiguration(RequestConfiguration.create().setHttpHeaders(new HttpHeaders()))
                    .build();

            CompletableFuture<SendSmsVerifyCodeResponse> response = client.sendSmsVerifyCode(sendSmsVerifyCodeRequest);
            // Synchronously get the return value of the API request
            SendSmsVerifyCodeResponse resp = response.get();
            System.out.println(new Gson().toJson(resp));
            // Asynchronous processing of return values
            /*response.thenAccept(resp -> {
                System.out.println(new Gson().toJson(resp));
            }).exceptionally(throwable -> { // Handling exceptions
                System.out.println(throwable.getMessage());
                return null;
            });*/

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
