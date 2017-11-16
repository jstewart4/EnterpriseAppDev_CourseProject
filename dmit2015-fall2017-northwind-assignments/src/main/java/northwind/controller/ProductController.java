package northwind.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Messages;

import northwind.data.ProductRepository;
import northwind.model.Product;
import northwind.report.ExpensiveProductReport;
import northwind.report.ProductSalesByYearReport;
import northwind.service.ProductService;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class ProductController implements Serializable{
	
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
	
	public List<ProductSalesByYearReport> retrieveProductSalesForYear() {
		return productRepository.findProductSales();
	}
	
	private List<Product> productsByCategory; //getter
	private List<Product> productsBySupplier;
	private Integer currentSelectedSupplierId; //getter/setter
	private int currentSelectedProductId; //getter/setter
	private Integer currentSelectedCategoryId; //getter/setter

	private Product currentSelectedProduct; //getter
	
	@Inject
	private ProductService productService;
	
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

	public Integer getCurrentSelectedCategoryId() {
		return currentSelectedCategoryId;
	}

	public void setCurrentSelectedCategoryId(Integer currentSelectedCategoryId) {
		this.currentSelectedCategoryId = currentSelectedCategoryId;
	}
	
	

	public Integer getCurrentSelectedSupplierId() {
		return currentSelectedSupplierId;
	}

	public void setCurrentSelectedSupplierId(Integer currentSelectedSupplierId) {
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
				Messages.addGlobalError("Bad request. A valid supplierId is required.");
			}
		}
	}

	private Product currentNewProduct = new Product(); //getter/setter
	
	public void createNewProduct() {
		try {
			productService.createProduct(currentNewProduct, currentSelectedSupplierId, currentSelectedCategoryId);
			Messages.addGlobalInfo("Create new product was successful.");
			
			currentNewProduct = new Product();
		} catch(Exception e) {
			Messages.addGlobalWarn("Create new product was not successful");
			Messages.addGlobalWarn("{0}", e.getMessage());
		}
	}

	
	public Product getCurrentNewProduct() {
		return currentNewProduct;
	}

	public void setCurrentNewProduct(Product currentNewProduct) {
		this.currentNewProduct = currentNewProduct;
	}
	
	
	

}
