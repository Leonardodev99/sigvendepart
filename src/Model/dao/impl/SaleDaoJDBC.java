package Model.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.dao.SaleDao;
import Model.entities.Customer;
import Model.entities.Employee;
import Model.entities.Sale;
import Model.entities.SaleItem;
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
			st.setBigDecimal(4, BigDecimal.ZERO); // Inserir temporariamente 0

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

		// Após inserir os SaleItems, atualiza o total
		updateTotalFromItems(obj);
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
		sale.setTotalBanco(rs.getBigDecimal("total")); // capturar valor do banco corretamente

		return sale;
	}

	
	@Override
	public List<Sale> findByDate(LocalDate date) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT s.*, c.name AS customer_name, e.name AS employee_name " +
				"FROM sales s " +
				"JOIN customers c ON s.id_customer = c.id_customer " +
				"JOIN employees e ON s.id_employee = e.id_employee " +
				"WHERE s.date_sale = ? " +
				"ORDER BY s.id_sale"
			);
			st.setDate(1, Date.valueOf(date));
			rs = st.executeQuery();

			List<Sale> list = new ArrayList<>();
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getInt("id_customer"));
				customer.setName(rs.getString("customer_name"));

				Employee employee = new Employee();
				employee.setId(rs.getInt("id_employee"));
				employee.setName(rs.getString("employee_name"));

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

	@Override
	public List<Sale> findByMonth(int year, int month) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT s.*, c.name AS customer_name, e.name AS employee_name " +
				"FROM sales s " +
				"JOIN customers c ON s.id_customer = c.id_customer " +
				"JOIN employees e ON s.id_employee = e.id_employee " +
				"WHERE YEAR(s.date_sale) = ? AND MONTH(s.date_sale) = ? " +
				"ORDER BY s.date_sale"
			);
			st.setInt(1, year);
			st.setInt(2, month);
			rs = st.executeQuery();

			List<Sale> list = new ArrayList<>();
			while (rs.next()) {
				Customer customer = new Customer();
				customer.setId(rs.getInt("id_customer"));
				customer.setName(rs.getString("customer_name"));

				Employee employee = new Employee();
				employee.setId(rs.getInt("id_employee"));
				employee.setName(rs.getString("employee_name"));

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
	
	public void updateTotalFromItems(Sale sale) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE sales SET total = (" +
				"SELECT COALESCE(SUM(subtotal), 0) FROM sale_items WHERE id_sale = ?" +
				") WHERE id_sale = ?"
			);
			st.setInt(1, sale.getId());
			st.setInt(2, sale.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException("Erro ao atualizar total da venda: " + e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}


}
