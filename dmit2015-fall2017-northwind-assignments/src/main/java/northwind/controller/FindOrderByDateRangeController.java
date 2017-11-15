package northwind.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Messages;

import northwind.data.OrderRepository;
import northwind.model.Order;
import northwind.model.OrderDetail;


@SuppressWarnings("serial")
@Named
@ViewScoped    
public class FindOrderByDateRangeController implements Serializable {
	
	@NotNull(message="A valid start date is required.")
	private Date currentStartDate; //getter/setter
	
	@NotNull(message="A valid end date is required.")
	private Date currentEndDate; //getter/setter
	
	private List<Order> ordersInRange; //getter
	
	private Integer currentOrderId;		// getter/setter
	private Order currentOrder;			// getter
	
	private double SubTotal;     				//getter
	private double SalesTotal;   				//getter
	
	public Date getCurrentStartDate() {
		return currentStartDate;
	}


	public void setCurrentStartDate(Date currentStartDate) {
		this.currentStartDate = currentStartDate;
	}


	public Date getCurrentEndDate() {
		return currentEndDate;
	}


	public void setCurrentEndDate(Date currentEndDate) {
		this.currentEndDate = currentEndDate;
	}
	
	
	
	public List<Order> getOrdersInRange() {
		return ordersInRange;
	}

	public Integer getCurrentOrderId() {
		return currentOrderId;
	}


	public void setCurrentOrderId(Integer currentOrderId) {
		this.currentOrderId = currentOrderId;
	}


	public Order getCurrentOrder() {
		return currentOrder;
	}

	
	public double getSubTotal() {
		return SubTotal;
	}


	public double getSalesTotal() {
		return SalesTotal;
	}



	
	@Inject
	private OrderRepository orderRepository;
	
	
	public void findOneInvoice( int orderId ) {
		currentOrder = orderRepository.findOne(orderId);
		
		if( currentOrder == null ) {
			Messages.addGlobalInfo("There is no order with order ID {0}", orderId);					
		} else {
			
			for (OrderDetail i : currentOrder.getOrderDetails()) {
				SubTotal = SubTotal + ( (i.getUnitPrice().doubleValue() - (i.getUnitPrice().doubleValue() * i.getDiscount())) * i.getQuantity());
			}
			
			SalesTotal = SubTotal + currentOrder.getFreight().doubleValue();
			
			Messages.addGlobalInfo("We found 1 result with order ID {0}", orderId);								
		}
	}
	
	
	public void retreiveOrderSalesByRange() {
		
		
				SimpleDateFormat format1 = new SimpleDateFormat("MMM, d yyyy");
				
		    	try {
		    		
					ordersInRange = orderRepository.findOrderByDateRange(currentStartDate, currentEndDate);
					
					currentOrder = null;
					
					if (ordersInRange.size() == 0) {
						Messages.addGlobalInfo("There are 0 orders from {0} to {1}", format1.format(currentStartDate), 
								format1.format(currentEndDate));
						
					} else {
						Messages.addGlobalInfo("There  are {0} orders from {1} to {2}", ordersInRange.size(), 
								format1.format(currentStartDate), 
								format1.format(currentEndDate) );
						
					}
					
				}catch(Exception e) {
					Messages.addGlobalWarn("There was an issue retreiving data");
					Messages.addGlobalWarn("{0}", e.getMessage());
				}
		
	}

}
