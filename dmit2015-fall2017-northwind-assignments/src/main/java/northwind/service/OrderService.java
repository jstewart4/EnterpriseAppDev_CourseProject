package northwind.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import northwind.data.OrderRepository;
import northwind.model.Order;

@Stateless
public class OrderService {

	@Inject
	private OrderRepository orderRepository;
	
	public List<Order> findOrdersByDateRange(Date dateOne, Date dateTwo) {
		return orderRepository.findOrderByDateRange(dateOne, dateTwo);
		
	}
}
