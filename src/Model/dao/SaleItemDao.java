package Model.dao;

import java.util.List;

import Model.entities.SaleItem;

public interface SaleItemDao {
	void insert(SaleItem obj);
	void update(SaleItem obj);
	void deleteById(int id);
	SaleItem findById(int id);
	List<SaleItem> findAll();
}
