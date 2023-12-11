package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
	
		SellerDao sellerDao = DaoFactory.creatSellerDao();
		
		System.out.println(" === Test 1: seller findById ===");
		Seller seller = sellerDao.findById(4);
		System.out.println(seller);
		
		System.out.println("\n === Test 2: seller findByDepartament ===");
		Departament departament = new Departament(1, null);
		List<Seller> list = sellerDao.findByDepartament(departament);
		for(Seller obj: list) {
			System.out.println(obj);
		}
		
		System.out.println("\n === Test 3: seller findAll ===");
		list = sellerDao.findAll();
		for(Seller obj: list) {
			System.out.println(obj);
		}
		System.out.println("\n === Test 4: seller insert ===");
		Seller newSeller = new Seller(null,"Greg", "Blue","greg@gmail.com",new Date(),15000.0,departament);
		sellerDao.insert(newSeller);
		System.out.println("Inserted! New id = "+newSeller.getId());

	}

}
