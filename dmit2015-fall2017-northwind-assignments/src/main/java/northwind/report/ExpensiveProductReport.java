package northwind.report;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExpensiveProductReport {
	
	private String productName; //getter/setter
	private BigDecimal unitPrice; //getter/setter
	
	public ExpensiveProductReport(String productName, BigDecimal unitPrice) {
		super();
		this.productName = productName;
		this.unitPrice = unitPrice;
	}
	
	public ExpensiveProductReport(String productName, Double unitPrice) {
		super();
		this.productName = productName;
		this.unitPrice = BigDecimal.valueOf(unitPrice).setScale(2, RoundingMode.HALF_UP);
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	
	
	

}
