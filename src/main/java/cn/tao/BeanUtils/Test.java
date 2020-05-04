package cn.tao.BeanUtils;

import cn.tao.Test1;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Test {

    public static void main(String[] args) {
//        A a = new A();
//        a.setAge(12);
//        a.setName("小明");
//        B b = new B();
//        BeanUtils.copyProperties(a,b);
//        System.out.println(b);
//        Map map = new HashMap();
//
//        Stack stack = new Stack();
//        stack.add("123");
//        stack.add(null);

        List<String> arrayList = new ArrayList();
        arrayList.add("123");
        System.out.println(arrayList.toArray()[0]);
        String [] strings = new String[10];
        String[] strings1 = Arrays.copyOf(strings, strings.length + 1);
        System.out.println(strings1.length);
//        Arrays.copyOf(, );
    }

    @Data
    @ToString
     private static class A{
        private String name;
        private Integer age;
    }

    @Data
    @ToString
    private static class B{
        private String name;
        private Integer age;
    }

    public static class Test1{
        public static void main(String[] args) {
            System.out.println(18>>1);
        }
    }
}
