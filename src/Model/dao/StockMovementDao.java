package Model.dao;

import java.util.List;

import Model.entities.StockMovement;

public interface StockMovementDao {
	void insert(StockMovement obj);
	void update(StockMovement obj);
	void deleteById(int id);
	StockMovement findById(int id);
	List<StockMovement> findAll();
}
