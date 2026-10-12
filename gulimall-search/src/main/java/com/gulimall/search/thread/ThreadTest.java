package com.gulimall.search.thread;

import java.util.concurrent.*;

public class ThreadTest {
    public static ExecutorService executor = Executors.newFixedThreadPool(10);
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //线程的启动
        //1.继承Thread
//        Thread01  thread01 = new Thread01();
//        thread01.start();
        //2.实现Runnable
//        Thread02 thread02 = new Thread02();
//        new Thread(thread02).start();
        //3.实现Callable
//        Thread03 thread03 = new Thread03();
//        FutureTask<String> stringFutureTask = new FutureTask<>(thread03);
//        stringFutureTask.run();
//        try {
//            System.out.println(stringFutureTask.get());
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        }
        System.out.println("main....start.....");

        //创建异步对象，使用runAsync方法。
//        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//            System.out.println("当前线程：" + Thread.currentThread().getName());
//            int i = 10 / 2;
//            System.out.println("运行结果：" + i);
//        },executor);
        //使用supplyAsync+whenCompleteAsync+exceptionally
//        CompletableFuture<Integer> exceptionally = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程：" + Thread.currentThread().getName());
//            int i = 10 / 0;
//            System.out.println("运行结果：" + i);
//            return i*2;
//        }, executor).whenCompleteAsync((res, execption) -> {
//            System.out.println("结果是" + res + "异常是" + execption);
//        }).exceptionally(e -> {
//            return 10;
//        });
        //handle的使用
//        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
//            System.out.println("当前线程：" + Thread.currentThread().getName());
//            int i = 10 / 0;
//            System.out.println("运行结果：" + i);
//            return i * 2;
//        }, executor).whenCompleteAsync((res, execption) -> {
//            System.out.println("结果是" + res + "异常是" + execption);
//        }).handle((res, t) -> {
//            if (res != null) {
//                return 0;
//            }
//            if (t != null) {
//                return 10;
//            }
//            return res;
//        }).thenRunAsync(()->{
            //thenRunAsync方法，无参无返回值
//            System.out.println("任务2启动了");
//        },executor);
//        }).thenAcceptAsync(res->{
            //thenAcceptAsync方法，接收上一个方法的结果，无返回值
//            System.out.println("任务2启动了"+res);
//        },executor);
//        }).thenApplyAsync(res -> {
            //thenApplyAsync方法，接收上一个方法结果，同时也返回结果
//            return res + 5;
//        });
//        CompletableFuture<Object> future01 = CompletableFuture.supplyAsync(() -> {
//            System.out.println("任务1启动。。。。。");
//            System.out.println("任务1结束。。。。。");
//            int i = 10 / 5;
//            return i;
//        }, executor);
//
//        CompletableFuture<Object> future02 = CompletableFuture.supplyAsync(() -> {
//            System.out.println("任务2启动。。。。。");
//            try {
//                Thread.sleep(3000);
//                System.out.println("任务2结束。。。。。");
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            return "hello";
//        }, executor);
        //两任务组合——都要完成
        //runAfterBothAsync,不接收两个任务的返回结果，且没有返回值
//        future01.runAfterBothAsync(future02,()->{
//            System.out.println("任务3启动");
//            System.out.println("任务3结束");
//        },executor);
//thenAcceptBothAsync,能够接收两个任务的返回结果，但没有返回值
//        future01.thenAcceptBothAsync(future02,(f1,f2)->{
//            System.out.println("任务3启动");
//            System.out.println("任务3结束"+f1+"----"+f2);
//        },executor);
        //thenCombineAsync,既可以接收两个任务的返回结果，也有返回值
//        CompletableFuture<String> future = future01.thenCombineAsync(future02, (f1, f2) -> {
//            System.out.println("任务3启动");
//            System.out.println("任务3结束" + f1 + "----" + f2);
//            return f1 + ">>>>>>" + f2;
//        }, executor);
        //两任务组合——一个完成即可
        //runAfterEitherAsync无参数无返回值
//        future01.runAfterEitherAsync(future02,()->{
//            System.out.println("任务3启动");
//            System.out.println("任务3结束");
//        },executor);
        //acceptEitherAsync有入参，无返回值
//        future01.acceptEitherAsync(future02,res->{
//            System.out.println("任务3启动");
//            System.out.println("任务3结束"+res);
//        },executor);
        //applyToEitherAsync有入参有返回值
//        CompletableFuture<String> future = future01.applyToEitherAsync(future02, res -> {
//            System.out.println("任务3启动");
//            System.out.println("任务3结束" + res);
//            return "hhhh" + res.toString();
//        }, executor);

        //多任务组合
        CompletableFuture<String> futureImg = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品图片");
            return "img";
        }, executor);
        CompletableFuture<String> futurePrice = CompletableFuture.supplyAsync(() -> {
            System.out.println("查询商品价格");
            return "price";
        }, executor);
        CompletableFuture<String> futureDesc = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("查询商品信息");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "desc";
        }, executor);
        //allof，所有任务都完成才结束，必须配合get/join，会等待所有结果完成
//        CompletableFuture.allOf(futureImg, futurePrice, futureDesc).get();
        //anyof一个任务完成就结束
        CompletableFuture.anyOf(futureImg,futurePrice,futureDesc).thenAccept(result -> {
            System.out.println("完成》》》"+result);
        }).get();
        System.out.println("main....end.....");


    }

    public static class Thread01 extends Thread{
        @Override
        public void run() {
            System.out.println("当前线程："+Thread.currentThread().getName());
            int i = 10/2;
            System.out.println("运行结果："+i);
        }
    }
    public static class Thread02 implements Runnable{

        @Override
        public void run() {
            System.out.println("当前线程："+Thread.currentThread().getName());
            int i = 10/2;
            System.out.println("运行结果："+i);
        }
    }

    public static class Thread03 implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("当前线程："+Thread.currentThread().getName());
            int i = 10/2;
            System.out.println("运行结果："+i);
            return "yes";
        }
    }
}
