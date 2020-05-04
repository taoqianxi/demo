package cn.tao.servlet;

import javax.servlet.*;
import java.io.IOException;
import java.util.*;

public class TestServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("开始初始化了！！！");
        System.out.println(servletConfig);
    }

    @Override
    public ServletConfig getServletConfig() {
        System.out.println("开始处理配置！！！");
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("开始处理业务逻辑");
    }

    @Override
    public String getServletInfo() {
        System.out.println("TestServlet.getServletInfo");
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("TestServlet.destroy");
    }


}
