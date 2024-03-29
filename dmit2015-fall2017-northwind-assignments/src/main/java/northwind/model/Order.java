package northwind.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Orders database table.
 * 
 */
@Entity
@Table(name="Orders")
@NamedQuery(name="Order.findAll", query="SELECT o FROM Order o")
@XmlAccessorType(XmlAccessType.FIELD)
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="OrderID")
	private int orderID;

	@Column(name="Freight")
	private BigDecimal freight;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="OrderDate")
	private Date orderDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="RequiredDate")
	private Date requiredDate;

	@Column(name="ShipAddress")
	private String shipAddress;

	@Column(name="ShipCity")
	private String shipCity;

	@Column(name="ShipCountry")
	private String shipCountry;

	@Column(name="ShipName")
	private String shipName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ShippedDate")
	private Date shippedDate;

	@Column(name="ShipPostalCode")
	private String shipPostalCode;

	@Column(name="ShipRegion")
	private String shipRegion;

	@XmlTransient
	//bi-directional many-to-one association to OrderDetail
	@OneToMany(mappedBy="order")
	private List<OrderDetail> orderDetails;

	@XmlTransient
	//bi-directional many-to-one association to Customer
	@ManyToOne
	@JoinColumn(name="CustomerID")
	private Customer customer;

	@XmlTransient
	//bi-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="EmployeeID")
	private Employee employee;

	//bi-directional many-to-one association to Shipper
	@ManyToOne
	@JoinColumn(name="ShipVia")
	private Shipper shipper;

	public Order() {
	}

	public int getOrderID() {
		return this.orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public BigDecimal getFreight() {
		return this.freight;
	}

	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getRequiredDate() {
		return this.requiredDate;
	}

	public void setRequiredDate(Date requiredDate) {
		this.requiredDate = requiredDate;
	}

	public String getShipAddress() {
		return this.shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	public String getShipCity() {
		return this.shipCity;
	}

	public void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}

	public String getShipCountry() {
		return this.shipCountry;
	}

	public void setShipCountry(String shipCountry) {
		this.shipCountry = shipCountry;
	}

	public String getShipName() {
		return this.shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public Date getShippedDate() {
		return this.shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}

	public String getShipPostalCode() {
		return this.shipPostalCode;
	}

	public void setShipPostalCode(String shipPostalCode) {
		this.shipPostalCode = shipPostalCode;
	}

	public String getShipRegion() {
		return this.shipRegion;
	}

	public void setShipRegion(String shipRegion) {
		this.shipRegion = shipRegion;
	}

	public List<OrderDetail> getOrderDetails() {
		return this.orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public OrderDetail addOrderDetail(OrderDetail orderDetail) {
		getOrderDetails().add(orderDetail);
		orderDetail.setOrder(this);

		return orderDetail;
	}

	public OrderDetail removeOrderDetail(OrderDetail orderDetail) {
		getOrderDetails().remove(orderDetail);
		orderDetail.setOrder(null);

		return orderDetail;
	}

	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Shipper getShipper() {
		return this.shipper;
	}

	public void setShipper(Shipper shipper) {
		this.shipper = shipper;
	}

}