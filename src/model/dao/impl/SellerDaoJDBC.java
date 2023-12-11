package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DbException;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn=conn;
	}

	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		ResultSet rs= null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+"(name, Sname, email, birthDate, baseSalary, fk_departament_id) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?,?)",
					Statement.RETURN_GENERATED_KEYS
					);
			st.setString(1, obj.getName());
			st.setString(2, obj.getsName());
			st.setString(3, obj.getEmail());
			st.setDate(4, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(5, obj.getBaseSalary());
			st.setInt(6, obj.getDepartament().getId());
			
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
	public void update(Seller obj) {
		PreparedStatement st = null;
		ResultSet rs= null;
		try {
			st = conn.prepareStatement(
					"UPDATE seller "
					+ "SET name = ?, Sname = ?, email = ?, birthDate = ?, baseSalary = ?, fk_departament_id  = ? "
					+"WHERE id = ?",
					Statement.RETURN_GENERATED_KEYS
					);
			st.setString(1, obj.getName());
			st.setString(2, obj.getsName());
			st.setString(3, obj.getEmail());
			st.setDate(4, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(5, obj.getBaseSalary());
			st.setInt(6, obj.getDepartament().getId());
			st.setInt(7, obj.getId());
			
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
			
			st= conn.prepareStatement(
					"delete FROM seller "
					+ "WHERE id =?"
					);
			st.setInt(1, id);
			 int rowsAffected = st.executeUpdate();
			 if(rowsAffected== 0) {
				 throw new DbException("Id does not exist");
			 }
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
						
						"SELECT seller.*, departament.name as depName "
						+"FROM seller "
						+ "INNER JOIN departament ON seller.fk_departament_id = departament.id "
						+ "WHERE seller.id = ?");
				st.setInt(1,id);
				rs = st.executeQuery();
				if(rs.next()) {
					Departament dep = instantiateDepartament(rs);
					Seller obj = instantiateSeller(rs, dep);
					return obj;
				}
				return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
			
		}
		
	}


	private Seller instantiateSeller(ResultSet rs, Departament dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("id"));
		obj.setName(rs.getString("name"));
		obj.setsName(rs.getString("Sname"));
		obj.setEmail(rs.getString("email"));
		obj.setBaseSalary(rs.getDouble("baseSalary"));
		obj.setBirthDate(rs.getDate("birthDate"));
		obj.setDepartament(dep);
		return obj;
	}

	private Departament instantiateDepartament(ResultSet rs) throws SQLException {
		Departament dep = new Departament();
		dep.setId(rs.getInt("fk_departament_id"));
		dep.setName(rs.getString("depName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
						
						"SELECT seller.*, departament.name as depName "
						+"FROM seller "
						+ "INNER JOIN departament ON seller.fk_departament_id = departament.id "
						+ "ORDER BY name");
				
				rs = st.executeQuery();
				List<Seller> list = new ArrayList<>();
				Map<Integer, Departament> map = new HashMap<>();
				while (rs.next()) {
					Departament dep = map.get(rs.getInt("fk_departament_id"));
					
					if(dep == null) {
						dep =instantiateDepartament(rs);
						map.put(rs.getInt("fk_departament_id"), dep);
					}
					
					Seller obj = instantiateSeller(rs, dep);
					list.add(obj);
					
				}
				return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
			
		}
	}

	@Override
	public List<Seller> findByDepartament(Departament departament) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
				st = conn.prepareStatement(
						
						"SELECT seller.*, departament.name as depName "
						+"FROM seller "
						+ "INNER JOIN departament ON seller.fk_departament_id = departament.id "
						+ "WHERE seller.id = ? "
						+ "ORDER BY name");
				st.setInt(1,departament.getId());
				rs = st.executeQuery();
				List<Seller> list = new ArrayList<>();
				Map<Integer, Departament> map = new HashMap<>();
				while (rs.next()) {
					Departament dep = map.get(rs.getInt("fk_departament_id"));
					
					if(dep == null) {
						dep =instantiateDepartament(rs);
						map.put(rs.getInt("fk_departament_id"), dep);
					}
					
					Seller obj = instantiateSeller(rs, dep);
					list.add(obj);
					
				}
				return list;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
			
		}
		
	}

}
