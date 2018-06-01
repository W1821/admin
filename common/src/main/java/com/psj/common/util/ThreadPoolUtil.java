package com.psj.common.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 *
 * @author saiya
 * @date 2018/5/23 0023
 */
public class ThreadPoolUtil {

    /**
     * 创建一个单线程的ThreadPoolExecutor
     *
     * @param threadName 线程名称
     * @param queueSize  工作队列大小
     * @return ThreadPoolExecutor实例
     */
    public static ThreadPoolExecutor createThreadPoolExecutor(String threadName, int queueSize) {
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat(threadName + "-%d").build();
        return new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<>(queueSize), factory);
    }

}
