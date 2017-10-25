package northwind.report;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AllSalesReport {

	private int orderMonth;		// +getter+setter
	private BigDecimal totalSales;	// +getter+setter
	
	public AllSalesReport(int orderMonth, BigDecimal totalSales) {
		super();
		this.orderMonth = orderMonth;
		this.totalSales = totalSales;
	}
	public AllSalesReport(int orderMonth, double totalSales) {
		super();
		this.orderMonth = orderMonth;
		this.totalSales = BigDecimal.valueOf(totalSales).setScale(2, RoundingMode.HALF_UP);
	}
	
	public int getOrderMonth() {
		return orderMonth;
	}

	public void setOrderMonth(int orderMonth) {
		this.orderMonth = orderMonth;
	}
	
	public BigDecimal getTotalSales() {
		return totalSales;
	}
	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}
	
	@Override
	public String toString() {
		return "AllSalesReport [orderMonth=" + orderMonth + ", totalSales=" + totalSales + "]";
	}
	
	
}
