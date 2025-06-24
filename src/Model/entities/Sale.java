package Model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sale implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Customer customer;
	private Employee employee;
	private LocalDate dateSale;
	private BigDecimal totalBanco;
	private List<SaleItem> items = new ArrayList<>();

	public Sale() {
	}

	public Sale(Integer id, Customer customer, Employee employee, LocalDate dateSale) {
		this.id = id;
		this.customer = customer;
		this.employee = employee;
		this.dateSale = dateSale;
	}

	// Getters e Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LocalDate getDateSale() {
		return dateSale;
	}

	public void setDateSale(LocalDate dateSale) {
		this.dateSale = dateSale;
	}

	public List<SaleItem> getItems() {
		return items;
	}

	public void addItem(SaleItem item) {
		items.add(item);
	}

	public void removeItem(SaleItem item) {
		items.remove(item);
	}
	
	public BigDecimal getTotalBanco() {
		return totalBanco;
	}

	public void setTotalBanco(BigDecimal totalBanco) {
		this.totalBanco = totalBanco;
	}

	// Calcula total com base nos itens da venda
	public BigDecimal getTotal() {
		if (items == null || items.isEmpty()) {
			return totalBanco != null ? totalBanco : BigDecimal.ZERO;
		}
		return items.stream()
			.map(SaleItem::getSubtotal)
			.reduce(BigDecimal.ZERO, BigDecimal::add);
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Sale other = (Sale) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Sale [id=" + id + ", customer=" + customer.getName() +
		       ", employee=" + employee.getName() +
		       ", dateSale=" + dateSale +
		       ", total=" + getTotal() + "]";
	}
}
