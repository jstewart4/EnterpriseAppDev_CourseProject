package northwind.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the Products database table.
 * 
 */
@Entity
@Table(name="Products")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
@XmlAccessorType(XmlAccessType.FIELD)
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ProductID")
	private int productID;

	@Column(name="Discontinued")
	private byte discontinued;

	@Column(name="ProductName")
	private String productName;

	@Column(name="QuantityPerUnit")
	private String quantityPerUnit;

	@Column(name="ReorderLevel")
	private short reorderLevel;

	@Column(name="UnitPrice")
	private BigDecimal unitPrice;

	@Column(name="UnitsInStock")
	private short unitsInStock;

	@Column(name="UnitsOnOrder")
	private short unitsOnOrder;

	@XmlTransient
	//bi-directional many-to-one association to OrderDetail
	@OneToMany(mappedBy="product")
	private List<OrderDetail> orderDetails;

	@XmlTransient
	//bi-directional many-to-one association to Category
	@ManyToOne
	@JoinColumn(name="CategoryID")
	private Category category;

	@XmlTransient
	//bi-directional many-to-one association to Supplier
	@ManyToOne
	@JoinColumn(name="SupplierID")
	private Supplier supplier;

	public Product() {
	}

	public int getProductID() {
		return this.productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public byte getDiscontinued() {
		return this.discontinued;
	}

	public void setDiscontinued(byte discontinued) {
		this.discontinued = discontinued;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getQuantityPerUnit() {
		return this.quantityPerUnit;
	}

	public void setQuantityPerUnit(String quantityPerUnit) {
		this.quantityPerUnit = quantityPerUnit;
	}

	public short getReorderLevel() {
		return this.reorderLevel;
	}

	public void setReorderLevel(short reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	public BigDecimal getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public short getUnitsInStock() {
		return this.unitsInStock;
	}

	public void setUnitsInStock(short unitsInStock) {
		this.unitsInStock = unitsInStock;
	}

	public short getUnitsOnOrder() {
		return this.unitsOnOrder;
	}

	public void setUnitsOnOrder(short unitsOnOrder) {
		this.unitsOnOrder = unitsOnOrder;
	}

	public List<OrderDetail> getOrderDetails() {
		return this.orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public OrderDetail addOrderDetail(OrderDetail orderDetail) {
		getOrderDetails().add(orderDetail);
		orderDetail.setProduct(this);

		return orderDetail;
	}

	public OrderDetail removeOrderDetail(OrderDetail orderDetail) {
		getOrderDetails().remove(orderDetail);
		orderDetail.setProduct(null);

		return orderDetail;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Supplier getSupplier() {
		return this.supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + productID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (productID != other.productID)
			return false;
		return true;
	}
	
	

}