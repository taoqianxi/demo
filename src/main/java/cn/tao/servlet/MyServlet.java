package cn.tao.servlet;


import cn.tao.util.*;
import com.alibaba.fastjson.JSON;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/myServlet")
public class MyServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        System.err.println("MyServlet.init");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin","*");
        request.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        PPTtoPNG ppTtoPNG = new PPTtoPNG();
        writer.print(JSON.toJSONString(ppTtoPNG.ppt()));
        writer.flush();
        writer.close();
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
        this.doGet(request,response);
    }
}
