package Model.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.dao.SupplierDao;
import Model.entities.Supplier;
import db.DB;
import db.DbException;

public class SupplierDaoJDBC implements SupplierDao {

	private Connection conn;

	public SupplierDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Supplier obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO supplies (name, nif, phone, cel, email, address, site) VALUES (?, ?, ?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS
			);

			st.setString(1, obj.getName());
			st.setString(2, obj.getNif());
			st.setString(3, obj.getPhone());
			st.setString(4, obj.getCel());
			st.setString(5, obj.getEmail());
			st.setString(6, obj.getAddress());
			st.setString(7, obj.getSite());

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
	public void update(Supplier obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE supplies SET name = ?, nif = ?, phone = ?, cel = ?, email = ?, address = ?, site = ? WHERE id_supplier = ?"
			);

			st.setString(1, obj.getName());
			st.setString(2, obj.getNif());
			st.setString(3, obj.getPhone());
			st.setString(4, obj.getCel());
			st.setString(5, obj.getEmail());
			st.setString(6, obj.getAddress());
			st.setString(7, obj.getSite());
			st.setInt(8, obj.getId());

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
			st = conn.prepareStatement("DELETE FROM supplies WHERE id_supplier = ?");
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Supplier findById(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM supplies WHERE id_supplier = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				return instantiateSupplier(rs);
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
	public List<Supplier> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM supplies ORDER BY name");
			rs = st.executeQuery();

			List<Supplier> list = new ArrayList<>();
			while (rs.next()) {
				list.add(instantiateSupplier(rs));
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Supplier instantiateSupplier(ResultSet rs) throws SQLException {
		Supplier obj = new Supplier();
		obj.setId(rs.getInt("id_supplier"));
		obj.setName(rs.getString("name"));
		obj.setNif(rs.getString("nif"));
		obj.setPhone(rs.getString("phone"));
		obj.setCel(rs.getString("cel"));
		obj.setEmail(rs.getString("email"));
		obj.setAddress(rs.getString("address"));
		obj.setSite(rs.getString("site"));
		return obj;
	}
}
