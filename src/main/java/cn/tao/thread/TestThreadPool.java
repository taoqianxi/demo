package cn.tao.thread;

import cn.tao.entity.Test;
import cn.tao.mapper.TestMapper;
import cn.tao.thread.task.AddTestTask;
import cn.tao.util.ThreadPoolUtil;
import org.springframework.context.support.*;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.*;
import java.util.concurrent.CountDownLatch;

public class TestThreadPool {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext =new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TestMapper bean = applicationContext.getBean(TestMapper.class);
        List<Test> tests = bean.listTest();
        for (Test test : tests) {
            System.out.println(test);
        }
        List<Test> list = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            Test test = new Test();
            test.setDesc(String.valueOf(i));
            list.add(test);
        }
        threadData(list,applicationContext);
        System.err.println("结束");
    }

    public static void threadData(List<Test> list,ClassPathXmlApplicationContext applicationContext){
        //500分组
        int count = 500;
        int listSize = list.size();
        //线程数
        int runSize = (listSize/count);
        runSize = runSize == 0?1:runSize;
        CountDownLatch countDownLatch = new CountDownLatch(runSize);
        try {
            List<Test>  newList = null;
            for (int i = 0; i < runSize; i++) {
                if((i+1)==runSize){
                    int startIndex = (i*count);
                    int endIndex = list.size();
                    newList =list.subList(startIndex,endIndex);
                }else{
                    int startIndex = i*count;
                    int endIndex = (i+1)*count;
                        newList = list.subList(startIndex, endIndex);
                }

                List<Test> finalNewList = newList;
                ThreadPoolUtil.getInstance().run(()->{
                        TestMapper bean = applicationContext.getBean(TestMapper.class);
                        if (finalNewList != null) {
                            for (Test t : finalNewList) {
                                t.setThread(Thread.currentThread().getName());
                                bean.insert(t);
                            }
                        }
                        countDownLatch.countDown();
                    });
            }
            System.err.println("线程睡了=============");
            countDownLatch.await();
            System.err.println("线程等待完毕=============");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
