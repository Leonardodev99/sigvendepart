package Model.dao.impl;

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

import Model.dao.ProductDao;
import Model.entities.Category;
import Model.entities.Product;
import Model.entities.Supplier;
import db.DB;
import db.DbException;

public class ProductDaoJDBC implements ProductDao {

	private Connection conn;

	public ProductDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Product obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO products (name, date_manufacture, date_expiration, quantity_stock, price, description, id_category, id_supplier) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS
			);
			st.setString(1, obj.getName());
			st.setDate(2, Date.valueOf(obj.getDateManufacture()));
			st.setDate(3, Date.valueOf(obj.getDateExpiration()));
			st.setInt(4, obj.getQuantityStock());
			st.setBigDecimal(5, obj.getPrice());
			st.setString(6, obj.getDescription());
			st.setInt(7, obj.getCategory().getId());
			st.setInt(8, obj.getSupplier().getId());

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
	public void update(Product obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE products SET name = ?, date_manufacture = ?, date_expiration = ?, quantity_stock = ?, price = ?, description = ?, id_category = ?, id_supplier = ? " +
				"WHERE id_product = ?"
			);
			st.setString(1, obj.getName());
			st.setDate(2, Date.valueOf(obj.getDateManufacture()));
			st.setDate(3, Date.valueOf(obj.getDateExpiration()));
			st.setInt(4, obj.getQuantityStock());
			st.setBigDecimal(5, obj.getPrice());
			st.setString(6, obj.getDescription());
			st.setInt(7, obj.getCategory().getId());
			st.setInt(8, obj.getSupplier().getId());
			st.setInt(9, obj.getId());

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
			st = conn.prepareStatement("DELETE FROM products WHERE id_product = ?");
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Product findById(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT p.*, c.name AS category_name, s.name AS supplier_name, s.nif, s.phone, s.cel, s.email, s.address, s.site " +
				"FROM products p " +
				"JOIN categories c ON p.id_category = c.id_category " +
				"JOIN supplies s ON p.id_supplier = s.id_supplier " +
				"WHERE p.id_product = ?"
			);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Category cat = new Category(rs.getInt("id_category"), rs.getString("category_name"));
				Supplier sup = new Supplier();
				sup.setId(rs.getInt("id_supplier"));
				sup.setName(rs.getString("supplier_name"));
				sup.setNif(rs.getString("nif"));
				sup.setPhone(rs.getString("phone"));
				sup.setCel(rs.getString("cel"));
				sup.setEmail(rs.getString("email"));
				sup.setAddress(rs.getString("address"));
				sup.setSite(rs.getString("site"));

				Product obj = instantiateProduct(rs, cat, sup);
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
	public List<Product> findAll() {

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT p.*, c.name AS category_name, s.name AS supplier_name, s.nif, s.phone, s.cel, s.email, s.address, s.site " +
				"FROM products p " +
				"JOIN categories c ON p.id_category = c.id_category " +
				"JOIN supplies s ON p.id_supplier = s.id_supplier " +
				"ORDER BY p.name"
			);
			rs = st.executeQuery();

			List<Product> list = new ArrayList<>();
			Map<Integer, Category> catMap = new HashMap<>();
			Map<Integer, Supplier> supMap = new HashMap<>();

			while (rs.next()) {
				int catId = rs.getInt("id_category");
				Category cat = catMap.get(catId);
				if (cat == null) {
					cat = new Category(catId, rs.getString("category_name"));
					catMap.put(catId, cat);
				}


				int supId = rs.getInt("id_supplier");
				Supplier sup = supMap.get(supId);
				if (sup == null) {
					cat = new Category(supId, rs.getString("supplier_name"));
					supMap.put(supId, sup);
				}

				Product prod = instantiateProduct(rs, cat, sup);
				list.add(prod);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Product instantiateProduct(ResultSet rs, Category category, Supplier supplier) throws SQLException {
		Product obj = new Product();
		obj.setId(rs.getInt("id_product"));
		obj.setName(rs.getString("name"));
		obj.setDateManufacture(rs.getDate("date_manufacture").toLocalDate());
		obj.setDateExpiration(rs.getDate("date_expiration").toLocalDate());
		obj.setQuantityStock(rs.getInt("quantity_stock"));
		obj.setPrice(rs.getBigDecimal("price"));
		obj.setDescription(rs.getString("description"));
		obj.setCategory(category);
		obj.setSupplier(supplier);
		return obj;
	}
	
	private Product instantiateProduct(ResultSet rs) throws SQLException {
		Product obj = new Product();
		obj.setId(rs.getInt("id_product"));
		obj.setName(rs.getString("name"));
		obj.setDateManufacture(rs.getDate("date_manufacture").toLocalDate());
		obj.setDateExpiration(rs.getDate("date_expiration").toLocalDate());
		obj.setQuantityStock(rs.getInt("quantity_stock"));
		obj.setPrice(rs.getBigDecimal("price"));
		obj.setDescription(rs.getString("description"));
		// Deixa category e supplier nulos nesse método simplificado
		return obj;
	}

	
	@Override
	public List<Product> findProductsExpiringSoon() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT * FROM products " +
				"WHERE date_expiration BETWEEN ? AND ? " +
				"ORDER BY date_expiration ASC"
			);

			LocalDate today = LocalDate.now();
			LocalDate limit = today.plusDays(14);

			st.setDate(1, Date.valueOf(today));
			st.setDate(2, Date.valueOf(limit));

			rs = st.executeQuery();

			List<Product> list = new ArrayList<>();
			while (rs.next()) {
				Product p = instantiateProduct(rs);
				list.add(p);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException("Erro ao buscar produtos prestes a expirar: " + e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}


}
