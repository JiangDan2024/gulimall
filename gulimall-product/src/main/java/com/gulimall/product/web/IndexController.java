package com.gulimall.product.web;

import com.gulimall.common.utils.uuid.UUID;
import com.gulimall.product.vo.Catelog2Vo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import com.gulimall.product.domain.Category;
import com.gulimall.product.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
public class IndexController {

    @Autowired
    ICategoryService categoryService;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping({"/","/index.html"})
    public String index(Model model){
        //消耗时间
        long l = System.currentTimeMillis();
        //返回一级目录数据
        List<Category> category = categoryService.get1LevelCategory();
        model.addAttribute("category",category);
        System.out.println("消耗时间："+(System.currentTimeMillis()-l));
        return "index";
    }

    //查询二三级菜单并封装vo
    @ResponseBody
    @GetMapping("/index/json/catalog.json")
    public Map<String,List<Catelog2Vo>> catalogLoader(){
        Map<String,List<Catelog2Vo>> map = new HashMap<>();
        map = categoryService.catalogLoader();
        return map;
    }

    //简单服务
    @ResponseBody
    @GetMapping("/hello")
    public String hello(){
        RLock lock = redissonClient.getLock("lock");
        lock.lock(10, TimeUnit.SECONDS);
        try{
            System.out.println("redisson占锁成功。。。"+Thread.currentThread().getId());
            Thread.sleep(30000);
        }catch (Exception e){
            log.error("redisson占锁失败：",e);
        }finally {
            System.out.println("redisson释放锁："+Thread.currentThread().getId());
            lock.unlock();
        }
        return "hello";
    }

    //测试读写锁
    @ResponseBody
    @GetMapping("/write")
    public String write(){
        String uuid = UUID.randomUUID().toString();
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("rw-lock");
        //加写锁
        readWriteLock.writeLock().lock();
        System.out.println("写锁加锁成功。。。。"+Thread.currentThread().getId());
        try{
            //写操作
            stringRedisTemplate.opsForValue().set("writeValue",uuid);
            Thread.sleep(10000);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //释放锁
            readWriteLock.writeLock().unlock();
            System.out.println("写锁释放"+Thread.currentThread().getId());
        }
        return uuid;
    };
    
    @ResponseBody
    @GetMapping("/read")
    public String read(){
        String uuid = null;
        RReadWriteLock readWriteLock = redissonClient.getReadWriteLock("rw-lock");
        readWriteLock.readLock().lock();
        System.out.println("读锁加锁成功。。。。"+Thread.currentThread().getId());
        try {
            uuid = stringRedisTemplate.opsForValue().get("writeValue");
//            Thread.sleep(10000);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //读锁释放
            readWriteLock.readLock().unlock();
            System.out.println("读锁释放成功。。。"+Thread.currentThread().getId());
        }
        return uuid;
    }

    //测试闭锁

    /**
     * 以放假关门为例测试闭锁：
     * 保安必须确认所有教室都关了才能关了大门
     * @return
     */
    @ResponseBody
    @GetMapping("lockDoor")
    public String lockDoor() throws InterruptedException {
        RCountDownLatch lockDoor = redissonClient.getCountDownLatch("lockDoor");
        //设置教室一共有几个门
        lockDoor.trySetCount(5);
        //等待所有门关闭
        lockDoor.await();
        return "放假了。。";
    }

    @ResponseBody
    @GetMapping("/gogogo/{id}")
    public String gogogo(@PathVariable("id") String id){
        RCountDownLatch lockDoor = redissonClient.getCountDownLatch("lockDoor");
        //关一个减一个，直到减为零
        lockDoor.countDown();
        return id+"关门了";
    }

    //测试信号量semaphore
    /**
     * 用停车场模拟信号量semaphore，一共3个
     */
    @ResponseBody
    @GetMapping("/come")
    public String come() throws InterruptedException {
        RSemaphore park = redissonClient.getSemaphore("park");
        boolean b = park.tryAcquire();
        return "ok"+b;
    }

    @ResponseBody
    @GetMapping("/go")
    public String go(){
        RSemaphore park = redissonClient.getSemaphore("park");
        park.release();
        return "ok";
    }

}
