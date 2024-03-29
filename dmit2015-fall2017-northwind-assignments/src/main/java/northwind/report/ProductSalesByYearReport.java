package northwind.report;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductSalesByYearReport {
	
	private String productName;		// getter+setter
	private String categoryName;	// getter+setter
	private BigDecimal totalSales;	// getter+setter
	
	
	public ProductSalesByYearReport(String categoryName, String productName, BigDecimal totalSales) {
		super();
		this.productName = productName;
		this.categoryName = categoryName;
		this.totalSales = totalSales;
	}
	
	public ProductSalesByYearReport(String categoryName, String productName, double totalSales) {
		super();
		this.productName = productName;
		this.categoryName = categoryName;
		this.totalSales = BigDecimal.valueOf(totalSales).setScale(2, RoundingMode.HALF_UP);
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}
	
	
	

}
