package com.gulimall.thirdparty.component;


import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dypnsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dypnsapi20170525.models.SendSmsVerifyCodeRequest;
import com.aliyun.sdk.service.dypnsapi20170525.models.SendSmsVerifyCodeResponse;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Data
@Component
@ConfigurationProperties(prefix = "spring.cloud.alibaba.sms")
public class SmsComponent {

    private String signName;

    private String templateCode;

    private Integer timeKeepAlive;

    @Value("${alibaba.cloud.access-key}")
    private String accessKey;

    @Value("${alibaba.cloud.secret-key}")
    private String secretKey;

    public void sendSmsCode(String phoneNumber, String code) {
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
                    .signName(signName)
                    .templateCode(templateCode)
                    .phoneNumber(phoneNumber)
                    .templateParam("{\"code\":\""+code+"\",\"min\":\""+timeKeepAlive.toString()+"\"}")
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
