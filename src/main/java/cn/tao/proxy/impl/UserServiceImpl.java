package cn.tao.proxy.impl;

import cn.tao.proxy.UserService;
import org.slf4j.*;

public class UserServiceImpl implements UserService {

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public String logIn(String userName, String passWord) {
        if ("123456".equals(userName)) {
            if ("123".equals(passWord)) {
                logger.info("登录成功","naho1","12121");
                return "success";
            }
        }
        logger.info("登录失败",userName,passWord);
        return "error";
    }

    @Override
    public void logOut() {
        logger.info("退出登录！");
    }

}
