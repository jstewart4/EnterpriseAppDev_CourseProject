package northwind.data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import northwind.model.Order;

public class OrderRepository extends AbstractJpaRepository<Order> {
	private static final long serialVersionUID = 1L;
	
	public OrderRepository() {
		super(Order.class);
		
	}
	
	public Order findOne(int orderId) {
		return getEntityManager().createQuery(
				"SELECT ord FROM Order ord JOIN FETCH ord.orderDetails WHERE ord.orderID = :IdValue", Order.class)
				.setParameter("IdValue", orderId)
				.getSingleResult();
	}
	
	public List<Order> findAllByCustomerId(String customerId) {
 		return getEntityManager().createQuery(
 			"SELECT o FROM Order o WHERE o.customer.customerID = :idValue", Order.class)
 			.setParameter("idValue", customerId)
 			.getResultList();
 	}
	
	public List<Order> findAllByEmployeeId(int employeeId) {
		return getEntityManager().createQuery(
			"SELECT o FROM Order o WHERE o.employee.employeeID = :idValue", Order.class)
			.setParameter("idValue", employeeId)
			.getResultList();
	}

	public BigDecimal findOrderSales(int year, int month) {
	
		BigDecimal allSales = BigDecimal.ZERO;
		try{
			allSales = getEntityManager().createQuery(

				"SELECT SUM(od.unitPrice * od.quantity ) "
				+ "FROM OrderDetail od, IN (od.order) o "
				+ "WHERE YEAR(o.shippedDate) = :yearValue AND MONTH(o.shippedDate) = :monthValue " 
				, BigDecimal.class)
				.setParameter("yearValue", year)
				.setParameter("monthValue", month)
				.getSingleResult();
		}catch (Exception e) {
			
		}
		if (allSales == null) {
			allSales = BigDecimal.ZERO;
		}
		return allSales;
	}	
	
	public List<Order> findOrderByDateRange(Date startDate, Date endDate ) {
		
		return getEntityManager().createQuery("SELECT o FROM Order o "
				+ "WHERE o.orderDate >= :startValue AND o.orderDate <= :endValue "
				, Order.class)
				.setParameter("startValue", startDate)
				.setParameter("endValue", endDate)
				.getResultList();
		
	}
	
	public List<Order> findOrdersByYearAndMonth(int year, int month) {		
		return getEntityManager().createQuery("SELECT o FROM Order o "
				+ "WHERE YEAR(o.shippedDate) = :yearValue AND MONTH(o.shippedDate) = :monthValue " 
				,Order.class)
				.setParameter("yearValue", year)
				.setParameter("monthValue", month)
				.getResultList();		
	}
	
}
