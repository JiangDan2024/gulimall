package com.gulimall.thirdparty.controller;

import com.aliyun.sts20150401.models.AssumeRoleResponse;
import com.aliyun.sts20150401.models.AssumeRoleResponseBody;
import com.aliyun.tea.TeaException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gulimall.common.core.domain.AjaxResult;
import jakarta.annotation.PostConstruct; // 注意 Spring Boot 3+ 是 jakarta
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/thirdparty/oss")
public class OssController {

    @Value("${spring.cloud.alicloud.oss.bucket}")
    private String bucket;

    @Value("${spring.cloud.alicloud.oss.region}")
    private String region;

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endpoint;

    @Value("${spring.cloud.alicloud.sts.role-arn}")
    private String roleArn;

    // 新增：STS的endpoint配置，默认杭州
    @Value("${spring.cloud.alicloud.sts.endpoint:sts.cn-hangzhou.aliyuncs.com}")
    private String stsEndpoint;

    private String host;

    // 在Bean初始化完成后，拼装host
    @PostConstruct
    public void init() {
        // 处理 endpoint 可能带有 http:// 或 https:// 前缀的情况
        String cleanEndpoint = endpoint.replace("https://", "").replace("http://", "");
        this.host = "https://" + bucket + "." + cleanEndpoint;
        System.out.println("OSS直传服务初始化完成，Host: " + this.host);
    }

    // 限定上传到OSS的文件前缀
    String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    String upload_dir = format+"/";
    // 指定过期时间，单位为秒
    Long expire_time = 3600L;

    public static String generateExpiration(long seconds) {
        long now = Instant.now().getEpochSecond();
        long expirationTime = now + seconds;
        Instant instant = Instant.ofEpochSecond(expirationTime);
        ZoneId zone = ZoneOffset.UTC;
        ZonedDateTime zonedDateTime = instant.atZone(zone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return zonedDateTime.format(formatter);
    }

    // 修改：去掉 static，改为实例方法
    public com.aliyun.sts20150401.Client createStsClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                .setAccessKeyId(System.getenv("OSS_ACCESS_KEY_ID"))
                .setAccessKeySecret(System.getenv("OSS_ACCESS_KEY_SECRET"));
        config.endpoint = this.stsEndpoint;
        return new com.aliyun.sts20150401.Client(config);
    }

    // 修改：去掉 static，使用注入的 this.roleArn
    public AssumeRoleResponseBody.AssumeRoleResponseBodyCredentials getCredential() throws Exception {
        com.aliyun.sts20150401.Client client = this.createStsClient();
        com.aliyun.sts20150401.models.AssumeRoleRequest assumeRoleRequest = new com.aliyun.sts20150401.models.AssumeRoleRequest()
                .setRoleArn(this.roleArn) // 注意：改成了使用注入的配置
                .setRoleSessionName("gulimall-session");
        com.aliyun.teautil.models.RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            AssumeRoleResponse response = client.assumeRoleWithOptions(assumeRoleRequest, runtime);
            return response.body.credentials;
        } catch (TeaException error) {
            System.out.println("STS获取失败: " + error.getMessage());
            System.out.println("诊断地址: " + error.getData().get("Recommend"));
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        // 容错
        AssumeRoleResponseBody.AssumeRoleResponseBodyCredentials defaultCredentials = new AssumeRoleResponseBody.AssumeRoleResponseBodyCredentials();
        defaultCredentials.accessKeyId = "ERROR_ACCESS_KEY_ID";
        defaultCredentials.accessKeySecret = "ERROR_ACCESS_KEY_SECRET";
        defaultCredentials.securityToken = "ERROR_SECURITY_TOKEN";
        return defaultCredentials;
    }

    @GetMapping("/get_post_signature_for_oss_upload")
    public AjaxResult getPostSignatureForOssUpload() throws Exception {
        AssumeRoleResponseBody.AssumeRoleResponseBodyCredentials sts_data = getCredential();
        String accesskeyid = sts_data.accessKeyId;
        String accesskeysecret = sts_data.accessKeySecret;
        String securitytoken = sts_data.securityToken;

        ZonedDateTime today = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);
        String date = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // ✅ 修改点：动态生成日期前缀，例如：2026-09-17/ 或者 2026/09/17/
        String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "/";

        ZonedDateTime now = ZonedDateTime.now().withZoneSameInstant(ZoneOffset.UTC);
        String x_oss_date = now.format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'"));

        String x_oss_credential = accesskeyid + "/" + date + "/" + region + "/oss/aliyun_v4_request";
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> policy = new HashMap<>();
        policy.put("expiration", generateExpiration(expire_time));
        List<Object> conditions = new ArrayList<>();

        conditions.add(Collections.singletonMap("bucket", bucket));
        conditions.add(Collections.singletonMap("x-oss-security-token", securitytoken));
        conditions.add(Collections.singletonMap("x-oss-signature-version", "OSS4-HMAC-SHA256"));
        conditions.add(Collections.singletonMap("x-oss-credential", x_oss_credential));
        conditions.add(Collections.singletonMap("x-oss-date", x_oss_date));
        conditions.add(Arrays.asList("content-length-range", 1, 10240000));
        conditions.add(Arrays.asList("eq", "$success_action_status", "200"));
        conditions.add(Arrays.asList("starts-with", "$key", upload_dir));

        policy.put("conditions", conditions);
        String jsonPolicy = mapper.writeValueAsString(policy);
        String stringToSign = new String(Base64.encodeBase64(jsonPolicy.getBytes()));

        byte[] dateKey = hmacsha256(("aliyun_v4" + accesskeysecret).getBytes(), date);
        byte[] dateRegionKey = hmacsha256(dateKey, region);
        byte[] dateRegionServiceKey = hmacsha256(dateRegionKey, "oss");
        byte[] signingKey = hmacsha256(dateRegionServiceKey, "aliyun_v4_request");
        byte[] result = hmacsha256(signingKey, stringToSign);
        String signature = BinaryUtil.toHex(result);

        Map<String, String> response = new HashMap<>();
        response.put("version", "OSS4-HMAC-SHA256");
        response.put("policy", stringToSign);
        response.put("x_oss_credential", x_oss_credential);
        response.put("x_oss_date", x_oss_date);
        response.put("signature", signature);
        response.put("security_token", securitytoken);
        response.put("dir", dateDir);
        response.put("host", host); // 此时 host 已经有值了

        return AjaxResult.success().put("data",response);
    }

    public static byte[] hmacsha256(byte[] key, String data) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);
            return mac.doFinal(data.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate HMAC-SHA256", e);
        }
    }
}