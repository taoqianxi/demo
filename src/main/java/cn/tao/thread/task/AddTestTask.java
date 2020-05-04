package cn.tao.thread.task;

import cn.tao.entity.Test;
import cn.tao.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class AddTestTask implements Runnable {

    private List<Test> list;
    private CountDownLatch countDownLatch;
    private ClassPathXmlApplicationContext applicationContext;
    public AddTestTask(List<Test> list,CountDownLatch countDownLatch,ClassPathXmlApplicationContext applicationContext){
        this.list = list;
        this.countDownLatch = countDownLatch;
        this.applicationContext = applicationContext;
    }
    @Override
    public void run() {
        TestMapper bean = applicationContext.getBean(TestMapper.class);
        if (list != null) {
            for (Test t : list) {
                t.setThread(Thread.currentThread().getName());
                bean.insert(t);
            }
        }
        countDownLatch.countDown();
    }
}
