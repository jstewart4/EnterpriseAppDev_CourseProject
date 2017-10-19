package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.omnifaces.util.Messages;

import northwind.data.ProductRepository;
import northwind.model.Product;
import northwind.report.ExpensiveProductReport;

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
	
	public List<ExpensiveProductReport> retrieveExpensiveProducts() {
		return productRepository.findTenMostExpensive();
	}
	
	private List<Product> productsByCategory; //getter
	private List<Product> productsBySupplier;
	private int currentSelectedSupplierId;
	private int currentSelectedProductId; //getter/setter
	private int currentSelectedCategoryId; //getter/setter

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
			if(currentSelectedCategoryId > 0) {
				productsByCategory = productRepository.findAllByCategory(currentSelectedCategoryId);
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
	

	public List<Product> getProductsBySupplier() {
		return productsBySupplier;
	}

	public Product getCurrentSelectedProduct() {
		return currentSelectedProduct;
	}

	public int getCurrentSelectedCategoryId() {
		return currentSelectedCategoryId;
	}

	public void setCurrentSelectedCategoryId(int currentSelectedCategoryId) {
		this.currentSelectedCategoryId = currentSelectedCategoryId;
	}
	
	

	public int getCurrentSelectedSupplierId() {
		return currentSelectedSupplierId;
	}

	public void setCurrentSelectedSupplierId(int currentSelectedSupplierId) {
		this.currentSelectedSupplierId = currentSelectedSupplierId;
	}

	public void findProductsBySupplier() {
		if(!FacesContext.getCurrentInstance().isPostback()) {
			if(currentSelectedSupplierId > 0) {
				productsBySupplier = productRepository.findAllBySupplier(currentSelectedSupplierId);
				if(productsBySupplier.size() == 0) {
					Messages.addGlobalInfo("There are no products for supplierId {0}", currentSelectedSupplierId);
				}
			} else {
				Messages.addFlashGlobalError("Bad request. A valid supplierId is required.");
			}
		}
	}

	
	
	
	

}
