package com.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import tool.jsonTool;

import com.db.*;

public class User_Delete extends HttpServlet {

	private List<List> list = new ArrayList<List>();
	private int listnum = 0;
	private String jsonString = " ";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);

	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String SQLusers = "select * from users";
		
		System.out.println("---------- get connection --------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

	
		DBManager db= DBManager.createInstance();
		try {
		
			list = db.selectAllusers(SQLusers);
				jsonString = jsonTool.creatJsonString(list);
				out.print(jsonString);
				System.out.println(jsonString);

				
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		} finally {
			System.out.println(4);
			out.flush();
			out.close();
			System.out.println("---------- close connection --------------");
		}
	}
}

