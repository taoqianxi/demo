package cn.tao.servlet;

import javax.servlet.http.*;
import java.io.*;

public class TestSessionServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("pwd"));
        writer.print("TestSessionServlet！");
        writer.flush();
        writer.close();
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException {
        this.doGet(request,response);
    }

}
