package cn.tao.proxy;

import org.slf4j.*;

import java.lang.reflect.*;

public class UserProxy {

   static Logger logger = LoggerFactory.getLogger(UserProxy.class);

    public static UserService proxy(UserService userService){
        UserService userServiceX =(UserService)Proxy.newProxyInstance(UserService.class.getClassLoader(),userService.getClass().getInterfaces(),(Object proxy, Method method, Object[] args)->{
            logger.info("=======User代理开始了======");
            System.out.println(method.getName());
            if ("logOut".equals(method.getName())) {

            } else {
                args[0]="123456";
                args[1]="123";
            }
            Object invoke = method.invoke(userService,args);
            logger.info("=======user代理结束了======");
            return invoke;
        });
        return userServiceX;
    }
}
