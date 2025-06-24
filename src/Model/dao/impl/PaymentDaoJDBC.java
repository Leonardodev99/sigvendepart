package Model.dao.impl;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import Model.dao.PaymentDao;
import Model.entities.Payment;
import Model.entities.Sale;
import db.DB;
import db.DbException;

import java.math.BigDecimal;

public class PaymentDaoJDBC implements PaymentDao {

	private Connection conn;

	public PaymentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Payment obj) {
		PreparedStatement st = null;

		try {
			// Se valor de pagamento for nulo, buscar o total da venda do banco
			if (obj.getValuePay() == null && obj.getSale() != null && obj.getSale().getId() != null) {
				PreparedStatement stTotal = null;
				ResultSet rsTotal = null;
				try {
					stTotal = conn.prepareStatement("SELECT total FROM sales WHERE id_sale = ?");
					stTotal.setInt(1, obj.getSale().getId());
					rsTotal = stTotal.executeQuery();
					if (rsTotal.next()) {
						obj.setValuePay(rsTotal.getBigDecimal("total"));
					} else {
						obj.setValuePay(BigDecimal.ZERO);
					}
				} catch (SQLException e) {
					throw new DbException("Erro ao buscar total da venda: " + e.getMessage());
				} finally {
					DB.closeResultSet(rsTotal);
					DB.closeStatement(stTotal);
				}
			}

			// Inserção do pagamento
			st = conn.prepareStatement(
				"INSERT INTO payments (id_sale, value_pay, method_payment, status_payment, date_payment) " +
				"VALUES (?, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS
			);
			st.setInt(1, obj.getSale().getId());
			st.setBigDecimal(2, obj.getValuePay());
			st.setString(3, obj.getMethodPayment());
			st.setString(4, obj.getStatusPayment());
			st.setTimestamp(5, Timestamp.valueOf(obj.getDatePayment() != null ? obj.getDatePayment() : LocalDateTime.now()));

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
	public void update(Payment obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
				"UPDATE payments SET id_sale=?, value_pay=?, method_payment=?, status_payment=?, date_payment=? " +
				"WHERE id_payment = ?"
			);
			st.setInt(1, obj.getSale().getId());
			st.setBigDecimal(2, obj.getValuePay());
			st.setString(3, obj.getMethodPayment());
			st.setString(4, obj.getStatusPayment());
			st.setTimestamp(5, Timestamp.valueOf(obj.getDatePayment()));
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
			st = conn.prepareStatement("DELETE FROM payments WHERE id_payment = ?");
			st.setInt(1, id);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Payment findById(int id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT p.* FROM payments p WHERE p.id_payment = ?"
			);
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Sale sale = new Sale();
				sale.setId(rs.getInt("id_sale"));

				return instantiatePayment(rs, sale);
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
	public List<Payment> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM payments ORDER BY date_payment DESC");
			rs = st.executeQuery();

			List<Payment> list = new ArrayList<>();
			while (rs.next()) {
				Sale sale = new Sale();
				sale.setId(rs.getInt("id_sale"));

				Payment pay = instantiatePayment(rs, sale);
				list.add(pay);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Payment instantiatePayment(ResultSet rs, Sale sale) throws SQLException {
		Payment obj = new Payment();
		obj.setId(rs.getInt("id_payment"));
		obj.setSale(sale);
		obj.setValuePay(rs.getBigDecimal("value_pay"));
		obj.setMethodPayment(rs.getString("method_payment"));
		obj.setStatusPayment(rs.getString("status_payment"));
		obj.setDatePayment(rs.getTimestamp("date_payment").toLocalDateTime());
		return obj;
	}
}
