package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.omnifaces.util.Messages;

import northwind.data.OrderRepository;
import northwind.model.Order;

@Model
public class OrderController {
	
	private int selectedOrderId; //getter/setter
	private Order selectedOrder; //getter
	
	public void findOrder() {
		if(!FacesContext.getCurrentInstance().isPostback()) {
			if(selectedOrderId > 0) {
				selectedOrder = orderRepository.findOne(selectedOrderId);
				if (selectedOrder == null) {
					Messages.addGlobalInfo("There is no Order with orderId {0}", selectedOrderId);
				}
			} else {
				Messages.addGlobalError("Bad Request. Invaild orderId.");
			}
		}
	}
	
	@Inject
	private OrderRepository orderRepository;
	
	private List<Order> orders;
	
	@PostConstruct
	void init() {
		orders = orderRepository.findAll();
	}
	
	public List<Order> getOrders() {
		return orders;
	}

	public int getSelectedOrderId() {
		return selectedOrderId;
	}

	public void setSelectedOrderId(int selectedOrderId) {
		this.selectedOrderId = selectedOrderId;
	}

	public Order getSelectedOrder() {
		return selectedOrder;
	}
	
	

}
