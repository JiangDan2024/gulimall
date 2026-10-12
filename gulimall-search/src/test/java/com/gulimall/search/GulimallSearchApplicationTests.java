package com.gulimall.search;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.LongTermsAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.LongTermsBucket;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
class GulimallSearchApplicationTests {

    @Autowired
    ElasticsearchClient esClient;

    @Test
    void contextLoads() {
        System.out.println(esClient);
    }
//保存数据
    @Test
    void indexTest() throws IOException {
        User user = new User();
        user.setName("zhangsan");
        user.setAge(18);
        user.setGender("男");
        IndexResponse indexRes = esClient.index(i ->
                i.index("users")
                        .id("1")
                        .document(user));
        System.out.println("indexRes = " + indexRes);
    }
//获取数据
    @Test
    void searchTest() throws IOException {
        GetResponse<User> response = esClient.get(g->g
                .index("users")
                .id("1"),
                User.class);
        if (response.found()){
            User user = response.source();
            System.out.println("User = " + user.toString());
        }else{
            System.out.println("查询失败");
        }
    }
//检索数据
    @Test
    void matchTest() throws IOException {
        SearchResponse<User> request = esClient.search(s->s
                .index("users")
                .query(q->q
                        .match(t->t
                                .field("name")
                                .query("zhangsan"))),User.class);
        System.out.println("request = " + request.toString());
    }
    //更新
    @Test
    void updateTest() throws IOException {
        User user = new User();
        user.setName("zhangsan2");
        user.setAge(28);
        user.setGender("女");
        UpdateResponse<User> users = esClient.update(u -> u
                        .index("users")
                        .id("1")
                        .doc(user)
                .docAsUpsert(true),
                User.class);
        System.out.println("users = " + users.toString());
    }
    //删除数据
    @Test
    void deleteDocTest() throws IOException {
        esClient.delete(d->d.index("users").id("1"));
    }
    //删除索引
    @Test
    void delIndex() throws IOException {
        esClient.indices().delete(d->d
                .index("users"));
    }
    @ToString
    @Data
    public static class User{
        private String name;
        private String gender;
        private Integer age;
    }

    //一个复杂的检索：bank里地址包含mill的年龄分布情况，及对应的平均薪资
    @Test
    void aggsTest() throws IOException {
        SearchResponse<Map> search = esClient.search(s -> s
                .index("bank")
                .query(q -> q
                        .match(t -> t
                                .field("address")
                                .query("mill")))
                .aggregations("ageAgg", a -> a
                        .terms(t -> t
                                .field("age")
                                .size(10)))
                .aggregations("balanceAvg", a -> a
                        .avg(avg -> avg
                                .field("balance"))), Map.class);
        //查看命中结果
        HitsMetadata<Map> hits = search.hits();
        for (Hit<Map> hit : hits.hits()) {
            System.out.println(hit);
        }
        //查看聚合结果
        Aggregate ageAgg = search.aggregations()
                .get("ageAgg");
        if(ageAgg.isLterms()){
            LongTermsAggregate lterms = ageAgg.lterms();
            for (LongTermsBucket bucket : lterms.buckets().array()) {
                long key = bucket.key();
                long docCount = bucket.docCount();
                System.out.println("Age:"+key+",count:"+docCount);
            }
        }
        double balanceAvg = search.aggregations().get("balanceAvg").avg().value();
        System.out.println("balanceAvg = " + balanceAvg);
    }

}
