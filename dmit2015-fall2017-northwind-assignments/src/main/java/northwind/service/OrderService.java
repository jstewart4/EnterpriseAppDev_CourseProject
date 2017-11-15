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
	
	public List<Order> findOrdersByYearAndMonth(int year, int month){
		return orderRepository.findOrdersByYearAndMonth(year, month);
	}
	
	public Order findOneSalesInvoice(int orderID){
		return orderRepository.find(orderID);
	}
}
