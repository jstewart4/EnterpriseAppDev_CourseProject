package northwind.controller;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Messages;

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
	private Order currentOrder;                            // getter/setter
	private Customer currentCustomer;                            // getter/setter  Need?
	private Employee currentEmployee;                            // getter/setter  Need?
	
	
	public Integer getCurrentProductId() {
		return currentProductId;
	}
	
	public void setCurrentProductId(Integer currentProductId) {
		this.currentProductId = currentProductId;
	}
	
	
	
	public void setDetails(Set<OrderDetail> details) {
		this.details = details;
	}

	public Order getCurrentOrder() {
		return currentOrder;
	}
	public void setCurrentOrder(Order currentOrder) {
		this.currentOrder = currentOrder;
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


	@Inject
	ProductService productService;
	
	@Inject
	OrderService orderService;
	
	public void addProductToCart() {
		OrderDetail currentDetail = new OrderDetail();
		Product detailProduct = productService.findOne(currentProductId);
		
		if (detailProduct == null || detailProduct.getDiscontinued() == 't') {
			Messages.addGlobalWarn("{0} is not a valid ProductID.", currentProductId);
			
			
		} else {
			
			currentDetail.setProduct(detailProduct);
			currentDetail.setUnitPrice(detailProduct.getUnitPrice());
			currentDetail.setDiscount(0.00);
			currentDetail.setQuantity((short)0);
	
				details.add(currentDetail);
				Messages.addGlobalInfo("Add product to cart successful.");
	
			
			
		}
	}
	
	
	
	public void createNewOrder() {
		try {
			
			// check order fields (including customer and employee) 
			
			orderService.createOrder(currentOrder, details); 
			Messages.addGlobalInfo("Order creation was successful");
			currentOrder = new Order();
			details.clear();
		} catch(Exception e) {
			Messages.addGlobalError("Order creation was successful");
		}
	}
	
	public void removeProduct(OrderDetail currentProduct) {
		details.remove(currentProduct);
		Messages.addGlobalInfo("Remove product was successful");
	}
	
	public void clearCart() { 
		details.clear();
	}
}
