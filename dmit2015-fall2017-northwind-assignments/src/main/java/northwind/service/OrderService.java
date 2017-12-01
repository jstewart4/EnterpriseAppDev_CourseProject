package northwind.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;


import northwind.data.OrderRepository;
import northwind.exception.IllegalQuantityException;
import northwind.exception.InsufficientStockException;
import northwind.exception.NoOrderDetailException;
import northwind.model.Order;
import northwind.model.OrderDetail;
import northwind.model.OrderDetailPK;
import northwind.model.Product;
import northwind.model.Shipper;

@Stateless
public class OrderService {
	
	@Resource
	private EJBContext context;
	
	@Inject
	private EntityManager entityManager;

	@Inject
	private OrderRepository orderRepository;
	
	public List<Order> findOrdersByDateRange(Date dateOne, Date dateTwo) {
		return orderRepository.findOrderByDateRange(dateOne, dateTwo);
		
	}
	
	public List<Order> findOrdersByYearAndMonth(int year, int month){
		return orderRepository.findOrdersByYearAndMonth(year, month);
	}
	
	public Order findOneSalesInvoice(int orderId){
		return orderRepository.findOne(orderId);
	}
	
	
	
	
	
	public int createOrder(Order newOrder, List<OrderDetail> products) 
			throws NoOrderDetailException, IllegalQuantityException, InsufficientStockException {
		
		int orderId = 0;
		
		if(products == null || products.size() == 0) {
			context.setRollbackOnly();
			throw new NoOrderDetailException("There are no products in the Order");
		}
		
		entityManager.persist(newOrder);
		orderId = newOrder.getOrderID();
		
		for (OrderDetail singleItem : products) {
			
			if (singleItem.getQuantity() < 1) {
				context.setRollbackOnly();
				throw new IllegalQuantityException("Invalid quantity ordered.");
			}
			
			if (singleItem.getQuantity() > singleItem.getProduct().getUnitsInStock() ) {
				context.setRollbackOnly();
				throw new InsufficientStockException("Not enough stock for quantity ordered.");
			}
			
			OrderDetailPK primaryKey = new OrderDetailPK();
			
			primaryKey.setOrderID(orderId);
			primaryKey.setProductID(singleItem.getProduct().getProductID());
			
			singleItem.setId(primaryKey);
			
			entityManager.persist(singleItem);
		}
		
		return orderId;
	}
	
//	data is passed from the controller into this method here to update it
	
	public int completeOrder(Order updateOrder, BigDecimal freight, Shipper shipper, Date shippedDate ) 
			throws IllegalQuantityException, InsufficientStockException{
		
		updateOrder.setShipper(shipper);
		updateOrder.setFreight(freight);
		updateOrder.setShippedDate(shippedDate);
		
		int orderId = 0;
		
		for (OrderDetail singleItem : updateOrder.getOrderDetails()) {
					
					if (singleItem.getQuantity() < 1) {
						context.setRollbackOnly();
						throw new IllegalQuantityException("Invalid quantity ordered.");
					}
					
					if (singleItem.getQuantity() > singleItem.getProduct().getUnitsInStock() ) {
						context.setRollbackOnly();
						throw new InsufficientStockException("Not enough stock for Product " + singleItem.getProduct().getProductName() + ". Too much quantity ordered.");
					}
					
					Product currentProduct = singleItem.getProduct();
					Short currentUnits = currentProduct.getUnitsInStock();
					currentProduct.setUnitsInStock((short) (currentUnits - singleItem.getQuantity()));
					
					entityManager.merge(currentProduct);
					
				}
				orderId = updateOrder.getOrderID();
				
				entityManager.merge(updateOrder);
		
		return orderId;
	}
		
	
}
