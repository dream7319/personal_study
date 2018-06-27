package com.lierl.other.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-06-25 10:36
 **/
public class MysqlTest {
	public static void main(String[] args) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://139.199.129.251:3306/python?useSSL=false", "root",
				"root");
		PreparedStatement preparedStatement = connection.prepareStatement("select * from t_user ");
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next()){
			System.out.println(resultSet.getInt(1));
			System.out.println(resultSet.getString(2));
		}


	}
}
