package Model.dao;

import java.util.List;

import Model.entities.Sale;

public interface SaleDao {
	void insert(Sale obj);
	void update(Sale obj);
	void deleteById(int id);
	Sale findById(int id);
	List<Sale> findAll();
}
