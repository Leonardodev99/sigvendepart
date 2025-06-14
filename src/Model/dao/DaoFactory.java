package Model.dao;

import Model.dao.impl.CategoryDaoJDBC;

public class DaoFactory {
	
	public static CategoryDao createCategoryDao() {
		return new CategoryDaoJDBC();
	}

}
