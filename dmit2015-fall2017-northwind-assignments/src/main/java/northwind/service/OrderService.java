package northwind.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;


import northwind.data.OrderRepository;
import northwind.data.ProductRepository;
import northwind.model.Order;
import northwind.model.OrderDetail;

@Stateless
public class OrderService {
	
	@Resource
	private EJBContext context;
	
	@Inject
	private EntityManager entityManager;

	@Inject
	private OrderRepository orderRepository;
	
	@Inject
	private ProductRepository productRepository;
	
	public List<Order> findOrdersByDateRange(Date dateOne, Date dateTwo) {
		return orderRepository.findOrderByDateRange(dateOne, dateTwo);
		
	}
	
	public List<Order> findOrdersByYearAndMonth(int year, int month){
		return orderRepository.findOrdersByYearAndMonth(year, month);
	}
	
	public Order findOneSalesInvoice(int orderId){
		return orderRepository.findOne(orderId);
	}
	
	public int createOrder(Order newOrder, List<OrderDetail> products) {
		int orderId = 0;
		
		if(products == null || products.size() == 0) {
			//context.setRollbackOnly();
			// throw new NoInvoiceLinesException("There are no items in the invoice");
		}
		
		entityManager.persist(newOrder);
		orderId = newOrder.getOrderID();
		
		for (OrderDetail singleItem : products) {
			
			if (singleItem.getQuantity() > 1) {
				//context.setRollbackOnly();
				//throw new IllegalQuantityException("Invalid quantity ordered.");
			}
			
			if (singleItem.getQuantity() <= 9 ) {
				//context.setRollbackOnly();
				//throw new IllegalQuantityException("Invalid quantity ordered.");
			}
			
			// set the invoice of each InvoiceLine
			singleItem.setInvoice(newInvoice);
			// persist the InvoiceLine
			entityManager.persist(singleItem);
		}
		
		return orderId;
	}
	
	
	
}
