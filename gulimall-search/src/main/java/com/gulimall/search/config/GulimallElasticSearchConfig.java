package com.gulimall.search.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.Jackson3JsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GulimallElasticSearchConfig {

    @Bean
    public ElasticsearchClient esClient(){
        String serverUrl = "http://localhost:9200";
        //http底层构建
        RestClient restClient = RestClient
                .builder(HttpHost.create(serverUrl))
                .build();
        //客户端json响应映射
        ElasticsearchTransport transport = new RestClientTransport(
                restClient,new Jackson3JsonpMapper()
        );

        //与客户端建立连接
        return new ElasticsearchClient(transport);
    }
}
