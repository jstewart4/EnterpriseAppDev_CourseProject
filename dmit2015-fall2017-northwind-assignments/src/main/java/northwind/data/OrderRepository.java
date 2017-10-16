package northwind.data;

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


}
