package northwind.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import northwind.model.Order;
import northwind.service.OrderService;

public class FindOrderByDateRangeController {
	
	@NotNull(message="A start date is required.")
	private String currentStartDate; //getter/setter
	
	private String currentEndDate; //getter/setter
	
	private List<Order> orders;
	
	
	public String getCurrentStartDate() {
		return currentStartDate;
	}


	public void setCurrentStartDate(String currentStartDate) {
		this.currentStartDate = currentStartDate;
	}


	public String getCurrentEndDate() {
		return currentEndDate;
	}


	public void setCurrentEndDate(String currentEndDate) {
		this.currentEndDate = currentEndDate;
	}



	@Inject
	private OrderService orderService; 
	
	
	public void retreiveOrderSalesByRange() {
		
		orders = orderService.findOrdersByDateRange(currentStartDate, currentEndDate);
	}

}
