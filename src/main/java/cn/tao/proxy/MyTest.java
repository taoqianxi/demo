package cn.tao.proxy;

import cn.tao.proxy.impl.UserServiceImpl;
import org.junit.Test;

public class MyTest {

    @Test
    public void test(){
        UserService userService = new UserServiceImpl();
        System.out.println(userService.logIn("1", "1"));
        System.err.println("===================");
        UserService userService1 = UserProxy.proxy(userService);
        System.out.println(userService1.logIn("1", "1"));
        userService1.logOut();
    }
}
