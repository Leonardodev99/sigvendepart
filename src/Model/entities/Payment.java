package Model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Payment implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Sale sale;
	private BigDecimal valuePay;
	private String methodPayment;   // 'cash', 'card', 'transfer', 'express'
	private String statusPayment;   // 'pending', 'paid', 'canceled'
	private LocalDateTime datePayment;

	public Payment() {
	}

	public Payment(Integer id, Sale sale, BigDecimal valuePay, String methodPayment, String statusPayment, LocalDateTime datePayment) {
		this.id = id;
		this.sale = sale;
		this.valuePay = valuePay;
		this.methodPayment = methodPayment;
		this.statusPayment = statusPayment;
		this.datePayment = datePayment;
	}

	// Getters e Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public BigDecimal getValuePay() {
		return valuePay;
	}

	public void setValuePay(BigDecimal valuePay) {
		this.valuePay = valuePay;
	}

	public String getMethodPayment() {
		return methodPayment;
	}

	public void setMethodPayment(String methodPayment) {
		if (!methodPayment.matches("cash|card|transfer|express")) {
			throw new IllegalArgumentException("Método de pagamento inválido.");
		}
		this.methodPayment = methodPayment;
	}

	public String getStatusPayment() {
		return statusPayment;
	}

	public void setStatusPayment(String statusPayment) {
		if (!statusPayment.matches("pending|paid|canceled")) {
			throw new IllegalArgumentException("Status de pagamento inválido.");
		}
		this.statusPayment = statusPayment;
	}

	public LocalDateTime getDatePayment() {
		return datePayment;
	}

	public void setDatePayment(LocalDateTime datePayment) {
		this.datePayment = datePayment;
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
		Payment other = (Payment) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", value=" + valuePay + ", method=" + methodPayment +
		       ", status=" + statusPayment + ", date=" + datePayment + "]";
	}
}
