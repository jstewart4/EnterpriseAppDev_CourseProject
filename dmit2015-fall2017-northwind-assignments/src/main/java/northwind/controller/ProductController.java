package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.omnifaces.util.Messages;

import northwind.data.ProductRepository;
import northwind.model.Product;

@Model
public class ProductController {
	
	@Inject
	private ProductRepository productRepository;
	
	private List<Product> products;
	
	@PostConstruct
	void init() {
		products = productRepository.findAll();
	}
	
	public List<Product> getProducts() {
		return products;
	}
	
	
	private List<Product> productsByCategory; //getter
	private int currentSelectedProductId; //getter/setter
	private Product currentSelectedProduct; //getter
	
	public void findProduct() {
		if(!FacesContext.getCurrentInstance().isPostback()) {
			if(currentSelectedProductId > 0) {
				currentSelectedProduct = productRepository.find(currentSelectedProductId);
				if(currentSelectedProduct == null) {
					Messages.addGlobalInfo("There is no product with productId {0} ",
							currentSelectedProductId);
				}
			} else {
				Messages.addGlobalError("Bad request. Valid productId needed.");
			}
		}
	}
	
	public void findProductByCategory() {
		if(!FacesContext.getCurrentInstance().isPostback()) {
			if(currentSelectedProductId > 0) {
				productsByCategory = productRepository.findAllByCataegory(currentSelectedProductId);
				if(productsByCategory.size() == 0) {
					Messages.addGlobalInfo("There are no products for categoryId {0}", currentSelectedProductId);
				}
			} else {
				Messages.addFlashGlobalError("Bad request. A valid categoryId is required.");
			}
		}
	}

	public int getCurrentSelectedProductId() {
		return currentSelectedProductId;
	}

	public void setCurrentSelectedProductId(int currentSelectedProductId) {
		this.currentSelectedProductId = currentSelectedProductId;
	}

	public List<Product> getProductsByCategory() {
		return productsByCategory;
	}

	public Product getCurrentSelectedProduct() {
		return currentSelectedProduct;
	}
	
	
	
	

}
