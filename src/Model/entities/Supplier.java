package Model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Supplier implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String nif;
	private String phone;
	private String cel;
	private String email;
	private String address;
	private String site;

	public Supplier() {
	}

	public Supplier(Integer id, String name, String nif, String phone, String cel, String email, String address, String site) {
		this.id = id;
		this.name = name;
		this.nif = nif;
		this.phone = phone;
		this.cel = cel;
		this.email = email;
		this.address = address;
		this.site = site;
	}

	// Getters e Setters
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
			throw new IllegalArgumentException("O nome do fornecedor não pode estar vazio.");
		}
		this.name = name;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		if (nif == null || nif.trim().isEmpty()) {
			throw new IllegalArgumentException("O NIF não pode estar vazio.");
		}
		this.nif = nif;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		if (phone == null || phone.trim().isEmpty()) {
			this.phone = "Telefone não informado";
		} else {
			this.phone = phone;
		}
	}

	public String getCel() {
		return cel;
	}

	public void setCel(String cel) {
		this.cel = cel == null || cel.trim().isEmpty() ? "Celular não informado" : cel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null || email.trim().isEmpty() ? "Email não informado" : email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address == null || address.trim().isEmpty() ? "Endereço não informado" : address;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site == null || site.trim().isEmpty() ? "Site não informado" : site;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		Supplier other = (Supplier) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Supplier [id=" + id + ", name=" + name + ", nif=" + nif + ", phone=" + phone
				+ ", cel=" + cel + ", email=" + email + ", address=" + address + ", site=" + site + "]";
	}
}
