package Model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class SaleItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Sale sale;
	private Product product;
	private Integer quantity;
	private BigDecimal unitPrice;
	private BigDecimal subtotal;

	public SaleItem() {
	}

	public SaleItem(Integer id, Sale sale, Product product, Integer quantity, BigDecimal unitPrice) {
		this.id = id;
		this.sale = sale;
		this.product = product;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.subtotal = calculateSubtotal();
	}

	private BigDecimal calculateSubtotal() {
		if (unitPrice != null && quantity != null) {
			return unitPrice.multiply(BigDecimal.valueOf(quantity));
		}
		return BigDecimal.ZERO;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
		this.subtotal = calculateSubtotal();
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
		this.subtotal = calculateSubtotal();
	}

	public BigDecimal getSubtotal() {
		if (unitPrice != null && quantity != null) {
			return unitPrice.multiply(BigDecimal.valueOf(quantity));
		}
		return BigDecimal.ZERO;
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
		SaleItem other = (SaleItem) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "SaleItem [id=" + id + ", product=" + product.getName() + ", quantity=" + quantity
				+ ", unitPrice=" + unitPrice + ", subtotal=" + subtotal + "]";
	}
}
