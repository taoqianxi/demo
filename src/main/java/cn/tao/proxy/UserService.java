package cn.tao.proxy;

public interface UserService {
    String logIn(String userName,String passWord);

    void logOut();
}
