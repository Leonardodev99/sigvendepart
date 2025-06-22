package Model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Model.dao.SaleItemDao;
import Model.entities.Product;
import Model.entities.Sale;
import Model.entities.SaleItem;
import db.DB;
import db.DbException;

public class SaleItemDaoJDBC implements SaleItemDao {

	private Connection conn;

	public SaleItemDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(SaleItem obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"INSERT INTO sale_items (id_sale, id_product, quantity, unit_price, subtotal) " +
				"VALUES (?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS
			);
			st.setInt(1, obj.getSale().getId());
			st.setInt(2, obj.getProduct().getId());
			st.setInt(3, obj.getQuantity());
			st.setBigDecimal(4, obj.getUnitPrice());
			st.setBigDecimal(5, obj.getSubtotal());

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
	public void update(SaleItem obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE sale_items SET id_sale=?, id_product=?, quantity=?, unit_price=?, subtotal=? " +
				"WHERE id_sale_item=?"
			);
			st.setInt(1, obj.getSale().getId());
			st.setInt(2, obj.getProduct().getId());
			st.setInt(3, obj.getQuantity());
			st.setBigDecimal(4, obj.getUnitPrice());
			st.setBigDecimal(5, obj.getSubtotal());
			st.setInt(6, obj.getId());

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
			st = conn.prepareStatement("DELETE FROM sale_items WHERE id_sale_item = ?");
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public SaleItem findById(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT si.*, p.name AS product_name FROM sale_items si " +
				"JOIN products p ON si.id_product = p.id_product " +
				"WHERE si.id_sale_item = ?"
			);
			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Product prod = new Product();
				prod.setId(rs.getInt("id_product"));
				prod.setName(rs.getString("product_name"));

				Sale sale = new Sale();
				sale.setId(rs.getInt("id_sale"));

				SaleItem item = instantiateSaleItem(rs, sale, prod);
				return item;
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
	public List<SaleItem> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT si.*, p.name AS product_name FROM sale_items si " +
				"JOIN products p ON si.id_product = p.id_product " +
				"ORDER BY si.id_sale_item"
			);
			rs = st.executeQuery();

			List<SaleItem> list = new ArrayList<>();

			while (rs.next()) {
				Product prod = new Product();
				prod.setId(rs.getInt("id_product"));
				prod.setName(rs.getString("product_name"));

				Sale sale = new Sale();
				sale.setId(rs.getInt("id_sale"));

				SaleItem item = instantiateSaleItem(rs, sale, prod);
				list.add(item);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private SaleItem instantiateSaleItem(ResultSet rs, Sale sale, Product product) throws SQLException {
		SaleItem obj = new SaleItem();
		obj.setId(rs.getInt("id_sale_item"));
		obj.setSale(sale);
		obj.setProduct(product);
		obj.setQuantity(rs.getInt("quantity"));
		obj.setUnitPrice(rs.getBigDecimal("unit_price"));
		return obj;
	}
}
