package Model.dao;

import java.util.List;

import Model.entities.Customer;

public interface CustomerDao {

	void insert(Customer obj);
	void update(Customer obj);
	void deleteById(int id);
	Customer findById(int id);
	List<Customer> findAll();
}
