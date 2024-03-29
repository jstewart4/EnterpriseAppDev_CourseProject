package northwind.report;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EmployeeSales {
	
	private String employeeName;	// getter+setter
	private BigDecimal totalSales; 	// getter+setter
	
	public EmployeeSales(String employeeName, BigDecimal totalSales) {
		super();
		this.employeeName = employeeName;
		this.totalSales = totalSales;
	}
	
	public EmployeeSales(String employeeName, double totalSales) {
		super();
		this.employeeName = employeeName;
		this.totalSales = BigDecimal.valueOf(totalSales).setScale(2, RoundingMode.HALF_UP);
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

}
