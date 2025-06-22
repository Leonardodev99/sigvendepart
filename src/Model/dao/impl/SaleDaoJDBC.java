package Model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.dao.SaleDao;
import Model.entities.Customer;
import Model.entities.Employee;
import Model.entities.Sale;
import db.DB;
import db.DbException;

public class SaleDaoJDBC implements SaleDao {

	private Connection conn;

	public SaleDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Sale obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO sales (id_customer, id_employee, date_sale, total) VALUES (?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS
			);
			st.setInt(1, obj.getCustomer().getId());
			st.setInt(2, obj.getEmployee().getId());
			st.setDate(3, Date.valueOf(obj.getDateSale()));
			st.setBigDecimal(4, obj.getTotal());

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
	public void update(Sale obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE sales SET id_customer=?, id_employee=?, date_sale=?, total=? WHERE id_sale=?"
			);
			st.setInt(1, obj.getCustomer().getId());
			st.setInt(2, obj.getEmployee().getId());
			st.setDate(3, Date.valueOf(obj.getDateSale()));
			st.setBigDecimal(4, obj.getTotal());
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
			st = conn.prepareStatement("DELETE FROM sales WHERE id_sale = ?");
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Sale findById(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT s.*, c.name AS customer_name, e.name AS employee_name " +
				"FROM sales s " +
				"JOIN customers c ON s.id_customer = c.id_customer " +
				"JOIN employees e ON s.id_employee = e.id_employee " +
				"WHERE s.id_sale = ?"
			);
			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getInt("id_customer"));
				customer.setName(rs.getString("customer_name"));

				Employee employee = new Employee();
				employee.setId(rs.getInt("id_employee"));
				employee.setName(rs.getString("employee_name"));

				return instantiateSale(rs, customer, employee);
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
	public List<Sale> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT s.*, c.name AS customer_name, e.name AS employee_name " +
				"FROM sales s " +
				"JOIN customers c ON s.id_customer = c.id_customer " +
				"JOIN employees e ON s.id_employee = e.id_employee " +
				"ORDER BY s.date_sale DESC"
			);
			rs = st.executeQuery();

			List<Sale> list = new ArrayList<>();
			Map<Integer, Customer> customerMap = new HashMap<>();
			Map<Integer, Employee> employeeMap = new HashMap<>();

			while (rs.next()) {
				int custId = rs.getInt("id_customer");
				Customer customer = customerMap.get(custId);
				if (customer == null) {
					customer = new Customer();
					customer.setId(custId);
					customer.setName(rs.getString("customer_name"));
					customerMap.put(custId, customer);
				}

				int empId = rs.getInt("id_employee");
				Employee employee = employeeMap.get(empId);
				if (employee == null) {
					employee = new Employee();
					employee.setId(empId);
					employee.setName(rs.getString("employee_name"));
					employeeMap.put(empId, employee);
				}

				Sale sale = instantiateSale(rs, customer, employee);
				list.add(sale);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Sale instantiateSale(ResultSet rs, Customer customer, Employee employee) throws SQLException {
		Sale sale = new Sale();
		sale.setId(rs.getInt("id_sale"));
		sale.setCustomer(customer);
		sale.setEmployee(employee);
		sale.setDateSale(rs.getDate("date_sale").toLocalDate());
		sale.setTotal(rs.getBigDecimal("total"));
		return sale;
	}
}
