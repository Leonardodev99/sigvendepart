package application;

import java.util.List;
import java.util.Scanner;

import Model.dao.CategoryDao;
import Model.dao.DaoFactory;
import Model.entities.Category;


public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		CategoryDao categoryDao = DaoFactory.createCategoryDao();
		
		System.out.println("=== TESTE 1: category findById ====");
		Category category = categoryDao.findById(2);
		
		System.out.println(category);
		
		System.out.println("\n=== TESTE 2: category findAll ====");
		List<Category> list = categoryDao.findAll();
		for (Category obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TESTE 3: category insert ====");
		/*Category newCategory = new Category(null, "Materiais de Enfermaria");
		categoryDao.insert(newCategory);
		System.out.println("Insert! New id"+ newCategory.getId());*/
		
		System.out.println("\n=== TESTE 4: category update ====");
		/*category = categoryDao.findById(9);
		category.setName("Produtos dentários");
		categoryDao.update(category);
		System.out.println("Update completed");*/
		
		System.out.println("\n=== TESTE 5: category dalete ====");
		System.out.println("Enter id for delete test: ");
		int id = sc.nextInt();
		categoryDao.deleteById(id);
		System.out.println("Delete completed");
		
		/*Category category = new Category(1, "Medicamentos");
		
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
			
			// Item da venda
			SaleItem item = new SaleItem(
				1,
				sale,
				product,
				2,
				new BigDecimal("300.00")
			);

			System.out.println(item);
			
			// Pagamento
			Payment payment = new Payment(
				1,
				sale,
				new BigDecimal("600.00"),
				"cash",
				"paid",
				LocalDateTime.now()
			);

			System.out.println(payment);*/


		sc.close();

	}

}
