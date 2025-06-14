package Model.dao;

import Model.dao.impl.CategoryDaoJDBC;
import db.DB;

public class DaoFactory {
	
	public static CategoryDao createCategoryDao() {
		return new CategoryDaoJDBC(DB.getConnection());
	}

}
