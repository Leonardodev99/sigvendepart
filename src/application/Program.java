package application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

import Model.entities.Employee;


public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		Employee obj = new Employee(1, "Arnald Black", "donalt@gmail.com", "0099753LA02112", "Rangel", LocalDate.of(1990, 5, 13), "931456789", 'M', LocalDate.of(2024, 6, 1), new BigDecimal("479000"), "123ASDF", "123456");
	
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
