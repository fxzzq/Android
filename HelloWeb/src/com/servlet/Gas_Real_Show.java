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

public class Gas_Real_Show extends HttpServlet {

	private List<List> list = new ArrayList<List>();
	private int listnum = 0;
	private String jsonString = " ";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);

	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String SQLcollectdata = "select collectgas from collectdata where collectid=(select max(collectid) from collectdata)";
		
		System.out.println("---------- get connection --------------");
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();

	//	String tag = request.getParameter("TAG");
		//System.out.println(tag);
		DBManager db= DBManager.createInstance();
		try {
		//	switch (tag) {
		//	case "cllectname":
				//list = db.selectAll(SQLcollectdata,tag);
			list = db.selectGas(SQLcollectdata);
				jsonString = jsonTool.creatJsonString(list);
				out.print(jsonString);
				System.out.println(jsonString);

			//	break;
				
		/*	case "wet":
				list = ds.selectAll(SQLwet,tag);
				jsonString = jsonTool.creatJsonString(list);
				out.print(jsonString);
				System.out.println(jsonString);*/
			//default:
			//	break;
		//	}
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
