package application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import Model.dao.*;
import Model.dao.impl.SaleDaoJDBC;
import Model.entities.*;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		CustomerDao customerDao = DaoFactory.createCustomerDao();
		Customer customer = customerDao.findById(2);

		EmployeeDao employeeDao = DaoFactory.createEmployeeDao();
		Employee employee = employeeDao.findById(2);

		ProductDao productDao = DaoFactory.createProductDao();
		Product prod1 = productDao.findById(1);
		Product prod2 = productDao.findById(2);

		SaleDao saleDao = DaoFactory.createSaleDao();
		/*SaleItemDao saleItemDao = DaoFactory.createSaleItemDao();

		// Criar nova venda
		System.out.println("\n=== TESTE: Inserir nova venda com vários itens ===");
		Sale novaVenda = new Sale(null, customer, employee, LocalDate.now());
		saleDao.insert(novaVenda); // Gera ID no banco com total = 0

		// Criar e inserir itens
		SaleItem item1 = new SaleItem(null, novaVenda, prod1, 2, prod1.getPrice());
		SaleItem item2 = new SaleItem(null, novaVenda, prod2, 1, prod2.getPrice());

		saleItemDao.insert(item1);
		saleItemDao.insert(item2);

		// Atualizar total após inserir itens
		((SaleDaoJDBC) saleDao).updateTotalFromItems(novaVenda);

		// Exibir venda com total atualizado
		System.out.println("Venda registrada:");
		System.out.println(saleDao.findById(novaVenda.getId()));

		// Criar pagamento com valor automático
		PaymentDao paymentDao = DaoFactory.createPaymentDao();
		Payment pagamento = new Payment(null, novaVenda, null, null, null, null);
		paymentDao.insert(pagamento);

		System.out.println("Pagamento registrado:");
		System.out.println(pagamento);*/
		
		System.out.println("\n=== PRODUTOS PRESTES A EXPIRAR (próximas 2 semanas) ===");
		List<Product> expiring = productDao.findProductsExpiringSoon();
		if (expiring.isEmpty()) {
			System.out.println("Nenhum produto prestes a vencer.");
		} else {
			expiring.forEach(p -> System.out.printf("Produto: %s | Validade: %s\n", p.getName(), p.getDateExpiration()));
		}


		// Relatórios
		/*System.out.println("\n=== RELATÓRIO DE VENDAS DO DIA ===");
		List<Sale> vendasHoje = saleDao.findByDate(LocalDate.now());
		vendasHoje.forEach(System.out::println);

		System.out.println("\n=== RELATÓRIO DE VENDAS DO MÊS ===");
		List<Sale> vendasMes = saleDao.findByMonth(2025, 2);
		vendasMes.forEach(System.out::println);*/

		sc.close();
	}
}
