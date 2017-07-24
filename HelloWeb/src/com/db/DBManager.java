package com.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DBManager {

    // ���ݿ����ӳ���
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String USER = "xzq";
    public static final String PASS = "123456";
    public static final String URL = "jdbc:mysql://118.89.38.92:3306/forest-fire-prevention";

    // ��̬��Ա��֧�ֵ�̬ģʽ
    private static DBManager per = null;
    private Connection conn = null;
    private Statement stmt = null;

    // ��̬ģʽ-����ģʽ
    private DBManager() {
    }

    public static DBManager createInstance() {
        if (per == null) {
            per = new DBManager();
            per.initDB();
        }
        return per;
    }

    // ��������
    public void initDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // �������ݿ⣬��ȡ���+����
    public void connectDB() {
        System.out.println("Connecting to database...");
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("SqlManager:Connect to database successful.");
    }

    // �ر����ݿ� �رն����ͷž��
    public void closeDB() {
        System.out.println("Close connection to database..");
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Close connection successful");
    }

	//public List selectAll(String sql,String tag) throws SQLException {
    public List selectAll(String sql) throws SQLException {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		 // ��ȡDB����
        DBManager sql1 = DBManager.createInstance();
        sql1.connectDB();

		try {
			
			ResultSet resultset = stmt.executeQuery(sql);
			System.out.println(resultset.getRow());
			System.out.println(1);
			/*switch(tag){
			case :*/
			while (resultset.next()) {
				
				Map<String,Object> item=new HashMap<String,Object>();
				System.out.println(2);
	//1
				item.put("collectid", resultset.getObject("collectid"));
    //2		
				System.out.println("xzq111");
				//item.put(tag,resultset.getObject(tag));
				
    //3				
				item.put("cllectname", resultset.getObject("cllectname"));
				System.out.println("xzq11111");
				item.put("collecttemp", resultset.getObject("collecttemp"));
    //4				
				item.put("collecthumi", resultset.getObject("collecthumi"));
	//5
				item.put("collectflame", resultset.getObject("collectflame"));
    //6				
				item.put("collectgas", resultset.getObject("collectgas"));
    //7
				item.put("time", resultset.getObject("time"));
				list.add(item);
			}
		//	case:
	//	}
			System.out.println(list.size());
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sql1.closeDB();
		}
		return list;

	}
    
    
    
    public List selectGas(String sql) throws SQLException {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		 // ��ȡDB����
        DBManager sql1 = DBManager.createInstance();
        sql1.connectDB();

		try {
			
			ResultSet resultset = stmt.executeQuery(sql);
			System.out.println(resultset.getRow());
			System.out.println(1);
			
			while (resultset.next()) {
				
				Map<String,Object> item=new HashMap<String,Object>();
				System.out.println(2);
				System.out.println("xzq111");
				
    //3		
				item.put("collectgas", resultset.getObject("collectgas"));
				System.out.println("xzq11111");
				
				list.add(item);
			}
	
			System.out.println(list.size());
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sql1.closeDB();
		}
		return list;

	}
    
    public List selectAllusers(String sql) throws SQLException {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		 // ��ȡDB����
        DBManager sql1 = DBManager.createInstance();
        sql1.connectDB();

		try {
			
			ResultSet resultset = stmt.executeQuery(sql);
			System.out.println(resultset.getRow());
			System.out.println(1);
			
			while (resultset.next()) {
				
				Map<String,Object> item=new HashMap<String,Object>();
				System.out.println(2);
	//1
				item.put("id", resultset.getObject("id"));
    //2		
				System.out.println("xzq111");
				
    //3		
				item.put("username", resultset.getObject("username"));
				System.out.println("xzq11111");
				item.put("password", resultset.getObject("password"));
				list.add(item);
			}
	
			System.out.println(list.size());
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sql1.closeDB();
		}
		return list;

	}
    
    
    // ��ѯ
    public ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    // ����/ɾ��/�޸�
    public int executeUpdate(String sql) {
        int ret = 0;
        try {
            ret = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    
}