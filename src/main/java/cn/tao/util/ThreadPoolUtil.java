package cn.tao.util;

import java.util.concurrent.*;

/**
 * tao
 * 2019-11-22
 */
public class ThreadPoolUtil {

    private static ThreadPoolUtil threadPoolUtil;
    private static int corePoolSize =100; //线程池中核心线程数量。
    private static int maximumPoolSize = 400;//线程池中线程总数量。
    private static int keepAliveTime = 60;//普通线程的最长空闲时间(若普通线程空闲时间超出keepAliveTime，则会被销毁)，只有corePoolSize小于
    private static int blockQueenSize = 10;//创建长度为8的阻塞队列
    private static ThreadPoolExecutor threadPoolExecutor;
    private static boolean isInit= false;

    static {
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime, TimeUnit.SECONDS,new ArrayBlockingQueue(blockQueenSize));
        threadPoolUtil = new ThreadPoolUtil();
    }

    public static void initConfig(int corePoolSize, int maximumPoolSize, int keepAliveTime, int blockQueenSize) {
        ThreadPoolUtil.corePoolSize = corePoolSize;
        ThreadPoolUtil.maximumPoolSize = maximumPoolSize;
        ThreadPoolUtil.keepAliveTime = keepAliveTime;
        ThreadPoolUtil.blockQueenSize = blockQueenSize;
        isInit = true;
    }

    public static ThreadPoolUtil getInstance() {
        try {
            if (!isInit) {
                throw new RuntimeException("线程池没有初始化参数");
            }
        } finally {
            return threadPoolUtil;
        }
    }

    public void run(Runnable runnable) {
        threadPoolExecutor.execute(runnable);
    }


}
