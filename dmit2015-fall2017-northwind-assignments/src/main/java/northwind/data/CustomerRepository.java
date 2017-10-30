package northwind.data;

import java.util.Collections;
import java.util.List;

import northwind.model.Customer;
import northwind.report.CustomerSales;

public class CustomerRepository extends AbstractJpaRepository<Customer> {
	private static final long serialVersionUID = 1L;

	public CustomerRepository() {
		super(Customer.class);
	}

	public List<CustomerSales> findCustomerSales() {
		return getEntityManager().createQuery(
				"SELECT new northwind.report.CustomerSales( m.companyName, m.customerID, SUM(od.unitPrice * od.quantity * (1 - od.discount)) As TotalSales ) "
						+ " FROM OrderDetail od, IN (od.order) t, IN (t.customer) m "
						+ " WHERE YEAR(t.shippedDate) = :yearDate"
						+ " GROUP BY m.customerID "
						+ " ORDER BY TotalSales DESC", 
						CustomerSales.class)
						.setParameter("yearDate", 1997)
						.setMaxResults(5)
				.getResultList();
			}
	
	public List<CustomerSales> findTopCustomerSales() {
		List<CustomerSales> sales = getEntityManager().createQuery(
				"SELECT new northwind.report.CustomerSales( m.companyName, m.customerID, SUM(od.unitPrice * od.quantity * (1 - od.discount)) As TotalSales ) "
						+ " FROM OrderDetail od, IN (od.order) t, IN (t.customer) m "
						+ " WHERE YEAR(t.shippedDate) = :yearDate"
						+ " GROUP BY m.customerID "
						+ " ORDER BY TotalSales DESC", 
						CustomerSales.class)
						.setParameter("yearDate", 1997)
				.getResultList();
		List<CustomerSales> topSales = sales.subList(0, (int) Math.rint((sales.size() * 0.25)) + 1);
		Collections.sort(topSales);
		return topSales;
			}
}
