package northwind.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.security.annotation.SecurityDomain;

import northwind.controller.FindOneInvoiceByID;
import northwind.data.OrderRepository;
import northwind.exception.IllegalQuantityException;
import northwind.exception.InsufficientStockException;
import northwind.exception.NoOrderDetailException;
import northwind.exception.ShippedDateExistsException;
import northwind.model.Order;
import northwind.model.OrderDetail;
import northwind.model.OrderDetailPK;
import northwind.model.Product;
import northwind.model.Shipper;

@Stateless
@SecurityDomain("northwindDomain")
@DeclareRoles({"Administrator","Employee","Customer"})
public class OrderService {
	
	@Resource
	private EJBContext context;
	
	@Inject
	private EntityManager entityManager;

	@Inject
	private OrderRepository orderRepository;
	
	@RolesAllowed({"Employee"})
	public List<Order> findOrdersByDateRange(Date dateOne, Date dateTwo) {
		return orderRepository.findOrderByDateRange(dateOne, dateTwo);
		
	}
	
	@RolesAllowed({"Employee"})
	public List<Order> findOrdersByYearAndMonth(int year, int month){
		return orderRepository.findOrdersByYearAndMonth(year, month);
	}
	
	@RolesAllowed({"Employee"})
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
	
	public void removeOrder(Order currentOrder) throws ShippedDateExistsException {
		if (currentOrder.getShippedDate() != null) {
			context.setRollbackOnly();
			throw new ShippedDateExistsException("Order has been shipped");
		} else {
			List<OrderDetail> items = currentOrder.getOrderDetails();
			for (OrderDetail details : items ) {
				entityManager.remove(entityManager.merge(details));
			}
			entityManager.remove(entityManager.merge(currentOrder));
			
			//orderRepository.remove(currentOrder); // remove order and order details
		}
		
	}	
	
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
