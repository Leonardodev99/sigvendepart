package application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import Model.dao.CategoryDao;
import Model.dao.CustomerDao;
import Model.dao.DaoFactory;
import Model.dao.EmployeeDao;
import Model.dao.PaymentDao;
import Model.dao.ProductDao;
import Model.dao.SaleDao;
import Model.dao.SaleItemDao;
import Model.dao.StockMovementDao;
import Model.dao.SupplierDao;
import Model.entities.Category;
import Model.entities.Customer;
import Model.entities.Employee;
import Model.entities.Payment;
import Model.entities.Product;
import Model.entities.Sale;
import Model.entities.SaleItem;
import Model.entities.StockMovement;
import Model.entities.Supplier;


public class Program {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		CategoryDao categoryDao = DaoFactory.createCategoryDao();
		Category category = categoryDao.findById(2);
		
		
		CustomerDao customerDao = DaoFactory.createCustomerDao();
		Customer customer = customerDao.findById(2);
		
		EmployeeDao employeeDao = DaoFactory.createEmployeeDao();
		Employee employee = employeeDao.findById(2);
		
		SupplierDao supplierDao = DaoFactory.createSupplierDao();
		Supplier supplier = supplierDao.findById(1);
		
		ProductDao productDao = DaoFactory.createProductDao();
		System.out.println("\n=== TESTE 1: product findById ====");
		Product product = productDao.findById(1);
		
		SaleDao saleDao = DaoFactory.createSaleDao();
		System.out.println("\n=== TESTE 1: sale findById ====");
		Sale sale = saleDao.findById(1);
		
		System.out.println("\n=== TESTE 1: sale-item findById ====");
		SaleItemDao saleItemDao = DaoFactory.createSaleItemDao();
		SaleItem saleItem = saleItemDao.findById(2);
		
		System.out.println("\n=== TESTE 1: payment findById ====");
		PaymentDao paymentDao = DaoFactory.createPaymentDao();
		Payment payment = paymentDao.findById(2);
		
		System.out.println("\n=== TESTE 1: Stock Movement findById ====");
		StockMovementDao stockMovementDao = DaoFactory.createStockMovementDao();
		StockMovement stockMovement = stockMovementDao.findById(1);
		System.out.println(stockMovement);
		
		System.out.println("\n=== TESTE 2: Stock Movement findAll ====");
		List<StockMovement> list = stockMovementDao.findAll();
		for(StockMovement obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TESTE 5: Stock Movement delete ====");
		System.out.println("Enter with id: ");
		int id = sc.nextInt();
		stockMovementDao.deleteById(id);
		System.out.println("Done, delete completed!");
		/*System.out.println("\n=== TESTE 4: Stock Movement update ====");
		stockMovement = stockMovementDao.findById(3);
		stockMovement.setQuantity(5);
		stockMovementDao.update(stockMovement);
		System.out.println("Done, data updated!");
		
		System.out.println("\n=== TESTE 3: Stock Movement insert ====");
		stockMovement = new StockMovement(null, product, "exit", 10, LocalDateTime.now());
		stockMovementDao.insert(stockMovement);
		System.out.println(stockMovement);
		
		
		System.out.println(payment);
		
		System.out.println("\n=== TESTE 2: payment findAll ====");
		List<Payment> list = paymentDao.findAll();
		for(Payment obj : list) {
			System.out.println(obj);
		}
		
		System.out.println("\n=== TESTE 5: payment Delete ====");
		System.out.println("Enter with id: ");
		int id = sc.nextInt();
		paymentDao.deleteById(id);
		System.out.println("Delte completed");
		
		System.out.println("\n=== TESTE 4: payment update ====");
		payment = paymentDao.findById(3);
		payment.setValuePay(new BigDecimal("2500.00"));
		paymentDao.update(payment);
		System.out.println("Done, data updated!");
		System.out.println("\n=== TESTE 3: payment insert ====");
		payment = new Payment(null, sale, new BigDecimal("2000.00"),
				"card",
				"pending",
				LocalDateTime.now());
		paymentDao.insert(payment);
		System.out.println(payment);
		

			
			// Movimentação de entrada
			StockMovement movement = new StockMovement(
				1,
				product,
				"entry",
				20,
				LocalDateTime.now()
			);
			System.out.println(movement);
			
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
