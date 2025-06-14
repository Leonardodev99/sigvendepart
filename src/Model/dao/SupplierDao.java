package Model.dao;

import java.util.List;

import Model.entities.Supplier;

public interface SupplierDao {
	void insert(Supplier obj);
	void update(Supplier obj);
	void deleteById(int id);
	Supplier findById(int id);
	List<Supplier> findAll();
}
