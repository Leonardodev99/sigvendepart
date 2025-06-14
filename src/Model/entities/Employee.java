package Model.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String name;
	private String email;
	private String bi;
	private String address;
	private LocalDate dateBirth;
	private String phone;
	private char sex;
	private LocalDate dateAdmission;
	private BigDecimal salary;
	private String walletNumber;
	private String password;

	public Employee() {
	}

	public Employee(Integer id, String name, String email, String bi, String address, LocalDate dateBirth, String phone,
			char sex, LocalDate dateAdmission, BigDecimal salary, String walletNumber, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.bi = bi;
		this.address = address;
		this.dateBirth = dateBirth;
		this.phone = phone;
		this.sex = sex;
		this.dateAdmission = dateAdmission;
		this.salary = salary;
		this.walletNumber = walletNumber;
		this.setPassword(password);
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
			throw new IllegalArgumentException("Nome do funcionário não pode estar vazio.");
		}
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (email == null || email.trim().isEmpty()) {
			throw new IllegalArgumentException("Email não pode estar vazio.");
		}
		this.email = email;
	}

	public String getBi() {
		return bi;
	}

	public void setBi(String bi) {
		if (bi == null || bi.trim().isEmpty()) {
			throw new IllegalArgumentException("BI não pode estar vazio.");
		}
		this.bi = bi;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		if (address == null || address.trim().isEmpty()) {
			this.address = "Endereço não informado";
		} else {
			this.address = address;
		}
	}

	public LocalDate getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(LocalDate dateBirth) {
		this.dateBirth = dateBirth;
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

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public LocalDate getDateAdmission() {
		return dateAdmission;
	}

	public void setDateAdmission(LocalDate dateAdmission) {
		this.dateAdmission = dateAdmission;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public String getWalletNumber() {
		return walletNumber;
	}

	public void setWalletNumber(String walletNumber) {
		this.walletNumber = walletNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (password == null || password.trim().isEmpty()) {
			throw new IllegalArgumentException("A senha não pode estar vazia.");
		}
		this.password = hashPassword(password);
	}

	
	private String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashedBytes = md.digest(password.getBytes());
			
			StringBuilder sb = new StringBuilder();
			for (byte b : hashedBytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Erro ao gerar hash da senha", e);
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
		if (obj == null || getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", email=" + email + ", bi=" + bi + ", address=" + address
				+ ", dateBirth=" + dateBirth + ", phone=" + phone + ", sex=" + sex + ", dateAdmission=" + dateAdmission
				+ ", salary=" + salary + ", walletNumber=" + walletNumber + ", password=" + password + "]";
	}

	
}
