package com.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class RegLet extends HttpServlet {

    private static final long serialVersionUID = 1L;
//369840050351775312
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    		doPost(request,response);
       
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	request.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
    	OutputStream out = response.getOutputStream();
    	 // 接收客户端信息
        String username = request.getParameter("username");
        //username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
        String password = request.getParameter("password");
        System.out.print("11");
        System.out.println(username + "--" + password);

        // 新建服务对象
        System.out.print("22");
        Service serv = new Service();

        // 验证处理
        boolean reged = serv.register(username, password);
        if (reged) {
        	 System.out.print("33");
            System.out.print("RegisterSuccss");
            out.write("yes".getBytes("utf-8"));
            request.getSession().setAttribute("username", username);
            // response.sendRedirect("welcome.jsp");
        } else {
        	 System.out.print("44");
        	 out.write("no".getBytes("utf-8"));
            System.out.print("RegisterFailed");
        }

        // 返回信息到客户端
     /*  response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print("用户名：" + username);
        out.print("密码：" + password);
        out.flush();
        out.close();*/


    }

}