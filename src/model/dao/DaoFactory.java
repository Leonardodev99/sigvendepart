package model.dao;

import db.DB;
import model.dao.impl.DepartamentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	
	public static SellerDao creatSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	public static DepartamentDao creatDepartamentDao() {
		return new DepartamentDaoJDBC(DB.getConnection());
	}

}
