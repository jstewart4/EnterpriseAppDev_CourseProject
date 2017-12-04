package northwind.controller;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Logger;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Messages;

import northwind.exception.IllegalQuantityException;
import northwind.exception.InsufficientStockException;
import northwind.model.Order;
import northwind.model.OrderDetail;
import northwind.model.Shipper;
import northwind.service.OrderService;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class FindOneInvoiceByID implements Serializable {
	
	@Inject
	private Logger log;
	
	@Inject
	private OrderService orderService;
	
	@NotNull(message="OrderID field value is required")
	
	private Integer searchValue;		// getter+setter
	private Order querySingleResult;	// getter
	
	private double SubTotal;			// getter
	private double SalesTotal = 0;			// getter

	private BigDecimal freight; 		// getter+setter
	private Shipper shipper; 			// getter+setter
	private Date shippedDate;			// getter+setter
	
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

	public BigDecimal getFreight() {
		return freight;
	}
	public void findOneSalesInvoice() {
		try {	
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
		} catch(Exception e) {
			log.info(e.getMessage());
			querySingleResult = null;
			Messages.addGlobalInfo("We found 0 results");
		}
	}
	
	public void deleteOrder() {
		try {
			orderService.removeOrder(querySingleResult);
			querySingleResult = null;
			Messages.addGlobalInfo("Delete was successful");
		} catch (Exception e) {
			Messages.addGlobalInfo("Delete was not successful");
			log.info(e.getMessage());
		}
	}
	
//	for complete an order
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}

	public Shipper getShipper() {
		return shipper;
	}

	public void setShipper(Shipper shipper) {
		this.shipper = shipper;
	}

	public Date getShippedDate() {
		return shippedDate;
	}

	public void setShippedDate(Date shippedDate) {
		this.shippedDate = shippedDate;
	}
	
	public void findOneOrder() {
		querySingleResult = orderService.findOneSalesInvoice(searchValue);
		if(querySingleResult == null) {
			Messages.addGlobalInfo("Found 0 results");
		} else if(querySingleResult.getShippedDate() != null){
			Messages.addGlobalError("Order has already been shipped. Cannot Complete Order.");
		} else {
			for (OrderDetail i : querySingleResult.getOrderDetails()) {
				SubTotal = SubTotal + ( (i.getUnitPrice().doubleValue() - (i.getUnitPrice().doubleValue() * i.getDiscount())) * i.getQuantity());
			}
			if (querySingleResult.getFreight() == null) {
				SalesTotal = 0.0;
			} else {
				SalesTotal = SubTotal + querySingleResult.getFreight().doubleValue();				
			}

			shipper = querySingleResult.getShipper();
			
			freight = querySingleResult.getFreight();
			
			Messages.addGlobalInfo("Successfully found one result");
		}
	}
	
	public void completeOrder() {
			try {
				orderService.completeOrder(querySingleResult, freight, shipper, shippedDate);
				Messages.addGlobalInfo("Complete order was successful");
			} 
			catch( IllegalQuantityException | InsufficientStockException e ) {
				Messages.addGlobalError(e.getMessage());
				
			}
			catch( Exception e ) {
				Messages.addGlobalError("Complete order was not successful");
				log.info(e.getMessage());
				
			}
		}

}
