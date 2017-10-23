package northwind.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CategorySalesReport {
	
	private String categoryName; //getter/setter
	private BigDecimal categoryRevenue; //getter/setter
	
	public CategorySalesReport(String categoryName, BigDecimal categoryRevenue) {
		super();
		this.categoryName = categoryName;
		this.categoryRevenue = categoryRevenue;
	}
	
	public CategorySalesReport(String categoryName, Double categoryRevenue) {
		super();
		this.categoryName = categoryName;
		this.categoryRevenue = BigDecimal.valueOf(categoryRevenue).setScale(2, RoundingMode.HALF_UP);
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public BigDecimal getCategoryRevenue() {
		return categoryRevenue;
	}

	public void setCategoryRevenue(BigDecimal categoryRevenue) {
		this.categoryRevenue = categoryRevenue;
	}
	
	

}
