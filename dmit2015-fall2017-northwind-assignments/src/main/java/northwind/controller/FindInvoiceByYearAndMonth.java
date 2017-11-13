package northwind.controller;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Messages;

import northwind.model.Order;
import northwind.service.OrderService;


@SuppressWarnings("serial")
@Named
@ViewScoped
public class FindInvoiceByYearAndMonth implements Serializable{
	
	@Inject
	private OrderService orderService;
	
	@NotNull(message="Year field value is required.")
	private Integer searchYear;		// +getter+setter
	
	@NotNull(message="Month field value is required.")
	private Integer searchMonth;		// +getter+setter
	
	private List<Order> queryResults;	// +getter
	
	@Inject
	private Logger log;
	
	public Integer getSearchYear() {
		return searchYear;
	}

	public void setSearchYear(Integer searchYear) {
		this.searchYear = searchYear;
	}
	
	public Integer getSearchMonth() {
		return searchMonth;
	}

	public void setSearchMonth(Integer searchMonth) {
		this.searchMonth = searchMonth;
	}

	public List<Order> getQueryResults() {
		return queryResults;
	}

	public void findSalesInvoicesByYearAndMonth() {
		try {
			queryResults = orderService.findOrdersByYearAndMonth(searchYear, searchMonth);
			if( queryResults == null ) {
				Messages.addGlobalInfo("We found 0 results for Year {0} and Month {1}",
						searchYear, searchMonth);
			} else {
				Messages.addGlobalInfo("Results found");				
			}
		} catch(Exception e) {
			log.info(e.getMessage());
			queryResults = null;
			Messages.addGlobalInfo("We found 0 results");
		}
	}
}
