package northwind.controller;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Messages;

import northwind.exception.IllegalQuantityException;
import northwind.exception.InsufficientStockException;
import northwind.exception.NoOrderDetailException;
import northwind.model.Customer;
import northwind.model.Employee;
import northwind.model.Order;
import northwind.model.OrderDetail;
import northwind.model.Product;
import northwind.service.OrderService;
import northwind.service.ProductService;

@SuppressWarnings("serial")
@Named
@SessionScoped  
public class POSController implements Serializable {


	@NotNull(message="ProductId field value is required")
	private Integer currentProductId;						// getter/setter
	private Set<OrderDetail> details = new HashSet<>();	   //  setter
	                          // getter/setter
	
	@NotNull(message="Customer field value selection is required")
	private Customer currentCustomer;                            // getter/setter
	@NotNull(message="Customer field value selection is required")
	private Employee currentEmployee;                            // getter/setter 
	
	private String shippingName; 								//getter/setter
	private String shippingAddress; 							//getter/setter
	private String shippingCity; 								//getter/setter
	private String shippingRegion; 								//getter/setter
	private String shippingPostal; 								//getter/setter
	private String shippingCountry; 							//getter/setter
	
	@NotNull(message="Required Date field value is required")
	private Date newRequiredDate; // getter/setter
	
	private boolean shipingCheck; // getter/setter
	
	
	public Integer getCurrentProductId() {
		return currentProductId;
	}
	
	public void setCurrentProductId(Integer currentProductId) {
		this.currentProductId = currentProductId;
	}
	
	
	public Set<OrderDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<OrderDetail> details) {
		this.details = details;
	}

	
	
	
	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

	public Employee getCurrentEmployee() {
		return currentEmployee;
	}

	public void setCurrentEmployee(Employee currentEmployee) {
		this.currentEmployee = currentEmployee;
	}

	public String getShippingName() {
		return shippingName;
	}

	public void setShippingName(String shippingName) {
		this.shippingName = shippingName;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getShippingCity() {
		return shippingCity;
	}

	public void setShippingCity(String shippingCity) {
		this.shippingCity = shippingCity;
	}

	public String getShippingRegion() {
		return shippingRegion;
	}

	public void setShippingRegion(String shippingRegion) {
		this.shippingRegion = shippingRegion;
	}

	public String getShippingPostal() {
		return shippingPostal;
	}

	public void setShippingPostal(String shippingPostal) {
		this.shippingPostal = shippingPostal;
	}

	public String getShippingCountry() {
		return shippingCountry;
	}

	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}
	
	
	public Date getNewRequiredDate() {
		return newRequiredDate;
	}

	public void setNewRequiredDate(Date newRequiredDate) {
		this.newRequiredDate = newRequiredDate;
	}

	

	public boolean isShipingCheck() {
		return shipingCheck;
	}

	public void setShipingCheck(boolean shipingCheck) {
		this.shipingCheck = shipingCheck;
	}



	@Inject
	ProductService productService;
	
	@Inject
	OrderService orderService;
	

	public void changeShipingInfo() { 
		
		if (currentCustomer == null) {
			Messages.addGlobalWarn("Must choose a customer to have the same shipping info.");
			
		} else if (shipingCheck == true) {
			
			shippingName = currentCustomer.getCompanyName();
			shippingAddress = currentCustomer.getAddress();
			shippingCity = currentCustomer.getCity();
			shippingRegion = currentCustomer.getRegion();
			shippingPostal = currentCustomer.getPostalCode();
			shippingCountry = currentCustomer.getCountry();
		} 
		
	}
	
	public void addProductToCart() {
		OrderDetail currentDetail = new OrderDetail();
		Product detailProduct = productService.findOne(currentProductId);
		
		if (detailProduct == null || detailProduct.getDiscontinued() == 't') {
			Messages.addGlobalWarn("{0} is not a valid ProductID.", currentProductId);
			
			
		} else {
			
			currentDetail.setProduct(detailProduct);
			currentDetail.setUnitPrice(detailProduct.getUnitPrice());
			currentDetail.setDiscount(0.00);
			currentDetail.setQuantity((short)1);
	
			if (details.add(currentDetail)) {
				Messages.addGlobalInfo("Add product to cart successful.");
				currentProductId = null;
			} else {
				Messages.addGlobalInfo("Product is already in cart");
				currentProductId = null;
			}
			
		}
	}
	
	
	
	public void createNewOrder() {
		try {
			
			Order currentOrder = new Order();  
			
			currentOrder.setCustomer(currentCustomer);
			currentOrder.setEmployee(currentEmployee);
			currentOrder.setShipName(shippingName);
			currentOrder.setShipAddress(shippingAddress);
			currentOrder.setShipCity(shippingCity);
			currentOrder.setShipRegion(shippingRegion);
			currentOrder.setShipPostalCode(shippingPostal);
			currentOrder.setShipCountry(shippingCountry);
			currentOrder.setRequiredDate(newRequiredDate);
			
			Date today = Calendar.getInstance().getTime();
			currentOrder.setOrderDate(new Timestamp(today.getTime()));
			
			
			int orderId = orderService.createOrder(currentOrder, new ArrayList<>(details)); 
			Messages.addGlobalInfo("Successfully created order {0}", orderId);
			
		
			currentCustomer = new Customer();
			currentEmployee = new Employee();
			details.clear();
			
			shippingName = null;
			shippingAddress = null;
			shippingCity = null;
			shippingRegion = null;
			shippingPostal = null;
			shippingCountry = null;
			
			newRequiredDate = null;
			
			
		} catch( NoOrderDetailException | IllegalQuantityException | InsufficientStockException e) {
			Messages.addGlobalError(e.getMessage());
			
		} catch(Exception e) {
			Messages.addGlobalError("Order creation was not successful");
			Messages.addGlobalError(e.getMessage());
		}
	}
	
	
	
	public void removeProduct(OrderDetail currentProduct) {
		details.remove(currentProduct);
		currentProductId = null;
		Messages.addGlobalInfo("Remove product was successful");
	}
	
	public void clearCart() { 
		details.clear();
		currentProductId = null;
		
	}
}
