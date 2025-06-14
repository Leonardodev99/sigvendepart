package Model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class StockMovement implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Product product;
	private String type; // "entry" ou "exit"
	private Integer quantity;
	private LocalDateTime date;

	public StockMovement() {
	}

	public StockMovement(Integer id, Product product, String type, Integer quantity, LocalDateTime date) {
		this.id = id;
		this.product = product;
		this.type = type;
		this.quantity = quantity;
		this.date = date;
	}

	// Getters e Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (!type.equals("entry") && !type.equals("exit")) {
			throw new IllegalArgumentException("Tipo de movimentação inválido. Use 'entry' ou 'exit'.");
		}
		this.type = type;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		if (quantity < 0) {
			throw new IllegalArgumentException("Quantidade não pode ser negativa.");
		}
		this.quantity = quantity;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
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
		StockMovement other = (StockMovement) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "StockMovement [id=" + id + ", type=" + type + ", quantity=" + quantity + ", date=" + date + "]";
	}
}
