package northwind.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Messages;

import northwind.data.OrderRepository;
import northwind.model.Order;
import northwind.model.OrderDetail;
import northwind.report.AllSalesReport;
import northwind.service.OrderService;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class OrderController implements Serializable{
	
	private int selectedOrderId; 				//getter/setter
	private Order selectedOrder;				//getter
	private double SubTotal;     				//getter
	private double SalesTotal;   				//getter
	private List<Order> ordersByCustomer;		// getter
	private String currentSelectedCustomerId;	// getter/setter
	private List<Order> ordersByEmployee;		// getter
	private int currentSelectedEmployeeId;		// getter/setter
	
	@NotNull(message="OrderId field value is required")
	private Integer currentSelectedOrderId;		// getter/setter
	private Order currentSelectedOrder;			// getter
	
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
	 	
    public void findOrdersByEmployee() {
    	if (!FacesContext.getCurrentInstance().isPostback()) {
			if (currentSelectedEmployeeId > 0) {
				ordersByEmployee = orderRepository.findAllByEmployeeId(currentSelectedEmployeeId);
				if (ordersByEmployee.size() == 0) {
					Messages.addGlobalInfo("There are no orders for EmployeeID {0}", currentSelectedEmployeeId);
				}
			} else {
				Messages.addGlobalError("Bad request. A valid employeeID is required.");
			}
		}
    }
    
	public void findOneInvoice() {
		currentSelectedOrder = orderRepository.findOne(currentSelectedOrderId);
		if( currentSelectedOrder == null ) {
			Messages.addGlobalInfo("There is no invoice with orderID {0}", currentSelectedOrderId);					
		} else {
			Messages.addGlobalInfo("We found 1 result with orderID {0}", currentSelectedOrderId);								
		}
	}

	public void findOneCustomerInvoice(int orderId) {
		currentSelectedOrder = orderRepository.findOne(orderId);
		if( currentSelectedOrder == null ) {
			Messages.addGlobalInfo("There is no invoice with orderID {0}", orderId);					
		} else {
			for (OrderDetail i : currentSelectedOrder.getOrderDetails()) {
				SubTotal = SubTotal + ( (i.getUnitPrice().doubleValue() - (i.getUnitPrice().doubleValue() * i.getDiscount())) * i.getQuantity());
			}
						
			SalesTotal = SubTotal + currentSelectedOrder.getFreight().doubleValue();
			
			Messages.addGlobalInfo("We found 1 result with orderID {0}", orderId);								
		}
	}	
	
	public void findAllInvoicesByCustomer() {
		ordersByCustomer = orderRepository.findAllByCustomerId(currentSelectedCustomerId);
		currentSelectedOrder = null;
		int resultCount = ordersByCustomer.size();
		if (ordersByCustomer.size() == 0) {
			Messages.addGlobalError("Unknown customerId \"{0}\". We found 0 results", currentSelectedCustomerId);
		} else {
			Messages.addGlobalInfo("There are {0} orders from customerId {1}", resultCount, currentSelectedCustomerId);
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
	
	public List<AllSalesReport> retreiveOrderSales(int year) {
		List<AllSalesReport> allSales = new ArrayList<AllSalesReport>();
		for (int i = 1; i <= 12; i++ ) {
			AllSalesReport report = new AllSalesReport(i, orderRepository.findOrderSales(year, i));
			//System.out.println(report);
			allSales.add(report);
		}
		return allSales;
	}

	
	public Integer getCurrentSelectedOrderId() {
		return currentSelectedOrderId;
	}


	public void setCurrentSelectedOrderId(Integer currentSelectedOrderId) {
		this.currentSelectedOrderId = currentSelectedOrderId;
	}


	public Order getCurrentSelectedOrder() {
		return currentSelectedOrder;
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


	public int getCurrentSelectedEmployeeId() {
		return currentSelectedEmployeeId;
	}


	public void setCurrentSelectedEmployeeId(int currentSelectedEmployeeId) {
		this.currentSelectedEmployeeId = currentSelectedEmployeeId;
	}

	

	public List<Order> getOrdersByEmployee() {
		return ordersByEmployee;
	}
	
	
	
	
	
	

}
