package Model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.dao.EmployeeDao;
import Model.entities.Employee;
import db.DB;
import db.DbException;

public class EmployeeDaoJDBC implements EmployeeDao {

	private Connection conn;

	public EmployeeDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Employee obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO employees (name, email, bi, address, date_birth, phone, sex, date_admission, salary, wallet_number, password) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS
			);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setString(3, obj.getBi());
			st.setString(4, obj.getAddress());
			st.setDate(5, Date.valueOf(obj.getDateBirth()));
			st.setString(6, obj.getPhone());
			st.setString(7, String.valueOf(obj.getSex()));
			st.setDate(8, Date.valueOf(obj.getDateAdmission()));
			st.setBigDecimal(9, obj.getSalary());
			st.setString(10, obj.getWalletNumber());
			st.setString(11, obj.getPassword()); // já vem com hash

			int rows = st.executeUpdate();
			if (rows > 0) {
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
	public void update(Employee obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE employees SET name=?, email=?, bi=?, address=?, date_birth=?, phone=?, sex=?, date_admission=?, salary=?, wallet_number=?, password=? " +
				"WHERE id_employee = ?"
			);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setString(3, obj.getBi());
			st.setString(4, obj.getAddress());
			st.setDate(5, Date.valueOf(obj.getDateBirth()));
			st.setString(6, obj.getPhone());
			st.setString(7, String.valueOf(obj.getSex()));
			st.setDate(8, Date.valueOf(obj.getDateAdmission()));
			st.setBigDecimal(9, obj.getSalary());
			st.setString(10, obj.getWalletNumber());
			st.setString(11, obj.getPassword());
			st.setInt(12, obj.getId());

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
			st = conn.prepareStatement("DELETE FROM employees WHERE id_employee = ?");
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Employee findById(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM employees WHERE id_employee = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				return instantiateEmployee(rs);
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
	public List<Employee> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM employees ORDER BY name");
			rs = st.executeQuery();

			List<Employee> list = new ArrayList<>();
			while (rs.next()) {
				list.add(instantiateEmployee(rs));
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Employee instantiateEmployee(ResultSet rs) throws SQLException {
		Employee obj = new Employee();
		obj.setId(rs.getInt("id_employee"));
		obj.setName(rs.getString("name"));
		obj.setEmail(rs.getString("email"));
		obj.setBi(rs.getString("bi"));
		obj.setAddress(rs.getString("address"));
		obj.setDateBirth(rs.getDate("date_birth").toLocalDate());
		obj.setPhone(rs.getString("phone"));
		obj.setSex(rs.getString("sex").charAt(0));
		obj.setDateAdmission(rs.getDate("date_admission").toLocalDate());
		obj.setSalary(rs.getBigDecimal("salary"));
		obj.setWalletNumber(rs.getString("wallet_number"));
		obj.setPassword(rs.getString("password"));
		return obj;
	}
}
