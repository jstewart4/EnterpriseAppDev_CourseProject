package northwind.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Employees database table.
 * 
 */
@Entity
@Table(name="Employees")
@NamedQuery(name="Employee.findAll", query="SELECT e FROM Employee e")
@XmlAccessorType(XmlAccessType.FIELD)
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="EmployeeID")
	private int employeeID;

	@Column(name="Address")
	private String address;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="BirthDate")
	private Date birthDate;

	@Column(name="City")
	private String city;

	@Column(name="Country")
	private String country;

	@Column(name="Extension")
	private String extension;

	@Column(name="FirstName")
	private String firstName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="HireDate")
	private Date hireDate;

	@Column(name="HomePhone")
	private String homePhone;

	@Column(name="LastName")
	private String lastName;

	@Lob
	@Column(name="Notes")
	private String notes;

	@Lob
	@Column(name="Photo")
	private byte[] photo;

	@Column(name="PhotoPath")
	private String photoPath;

	@Column(name="PostalCode")
	private String postalCode;

	@Column(name="Region")
	private String region;

	@Column(name="Salary")
	private float salary;

	@Column(name="Title")
	private String title;

	@Column(name="TitleOfCourtesy")
	private String titleOfCourtesy;

	@XmlTransient
	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="ReportsTo")
	private Employee employee;

	@XmlTransient
	//bi-directional many-to-one association to Employee
	@OneToMany(mappedBy="employee")
	private List<Employee> employees;

	@XmlTransient
	//bi-directional many-to-one association to Order
	@OneToMany(mappedBy="employee")
	private List<Order> orders;

	public Employee() {
	}

	public int getEmployeeID() {
		return this.employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getExtension() {
		return this.extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getHireDate() {
		return this.hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getHomePhone() {
		return this.homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public byte[] getPhoto() {
		return this.photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getPhotoPath() {
		return this.photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public float getSalary() {
		return this.salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleOfCourtesy() {
		return this.titleOfCourtesy;
	}

	public void setTitleOfCourtesy(String titleOfCourtesy) {
		this.titleOfCourtesy = titleOfCourtesy;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<Employee> getEmployees() {
		return this.employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public Employee addEmployee(Employee employee) {
		getEmployees().add(employee);
		employee.setEmployee(this);

		return employee;
	}

	public Employee removeEmployee(Employee employee) {
		getEmployees().remove(employee);
		employee.setEmployee(null);

		return employee;
	}

	public List<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Order addOrder(Order order) {
		getOrders().add(order);
		order.setEmployee(this);

		return order;
	}

	public Order removeOrder(Order order) {
		getOrders().remove(order);
		order.setEmployee(null);

		return order;
	}

}