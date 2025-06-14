package Model.dao;

import java.util.List;

import Model.entities.Product;

public interface ProductDao {
	void insert(Product obj);
	void update(Product obj);
	void deleteById(int id);
	Product findById(int id);
	List<Product> findAll();
}
