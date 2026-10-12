package com.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;

import com.gulimall.common.utils.StringUtils;
import com.gulimall.product.service.ICategoryBrandRelationService;
import com.gulimall.product.vo.Catelog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import com.gulimall.product.mapper.CategoryMapper;
import com.gulimall.product.domain.Category;
import com.gulimall.product.service.ICategoryService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author jiangdan
 * @date 2026-09-12
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements ICategoryService
{

    @Autowired
    ICategoryBrandRelationService categoryBrandRelationService;

    //TODO redisTemplate底层默认是lettuce，lettuce使用netty，容易出现堆外内存溢出风险，想要解决该问题，可以把lettuce缓存jedis，视频p154
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;
//    Map<String,Object> cache=new HashMap<>();
//    @Override
//    public List<Category> listWithTree() {
//        //查询所有数据
//        List<Category> entites = baseMapper.selectList(null);
//        //找出一级目录
//        List<Category> collect = entites.stream().filter(category ->
//                //父目录为0找出一级目录
//                category.getParentCid() == 0
//        ).map((menu) -> {
//            menu.setChildren(getChildren(menu, entites));
//            return menu;
//        }).sorted((menu1, menu2) -> {
//            return Math.toIntExact((menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort()));
//        }).collect(Collectors.toList());
//        return collect;
//    }
//
//    private List<Category> getChildren(Category root,List<Category> all){
//        List<Category> children = all.stream().filter(category ->{
//            return category.getParentCid() == root.getCatId();
//        }).map(category -> {
//            category.setChildren(getChildren(category,all));
//            return category;
//        }).sorted((menu1,menu2)->{
//            return Math.toIntExact((menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort()));
//        }).collect(Collectors.toList());
//
//        return children;
//    }
    @Override
    public List<Category> listWithTree() {
        // 查询所有数据
        List<Category> entities = baseMapper.selectList(null);

        // 找出一级目录
        return entities.stream()
                .filter(category -> category.getParentCid() != null && category.getParentCid() == 0L)
                .map(category -> {
                    category.setChildren(getChildren(category, entities));
                    return category;
                })
                .sorted((m1, m2) -> {
                    long s1 = m1.getSort() == null ? 0 : m1.getSort();
                    long s2 = m2.getSort() == null ? 0 : m2.getSort();
                    return Long.compare(s1, s2);
                })
                .collect(Collectors.toList());
    }

    @Override
    public int removeMenusByIds(List<Long> ids) {
        //todo 删除前先检查id是否有其他正在使用的地方
        return baseMapper.deleteByIds(ids);
    }

    @Override
    public Long[] findCategoryPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        //递归查找父id
        List<Long> parentPath = findParentPath(catelogId, paths);
        Collections.reverse(parentPath);
        return parentPath.toArray(new Long[parentPath.size()]);
    }

