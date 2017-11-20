package northwind.controller;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.omnifaces.util.Messages;

import northwind.model.Product;
import northwind.service.ProductService;

@SuppressWarnings("serial")
@Named
@SessionScoped //CHANGE PRODUCT TO oRDER DETAILS
public class ShoppingCartController implements Serializable {
	
	@NotNull(message="ProductId field value is required")
	private Integer currentProductId;						// +getter +setter
	private Set<Product> products = new HashSet<>();	   // +setter
	
	
	
	public Integer getCurrentProductId() {
		return currentProductId;
	}
	public void setCurrentProductId(Integer currentProductId) {
		this.currentProductId = currentProductId;
	}
	

	public void setProducts(Set<Product> products) {
		this.products = products;
	}



	@Inject
	ProductService productService;
	
	public void addProductToCart() {
		Product currentProduct = productService.findOne(currentProductId);
		if (currentProduct == null) {
			Messages.addGlobalWarn("{0} is not a valid ProductID.", currentProduct);
		} else {
			products.add(currentProduct);
			Messages.addGlobalInfo("Adding product to cart successful.");
		}
	}
	
	public void removeProduct(Product currentProduct) {
		products.remove(currentProduct);
		Messages.addGlobalInfo("Remove product was successful");
	}
	
	public void clearCart() { //May not need...
		products.clear();
	}

	
	
	
}
