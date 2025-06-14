package Model.dao;

import java.util.List;

import Model.entities.Employee;

public interface EmployeeDao {
	void insert(Employee obj);
	void update(Employee obj);
	void deleteById(int id);
	Employee findById(int id);
	List<Employee> findAll();
}
