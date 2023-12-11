package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DbException;
import model.dao.DepartamentDao;
import model.entities.Departament;

public class DepartamentDaoJDBC implements DepartamentDao {
	
	private Connection conn;
	
	public DepartamentDaoJDBC(Connection conn) {
		this.conn= conn;
		
	}

	@Override
	public void insert(Departament obj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO departament (name) VALUES (?)",
					Statement.RETURN_GENERATED_KEYS
					);
			st.setString(1, obj.getName());
			
			int rowsAffected = st.executeUpdate();
			if(rowsAffected>0) {
				rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
			}
			else {
				throw new DbException("Unexpected error! No row affected!");
			}
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
			
		}
		
	}

	@Override
	public void update(Departament obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"UPDATE departament "
					+"SET name = ? "
					+"WHERE id = ?",
					Statement.RETURN_GENERATED_KEYS
					);
			st.setString(1, obj.getName());
			st.executeUpdate();
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"delete FROM departament "
					+"WHERE id = ?"
					);
			st.setInt(1, id);
			int rowsAffected = st.executeUpdate();
			if(rowsAffected==0) {
				throw new DbException("Id does not exist");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}

	@Override
	public Departament findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT departament.* "
					+ "FROM departament "
					+ "WHERE id =?"
					);
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Departament dep = instantiateDepartament(rs);
				return dep;
			}
			return null;
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		
	}
	private Departament instantiateDepartament (ResultSet rs) throws SQLException {
		Departament obj = new Departament();
		obj.setId(rs.getInt("id"));
		obj.setName(rs.getString("name"));
		return obj;
		
	}

	@Override
	public List<Departament> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT departament.* "
					+ "FROM departament "
					+ "ORDER BY name"
					);
			rs = st.executeQuery();
			List<Departament> list = new ArrayList<>();
			
		}
		catch(SQLException e ) {
			throw new DbException(e.getMessage());
		}
		return null;
	}

}
