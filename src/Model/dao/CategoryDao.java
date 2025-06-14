package Model.dao;

import java.util.List;

import Model.entities.Category;

public interface CategoryDao {
	void insert(Category obj);
	void update(Category obj);
	void deleteById(int id);
	Category findById(int id);
	List<Category> findAll();
}
