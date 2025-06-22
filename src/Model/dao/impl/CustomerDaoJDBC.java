package Model.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.dao.CustomerDao;
import Model.entities.Customer;
import db.DB;
import db.DbException;

public class CustomerDaoJDBC implements CustomerDao {

	private Connection conn;

	public CustomerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Customer obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO customers (name, address, email, phone) VALUES (?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS
			);

			st.setString(1, obj.getName());
			st.setString(2, obj.getAddress());
			st.setString(3, obj.getEmail());
			st.setString(4, obj.getPhone());

			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					obj.setId(rs.getInt(1));
				}
				DB.closeResultSet(rs);
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
	public void update(Customer obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE customers SET name = ?, address = ?, email = ?, phone = ? WHERE id_customer = ?"
			);

			st.setString(1, obj.getName());
			st.setString(2, obj.getAddress());
			st.setString(3, obj.getEmail());
			st.setString(4, obj.getPhone());
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
			st = conn.prepareStatement(
				"DELETE FROM customers WHERE id_customer = ?"
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
	public Customer findById(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
				"SELECT * FROM customers WHERE id_customer = ?"
			);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Customer obj = instantiateCustomer(rs);
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
	public List<Customer> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT * FROM customers ORDER BY name");
			rs = st.executeQuery();

			List<Customer> list = new ArrayList<>();
			while (rs.next()) {
				Customer obj = instantiateCustomer(rs);
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

	private Customer instantiateCustomer(ResultSet rs) throws SQLException {
		Customer obj = new Customer();
		obj.setId(rs.getInt("id_customer"));
		obj.setName(rs.getString("name"));
		obj.setAddress(rs.getString("address"));
		obj.setEmail(rs.getString("email"));
		obj.setPhone(rs.getString("phone"));
		return obj;
	}
}
