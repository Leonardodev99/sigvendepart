package Model.dao;

import Model.dao.impl.CategoryDaoJDBC;
import Model.dao.impl.CustomerDaoJDBC;
import Model.dao.impl.EmployeeDaoJDBC;
import Model.dao.impl.PaymentDaoJDBC;
import Model.dao.impl.ProductDaoJDBC;
import Model.dao.impl.SaleDaoJDBC;
import Model.dao.impl.SaleItemDaoJDBC;
import Model.dao.impl.StockMovementDaoJDBC;
import Model.dao.impl.SupplierDaoJDBC;
import db.DB;

public class DaoFactory {
	
	public static CategoryDao createCategoryDao() {
		return new CategoryDaoJDBC(DB.getConnection());
	}
	public static CustomerDao createCustomerDao() {
		return new CustomerDaoJDBC(DB.getConnection());
	}
	public static EmployeeDao createEmployeeDao() {
		return new EmployeeDaoJDBC(DB.getConnection());
	}
	public static SupplierDao createSupplierDao() {
		return new SupplierDaoJDBC(DB.getConnection());
	}
	public static ProductDao createProductDao() {
		return new ProductDaoJDBC(DB.getConnection());
	}
	public static SaleDao createSaleDao() {
		return new SaleDaoJDBC(DB.getConnection());
	}
	public static SaleItemDao createSaleItemDao() {
		return new SaleItemDaoJDBC(DB.getConnection());
	}
	public static StockMovementDao createStockMovementDao() {
		return new StockMovementDaoJDBC(DB.getConnection());
	}
	public static PaymentDao createPaymentDao() {
		return new PaymentDaoJDBC(DB.getConnection());
	}
	

}
