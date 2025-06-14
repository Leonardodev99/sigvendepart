package Model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Sale implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Customer customer;
	private Employee employee;
	private LocalDate dateSale;
	private BigDecimal total;

	public Sale() {
	}

	public Sale(Integer id, Customer customer, Employee employee, LocalDate dateSale, BigDecimal total) {
		this.id = id;
		this.customer = customer;
		this.employee = employee;
		this.dateSale = dateSale;
		this.total = total;
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

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Sale [id=" + id + ", customer=" + customer.getName() + ", employee=" + employee.getName()
				+ ", dateSale=" + dateSale + ", total=" + total + "]";
	}
}
