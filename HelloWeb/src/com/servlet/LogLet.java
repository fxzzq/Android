package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import java.io.OutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.Service;

public class LogLet extends HttpServlet {

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
       // username = new String(username.getBytes("ISO-8859-1"), "UTF-8");
        String password = request.getParameter("password");
        System.out.println("1");
        
        System.out.println(username + "--" + password);
        System.out.println("2");
        // 新建服务对象
        Service serv = new Service();

        // 验证处理
        boolean loged = serv.login(username, password);
        if (loged) {
        	 System.out.println("3");
            System.out.print("Succss");
            
            System.out.println("4");
            //返回客服端yes
            out.write("yes".getBytes("utf-8"));
            request.getSession().setAttribute("username", username);
          // response.sendRedirect("index.jsp");
        } else {
        	out.write("no".getBytes("utf-8"));
            System.out.print("Failed");
        }

        // 返回信息到客户端
     /*   response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
       
        PrintWriter out = response.getWriter();
        out.print("用户名122：" + username);
        out.print("密码234：" + password);*/
        //out.flush();
       // out.close();
    }

}