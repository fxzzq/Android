package cuit.edu.cn;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		OutputStream out = response.getOutputStream();
		//
	//PrintWriter out = response.getWriter();
		
		String userName = request.getParameter("userName");
		String pwd = request.getParameter("pwd");
		if("xzq".equals(userName) && "123456".equals(pwd)){
			out.write("yes".getBytes("utf-8"));
		System.out.println("124");
		}else{
			out.write("no".getBytes("utf-8"));
		}
	}
}

