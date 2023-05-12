package com.dsk.utils;

import org.springframework.util.StopWatch;

import java.time.Duration;
import java.time.Instant;

/**
 * @author dsk
 * @date 2023/5/12
 */
public class CalculationSpentTimeUtil {

    public static void main(String[] args) throws InterruptedException {
        // method01();
        // method02();
        // method03();
    }

    public static void method01() throws InterruptedException {
        long start = System.currentTimeMillis();
        Thread.sleep(2000);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "ms");
    }

    public static void method02() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("任务1");
        Thread.sleep(3000);
        //System.out.println("当前任务名称：" + stopWatch.currentTaskName());
        stopWatch.stop();

        stopWatch.start("任务2");
        Thread.sleep(2000);
        //System.out.println("当前任务名称：" + stopWatch.currentTaskName());
        stopWatch.stop();

        // 打印出耗时
        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.shortSummary());
        // stop后它的值为null
        System.out.println(stopWatch.currentTaskName());

        // 最后一个任务的相关信息
        System.out.println(stopWatch.getLastTaskName());

        // 任务总的耗时  如果你想获取到每个任务详情（包括它的任务名、耗时等等）可使用
        System.out.println("所有任务总耗时：" + stopWatch.getTotalTimeMillis());
        System.out.println("任务总数：" + stopWatch.getTaskCount());
    }

    public static void method03() throws InterruptedException {
        Instant start = Instant.now();
        Thread.sleep(2000);
        Instant end = Instant.now();
        long duration = Duration.between(start, end).toMillis();
        System.out.println("耗时：" + duration + "ms");
    }
}
