package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.omnifaces.util.Messages;

import northwind.data.OrderRepository;
import northwind.model.Order;
import northwind.model.OrderDetail;

@Model
public class OrderController {
	
	private int selectedOrderId; //getter/setter
	private Order selectedOrder; //getter
	private double SubTotal;     //getter
	private double SalesTotal;   //getter
	private List<Order> ordersByCustomer;	// getter
	private String currentSelectedCustomerId;	// getter/setter
	
	public void findOrder() {
		if(!FacesContext.getCurrentInstance().isPostback()) {
			if(selectedOrderId > 0) {
				selectedOrder = orderRepository.findOne(selectedOrderId);
				if (selectedOrder == null) {
					Messages.addGlobalInfo("There is no Order with orderId {0}", selectedOrderId);
				} else {
					for (OrderDetail i : selectedOrder.getOrderDetails()) {
						SubTotal = SubTotal + ( (i.getUnitPrice().doubleValue() - (i.getUnitPrice().doubleValue() * i.getDiscount())) * i.getQuantity());
					}
					
					SalesTotal = SubTotal + selectedOrder.getFreight().doubleValue();
					
				}
			} else {
				Messages.addGlobalError("Bad Request. Invaild orderId.");
			}
		}
	}
	
	
    public void findOrdersByCustomer() {
    	if( !FacesContext.getCurrentInstance().isPostback() ) {
	 		// verify that a valid customerId was set
	 		if( currentSelectedCustomerId != null && ! currentSelectedCustomerId.isEmpty()) {
	 			ordersByCustomer = orderRepository.findAllByCustomerId(currentSelectedCustomerId);
	 			if( ordersByCustomer.size() == 0 ) {
	 				Messages.addGlobalInfo("There are no orders for customerID {0}", 
	 							currentSelectedCustomerId);
	 				}
	 			} else {
	 				Messages.addGlobalError("Bad request. A valid customerID is required.");
	 			}
	 		}
	 	}
	 	
	 	
	
	
	
	@Inject
	private OrderRepository orderRepository;
	
	private List<Order> orders;
	
	@PostConstruct
	void init() {
		orders = orderRepository.findAll();
	}
	
	public List<Order> getOrders() {
		return orders;
	}

	public int getSelectedOrderId() {
		return selectedOrderId;
	}

	public void setSelectedOrderId(int selectedOrderId) {
		this.selectedOrderId = selectedOrderId;
	}

	public Order getSelectedOrder() {
		return selectedOrder;
	}

	public double getSubTotal() {
		return SubTotal;
	}

	public double getSalesTotal() {
		return SalesTotal;
	}
	
	public String getCurrentSelectedCustomerId() {
 		return currentSelectedCustomerId;
 	}
 
 	public void setCurrentSelectedCustomerId(String currentSelectedCustomerId) {
 		this.currentSelectedCustomerId = currentSelectedCustomerId;
 	}
 
 	public List<Order> getOrdersByCustomer() {
 		return ordersByCustomer;
 	}

}
