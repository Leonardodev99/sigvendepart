package Model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Customer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name;
	private String address;
	private String email;
	private String phone;
	
	public Customer() {
		
	}

	public Customer(Integer id, String name, String address, String email, String phone) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null || name.trim().isEmpty()) {
	        throw new IllegalArgumentException("O nome do cliente não pode estar vazio.");
	    }
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		
		if (address == null || address.trim().isEmpty()) {
			throw new IllegalArgumentException("O endereço do cliente não pode ser vazio.");
		}else {
			this.address = address;
		}
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		
		if (email == null || email.trim().isEmpty()) {
			throw new IllegalArgumentException("O e-mail do cliente não pode estar vazio.");
		}else {
			this.email = email;
		}
		
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		
		if (phone == null || phone.trim().isEmpty()) {
			throw new IllegalArgumentException("O número de telefone do cliente não pode estar vazio.");
		}else {
			this.phone = phone;
		}
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + ", phone="
				+ phone + "]";
	}
}
