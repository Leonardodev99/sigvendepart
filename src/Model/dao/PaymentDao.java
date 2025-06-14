package Model.dao;

import java.util.List;

import Model.entities.Payment;

public interface PaymentDao {
	void insert(Payment obj);
	void update(Payment obj);
	void deleteById(int id);
	Payment findById(int id);
	List<Payment> findAll();
}
