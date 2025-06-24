package Model.dao.impl;

import java.sql.*;
import java.util.*;

import Model.dao.StockMovementDao;
import Model.entities.Product;
import Model.entities.StockMovement;
import db.DB;
import db.DbException;

public class StockMovementDaoJDBC implements StockMovementDao {

	private Connection conn;

	public StockMovementDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(StockMovement obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO stock_movements (id_product, type, quantity, date) VALUES (?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS
			);
			st.setInt(1, obj.getProduct().getId());
			st.setString(2, obj.getType());
			st.setInt(3, obj.getQuantity());
			st.setTimestamp(4, Timestamp.valueOf(obj.getDate()));

			int rows = st.executeUpdate();
			if (rows > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					obj.setId(rs.getInt(1));
				}
				DB.closeResultSet(rs);

				
				Product product = obj.getProduct();
				if (obj.getType().equals("entry")) {
					product.setQuantityStock(product.getQuantityStock() + obj.getQuantity());
				} else {
					product.setQuantityStock(product.getQuantityStock() - obj.getQuantity());
				}
				new ProductDaoJDBC(conn).update(product);
			} else {
				throw new DbException("Erro: nenhuma linha inserida.");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}


	@Override
	public void update(StockMovement obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE stock_movements SET id_product = ?, type = ?, quantity = ?, date = ? WHERE id_movement = ?"
			);
			st.setInt(1, obj.getProduct().getId());
			st.setString(2, obj.getType());
			st.setInt(3, obj.getQuantity());
			st.setTimestamp(4, Timestamp.valueOf(obj.getDate()));
			st.setInt(5, obj.getId());

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(int id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM stock_movements WHERE id_movement = ?");
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public StockMovement findById(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT sm.*, p.name AS product_name FROM stock_movements sm " +
				"JOIN products p ON sm.id_product = p.id_product " +
				"WHERE sm.id_movement = ?"
			);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id_product"));
				product.setName(rs.getString("product_name"));

				return instantiateMovement(rs, product);
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<StockMovement> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT sm.*, p.name AS product_name FROM stock_movements sm " +
				"JOIN products p ON sm.id_product = p.id_product " +
				"ORDER BY sm.date DESC"
			);
			rs = st.executeQuery();

			List<StockMovement> list = new ArrayList<>();
			while (rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id_product"));
				product.setName(rs.getString("product_name"));

				StockMovement move = instantiateMovement(rs, product);
				list.add(move);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private StockMovement instantiateMovement(ResultSet rs, Product product) throws SQLException {
		StockMovement obj = new StockMovement();
		obj.setId(rs.getInt("id_movement"));
		obj.setProduct(product);
		obj.setType(rs.getString("type"));
		obj.setQuantity(rs.getInt("quantity"));
		obj.setDate(rs.getTimestamp("date").toLocalDateTime());
		return obj;
	}
}
