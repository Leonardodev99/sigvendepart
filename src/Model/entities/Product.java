package Model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private LocalDate dateManufacture;
	private LocalDate dateExpiration;
	private Integer quantityStock;
	private BigDecimal price;
	private String description;
	private Category category;
	private Supplier supplier;

	public Product() {
	}

	public Product(Integer id, String name, LocalDate dateManufacture, LocalDate dateExpiration, Integer quantityStock,
			BigDecimal price, String description, Category category, Supplier supplier) {
		this.id = id;
		this.name = name;
		this.dateManufacture = dateManufacture;
		this.dateExpiration = dateExpiration;
		this.quantityStock = quantityStock;
		this.price = price;
		this.description = description;
		this.category = category;
		this.supplier = supplier;
	}

	// Getters e Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("O nome do produto não pode estar vazio.");
		}
		this.name = name;
	}

	public LocalDate getDateManufacture() {
		return dateManufacture;
	}

	public void setDateManufacture(LocalDate dateManufacture) {
		this.dateManufacture = dateManufacture;
	}

	public LocalDate getDateExpiration() {
		return dateExpiration;
	}

	public void setDateExpiration(LocalDate dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	public Integer getQuantityStock() {
		return quantityStock;
	}

	public void setQuantityStock(Integer quantityStock) {
		this.quantityStock = quantityStock;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? "" : description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
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
		Product other = (Product) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", dateManufacture=" + dateManufacture + ", dateExpiration="
				+ dateExpiration + ", quantityStock=" + quantityStock + ", price=" + price + ", description="
				+ description + ", category=" + category + ", supplier=" + supplier + "]";
	}

}
