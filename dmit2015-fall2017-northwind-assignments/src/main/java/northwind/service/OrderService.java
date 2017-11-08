package northwind.service;

import java.util.List;

import javax.ejb.Stateless;

import northwind.data.OrderRepository;
import northwind.model.Order;

@Stateless
public class OrderService {

	private OrderRepository orderRepository;
	
	public List<Order> findOrdersByDateRange(String dateOne, String dateTwo) {
		return orderRepository.findOrderByDateRange(dateOne, dateTwo);
		
	}
}
