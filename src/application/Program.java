package application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Model.entities.Category;
import Model.entities.Customer;
import db.DB;

public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		//Category obj = new Category(9, "");
		Customer obj = new Customer(3, "Tiago White", "Brigada", "tiago@gmail.com", "921567890");
		System.out.println(obj);
		
	/*	Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("select * from Supplies");
			while (rs.next()) {
				System.out.println(rs.getInt("id_supplier") + ", " + rs.getString("name")
				+ ", " + rs.getString("nif") + ", " + rs.getString("phone") + ", "
				+ rs.getString("cel") + ", "  + rs.getString("email") + ", " + 
				rs.getString("address") + ", " + rs.getString("site"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
			DB.closeConnection();
		}*/
		sc.close();

	}

}