//    @CacheEvict(value = "category",key = "'get1LevelCategory'")
    @Caching(evict = {
            @CacheEvict(value = "category",key = "'get1LevelCategory'"),
            @CacheEvict(value = "category",key = "'catalogLoader'")
    })
    @Transactional
    @Override
    public boolean updateCascader(Category category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
        return true;
    }

    @Cacheable(value = "category",key = "#root.method.name")
    @Override
    public List<Category> get1LevelCategory() {
        System.out.println("执行get1LevelCategory");

        return this.list(new QueryWrapper<Category>().eq("parent_cid", 0L));
    }

    @Cacheable(value = "category",key = "#root.method.name")
    @Override
    public Map<String, List<Catelog2Vo>> catalogLoader() {
        //优化业务逻辑，一次查出所有数据，无需多次访问数据库
        List<Category> categoryList = this.list();
        System.out.println("查询数据库");
        //查出一级分类
        List<Category> category1L = parentCid(categoryList,0L);
        if(category1L!=null&& !category1L.isEmpty()){
            //封装一级分类下的二级分类
            Map<String, List<Catelog2Vo>> collect = category1L.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
                //查询一级分类下的二级分类
                List<Category> category2L = parentCid(categoryList,v.getCatId());
                List<Catelog2Vo> catelog2Vos = new ArrayList<>();
                if(category2L!=null&& !category2L.isEmpty()){
                    catelog2Vos = category2L.stream().map(l2 -> {
                        Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), l2.getCatId().toString(), l2.getName(), null);
                        //封装三级分类
                        List<Category> category3L = parentCid(categoryList,l2.getCatId());
                        if(category3L!=null&& !category3L.isEmpty()){
                            List<Catelog2Vo.Catelog3Vo> catelog3Vos = category3L.stream().map(l3 -> {
                                Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                                return catelog3Vo;
                            }).toList();
                            catelog2Vo.setCatalog3List(catelog3Vos);
                        }
                        return catelog2Vo;
                    }).toList();
                }
                return catelog2Vos;
            }));
            return collect;
        }
        return null;
    }

    public Map<String, List<Catelog2Vo>> catalogLoader1() {
        String catalogLoader = stringRedisTemplate.opsForValue().get("catalogLoader");
        if(StringUtils.isEmpty(catalogLoader)){
            System.out.println("缓存不命中。。查询数据库。。。");
            Map<String, List<Catelog2Vo>> stringListMap = catalogLoaderWithRedisson();
            return stringListMap;
        }
        System.out.println("缓存命中。。直接返回。。。");
        Map<String, List<Catelog2Vo>> catelogMap = JSON.parseObject(catalogLoader, new TypeReference<Map<String, List<Catelog2Vo>>>() {
        });
        return catelogMap;
    }

    //使用redisson
    public Map<String, List<Catelog2Vo>> catalogLoaderWithRedisson() {
        RLock lock = redissonClient.getLock("catalogLoader-lock");
        lock.lock();
        //执行业务
        Map<String, List<Catelog2Vo>> stringListMap = catalogLoaderDB();
        return stringListMap;
    }


    //实现分布式锁
    public Map<String, List<Catelog2Vo>> catalogLoaderWithRedis() {
        //set nx 抢占锁 err1:各种故障导致解锁失败---加上过期时间
        String uuid = UUID.randomUUID().toString();
        Boolean b = stringRedisTemplate.opsForValue().setIfAbsent("lock", uuid,300,TimeUnit.SECONDS);
        Map<String, List<Catelog2Vo>> stringListMap = Map.of();

        if(b){
            //抢到锁
            //设置过期时间，err2:在占锁成功后由于故障过期时间设置失败----占锁和过期时间应该是原子操作
//            stringRedisTemplate.expire("lock",300, TimeUnit.SECONDS);
            System.out.println("redis分布式锁抢占成功");
            //执行业务
            stringListMap = catalogLoaderDB();

            //删除锁,err3:自己的锁已过期，业务超时，意外删除其他线程正在使用的锁----value使用随机uuid
//            String lock = stringRedisTemplate.opsForValue().get("lock");
//            if (lock != null && lock.equals(uuid)) {
                //使用uuid标识，只删除自己创建的lockk，避免误删。err4:在获取lock值和删除之间延迟，导致获取value是自己的，但删除时锁已过期，而是其他的锁了，又会误删----使用lua脚本让查询和删除的操作成为原子操作
//                stringRedisTemplate.delete("lock");
//            }
            //lua脚本删除
            String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                    "    return redis.call(\"del\",KEYS[1])\n" +
                    "else\n" +
                    "    return 0\n" +
                    "end";
            stringRedisTemplate.execute(new DefaultRedisScript<Long>(script,Long.class),Arrays.asList("lock"),uuid);
        }else{
            //占锁失败自旋重试
            catalogLoaderWithRedis();
        }
        return stringListMap;
    }
    
    public Map<String, List<Catelog2Vo>> catalogLoaderDB() {
        //加锁，解决缓存击穿问题
//        synchronized (this){
            //加锁后再次查询缓存确认无数据
            String catalogLoader = stringRedisTemplate.opsForValue().get("catalogLoader");
            if(!StringUtils.isEmpty(catalogLoader)){
                return JSON.parseObject(catalogLoader, new TypeReference<Map<String, List<Catelog2Vo>>>() {});
            }
            //优化业务逻辑，一次查出所有数据，无需多次访问数据库
            List<Category> categoryList = this.list();
            System.out.println("查询数据库");
            //查出一级分类
            List<Category> category1L = parentCid(categoryList,0L);
            if(category1L!=null&& !category1L.isEmpty()){
                //封装一级分类下的二级分类
                Map<String, List<Catelog2Vo>> collect = category1L.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
                    //查询一级分类下的二级分类
                    List<Category> category2L = parentCid(categoryList,v.getCatId());
                    List<Catelog2Vo> catelog2Vos = new ArrayList<>();
                    if(category2L!=null&& !category2L.isEmpty()){
                        catelog2Vos = category2L.stream().map(l2 -> {
                            Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), l2.getCatId().toString(), l2.getName(), null);
                            //封装三级分类
                            List<Category> category3L = parentCid(categoryList,l2.getCatId());
                            if(category3L!=null&& !category3L.isEmpty()){
                                List<Catelog2Vo.Catelog3Vo> catelog3Vos = category3L.stream().map(l3 -> {
                                    Catelog2Vo.Catelog3Vo catelog3Vo = new Catelog2Vo.Catelog3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());
                                    return catelog3Vo;
                                }).toList();
                                catelog2Vo.setCatalog3List(catelog3Vos);
                            }
                            return catelog2Vo;
                        }).toList();
                    }
                    return catelog2Vos;
                }));
                String jsonString = JSON.toJSONString(collect);
                stringRedisTemplate.opsForValue().set("catalogLoader",jsonString);
                return collect;
            }
            return null;
//        }
    }

    private List<Long> findParentPath(Long categoryId,List<Long> paths) {
        paths.add(categoryId);
        Category byId = this.getById(categoryId);
        if (byId == null) {
            return paths;
        }
        if(byId.getParentCid()!=0){
            findParentPath(byId.getParentCid(),paths);
        }
        return paths;
    }

    private List<Category> getChildren(Category root, List<Category> all) {
        return all.stream()
                .filter(category -> category.getParentCid() != null
                        && category.getParentCid().equals(root.getCatId()))
                .map(category -> {
                    category.setChildren(getChildren(category, all));
                    return category;
                })
                .sorted((m1, m2) -> {
                    long s1 = m1.getSort() == null ? 0 : m1.getSort();
                    long s2 = m2.getSort() == null ? 0 : m2.getSort();
                    return Long.compare(s1, s2);
                })
                .collect(Collectors.toList());
    }

    private List<Category> parentCid(List<Category> categoryList,Long pCid){
        return  categoryList.stream().filter(item->item.getParentCid()==pCid).toList();
    }
}
