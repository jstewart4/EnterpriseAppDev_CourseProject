package northwind.report;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CustomerSales implements Comparable<CustomerSales> {
	private String companyName; // getter + setter
	private String customerID;
	private BigDecimal totalSales; // getter + setter
	
	public CustomerSales(String companyName,String customerID, BigDecimal totalSales) {
		super();
		this.companyName = companyName;
		this.customerID = customerID;
		this.totalSales = totalSales;
		
	}
	
	public CustomerSales(String companyName, String customerID, double totalSales) {
		super();
		this.companyName = companyName;
		this.customerID = customerID;
		this.totalSales = BigDecimal.valueOf(totalSales).setScale(2, RoundingMode.HALF_UP);
	}
	
	public int compareTo(CustomerSales other) {
		return companyName.compareTo(other.companyName);
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	
	
}
