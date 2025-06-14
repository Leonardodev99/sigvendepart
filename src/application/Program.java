package application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

import Model.entities.Category;
import Model.entities.Customer;
import Model.entities.Employee;
import Model.entities.Product;
import Model.entities.Sale;
import Model.entities.StockMovement;
import Model.entities.Supplier;


public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		Category category = new Category(1, "Medicamentos");
		
		// Cliente
				Customer customer = new Customer(
					1,
					"Anna Black",
					"Rua 12 de julho",
					"anna@gmail.com",
					"922684456"
				);
		
		Employee employee = new Employee(1, "Arnald Black", "donalt@gmail.com", "0099753LA02112", "Rangel", LocalDate.of(1990, 5, 13), "931456789", 'M', LocalDate.of(2024, 6, 1), new BigDecimal("479000"), "123ASDF", "123456");
	
		System.out.println(category);
		Supplier supplier = new Supplier(
			    1,
			    "Antonio Farmaco",
			    "1234ASW45",
			    "933456789",
			    "911223456",
			    "antonio@gmail.com",
			    "Rua 11 de Novembro",
			    "www.antonio.com"
			);

			System.out.println(supplier);
			
			Product product = new Product(
					1,
					"Paracetamol",
					LocalDate.of(2023, 12, 22),
					LocalDate.of(2026, 12, 22),
					45,
					new BigDecimal("300.00"),
					"Marca indiana",
					category,
					supplier
				);
			System.out.println(product);
			
			// Movimentação de entrada
			StockMovement movement = new StockMovement(
				1,
				product,
				"entry",
				20,
				LocalDateTime.now()
			);
			System.out.println(movement);
			
			// Venda
			Sale sale = new Sale(
				1,
				customer,
				employee,
				LocalDate.of(2025, 2, 15),
				new BigDecimal("600.00")
			);

			System.out.println(sale);

		
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
