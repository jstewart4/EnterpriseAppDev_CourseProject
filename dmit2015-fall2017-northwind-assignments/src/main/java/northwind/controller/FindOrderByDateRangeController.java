package northwind.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.omnifaces.util.Messages;

import northwind.model.Order;
import northwind.service.OrderService;

public class FindOrderByDateRangeController {
	
	@NotNull(message="A valid start date is required.")
	@NotBlank (message="A valid start date is required.")
	private String currentStartDate; //getter/setter
	
	@NotNull(message="A valid end date is required.")
	@NotBlank (message="A valid end date is required.")
	private String currentEndDate; //getter/setter
	
	private List<Order> ordersInRange;
	
	
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
		try {
			ordersInRange = orderService.findOrdersByDateRange(currentStartDate, currentEndDate);
			if (ordersInRange.size() == 0) {
				Messages.addGlobalWarn("There are 0 orders from {0} to {1}", LocalDateTime.parse(currentStartDate, 
						DateTimeFormatter.ofPattern("MMM d, yyyy")), LocalDateTime.parse(currentEndDate, 
								DateTimeFormatter.ofPattern("MMM d, yyyy")) );
				ordersInRange.clear();
			} else {
				Messages.addGlobalWarn("There are {0} orders from {1} to {2}", ordersInRange.size(), LocalDateTime.parse(currentStartDate, 
						DateTimeFormatter.ofPattern("MMM d, yyyy")), LocalDateTime.parse(currentEndDate, 
								DateTimeFormatter.ofPattern("MMM d, yyyy")) );
			}
			
		}catch(Exception e) {
			Messages.addGlobalWarn("There are 0 orders from {0} to {1}", LocalDateTime.parse(currentStartDate, 
					DateTimeFormatter.ofPattern("MMM d, yyyy")), LocalDateTime.parse(currentEndDate, 
							DateTimeFormatter.ofPattern("MMM d, yyyy")) );
		}
		
	}

}
