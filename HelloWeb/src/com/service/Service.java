package com.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.db.DBManager;

public class Service {

    public Boolean login(String username, String password) {

        // ��ȡSql��ѯ���
        String logSql = "select * from users where username ='" + username
                + "' and password ='" + password + "'";

        // ��ȡDB����
        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        // ����DB����
        try {
            ResultSet rs = sql.executeQuery(logSql);
            if (rs.next()) {
                sql.closeDB();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql.closeDB();
        return false;
    }

    public Boolean register(String username, String password) {
    
        // ��ȡSql��ѯ���
    	System.out.print("��ѯǰ");
        String regSql = "insert into users(username,password) values('"+ username+ "','"+ password+ "') ";
        System.out.print("��ѯ��");
        // ��ȡDB����
        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        int ret = sql.executeUpdate(regSql);
        if (ret != 0) {
            sql.closeDB();
            return true;
        }
        sql.closeDB();
        
        return false;
    }
    
    public Boolean Gasquery() {

        // ��ȡSql��ѯ���
        String gasSql = "select collectgas from collectdata where collectid=(select max(collectid) from collectdata)";

        // ��ȡDB����
        DBManager sql = DBManager.createInstance();
        sql.connectDB();

        // ����DB����
        try {
            ResultSet rs = sql.executeQuery(gasSql);
            if (rs.next()) {
                sql.closeDB();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sql.closeDB();
        return false;
    }
}