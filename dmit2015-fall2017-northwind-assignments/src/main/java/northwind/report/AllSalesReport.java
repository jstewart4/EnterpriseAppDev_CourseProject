package northwind.report;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AllSalesReport {

	private String orderName;		// +getter+setter
	private BigDecimal totalSales;	// +getter+setter
	
	public AllSalesReport(String orderName, BigDecimal totalSales) {
		super();
		this.orderName = orderName;
		this.totalSales = totalSales;
	}
	public AllSalesReport(String orderName, double totalSales) {
		super();
		this.orderName = orderName;
		this.totalSales = BigDecimal.valueOf(totalSales).setScale(2, RoundingMode.HALF_UP);
	}
	
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	
	public BigDecimal getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}
}
