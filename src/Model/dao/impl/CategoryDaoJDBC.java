package Model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.dao.CategoryDao;
import Model.entities.Category;
import db.DB;
import db.DbException;

public class CategoryDaoJDBC implements CategoryDao {

	private Connection conn;

	public CategoryDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Category obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO categories (name) VALUES (?)",
				Statement.RETURN_GENERATED_KEYS
			);
			st.setString(1, obj.getName());

			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					obj.setId(rs.getInt(1));
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Erro inesperado: nenhuma linha foi inserida.");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Category obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE categories SET name = ? WHERE id_category = ?"
			);
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());

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
			st = conn.prepareStatement(
				"DELETE FROM categories WHERE id_category = ?"
			);
			st.setInt(1, id);

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Category findById(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM categories WHERE id_category = ?"
			);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Category obj = new Category();
				obj.setId(rs.getInt("id_category"));
				obj.setName(rs.getString("name"));
				return obj;
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
	public List<Category> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM categories ORDER BY name");
			rs = st.executeQuery();

			List<Category> list = new ArrayList<>();
			while (rs.next()) {
				Category obj = new Category();
				obj.setId(rs.getInt("id_category"));
				obj.setName(rs.getString("name"));
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}
