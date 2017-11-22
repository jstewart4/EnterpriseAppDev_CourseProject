package northwind.controller;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Messages;

import northwind.model.OrderDetail;
import northwind.model.Product;
import northwind.service.ProductService;

@SuppressWarnings("serial")
@Named
@SessionScoped //CHANGE PRODUCT TO oRDER DETAILS
public class ShoppingCartController implements Serializable {
	
	@NotNull(message="ProductId field value is required")
	private Integer currentProductId;						// +getter +setter
	private Set<OrderDetail> orderDetails = new HashSet<>();	   // +setter
	
	
	
	public Integer getCurrentProductId() {
		return currentProductId;
	}
	public void setCurrentProductId(Integer currentProductId) {
		this.currentProductId = currentProductId;
	}
	

	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}



	@Inject
	ProductService productService;
	
	public void addProductToCart() {
		OrderDetail currentDetail = new OrderDetail();
		Product detailProduct = productService.findOne(currentProductId);
		
		if (detailProduct == null) {
			Messages.addGlobalWarn("{0} is not a valid ProductID.", currentProductId);
			
		} else {
			
			currentDetail.setProduct(detailProduct);
			currentDetail.setUnitPrice(detailProduct.getUnitPrice());
			currentDetail.setDiscount(0.00);
			currentDetail.setQuantity((short)0);
	
                orderDetails.add(currentDetail);
				Messages.addGlobalInfo("Add product to cart successful.");
		}
	}
	
	public void removeProduct(OrderDetail currentProduct) {
		orderDetails.remove(currentProduct);
		Messages.addGlobalInfo("Remove product was successful");
	}
	
	public void clearCart() { //May not need...
		orderDetails.clear();
	}

	
	
	
}
