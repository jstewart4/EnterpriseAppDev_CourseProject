package northwind.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Messages;

import northwind.model.Order;
import northwind.model.OrderDetail;
import northwind.service.OrderService;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class FindOneInvoiceByID implements Serializable {
	
	@Inject
	private OrderService orderService;
	
	@NotNull(message="OrderID field value is required")
	
	private Integer searchValue;		// getter+setter
	private Order querySingleResult;	// getter
	
	private double SubTotal;			// getter
	private double SalesTotal;			// getter

	public Integer getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(Integer searchValue) {
		this.searchValue = searchValue;
	}

	public Order getQuerySingleResult() {
		return querySingleResult;

	}
	
	public double getSubTotal() {
		return SubTotal;
	}

	public double getSalesTotal() {
		return SalesTotal;
	}	

	public void findOneSalesInvoice() {
		querySingleResult = orderService.findOneSalesInvoice(searchValue);
		if(querySingleResult == null) {
			Messages.addGlobalInfo("Found 0 results");
		} else {
			for (OrderDetail i : querySingleResult.getOrderDetails()) {
				SubTotal = SubTotal + ( (i.getUnitPrice().doubleValue() - (i.getUnitPrice().doubleValue() * i.getDiscount())) * i.getQuantity());
			}
			SalesTotal = SubTotal + querySingleResult.getFreight().doubleValue();
			Messages.addGlobalInfo("Successfully found one result");
		}
	}

}