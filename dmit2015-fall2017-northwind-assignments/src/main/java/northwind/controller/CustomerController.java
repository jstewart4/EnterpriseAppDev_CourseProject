package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.omnifaces.util.Messages;

import northwind.data.CustomerRepository;
import northwind.model.Customer;
import northwind.report.CustomerSales;
import northwind.service.CustomerService;

@Model
public class CustomerController {
	
	
	private String currentSelectedCustomerId;		// getter/setter
	private Customer currentSelectedCustomer;	   // getter
	 	
	 	public void findCustomer() {
	 		if( !FacesContext.getCurrentInstance().isPostback() ) {
	 			if( currentSelectedCustomerId != "") {
	 				currentSelectedCustomer = customerRepository.find(currentSelectedCustomerId);
	 				if( currentSelectedCustomer == null ) {
	 					Messages.addGlobalInfo("There is no customer with customerID {0}", 
	 							currentSelectedCustomerId);
	 				} else {
	 					Messages.addGlobalInfo("Successfully retreived customer info.");
	 				}
	 			} else {
	 				Messages.addGlobalError("Bad request. A valid customerID is required.");
	 			}
	 		}		
	 	}
	
	@Inject
	private CustomerRepository customerRepository;
	
	private List<Customer> customers;
	
	@PostConstruct
	void init() {
		customers = customerRepository.findAll();
	}
	
	public List<Customer> getCustomers() {
		return customers;
	}
	
	public String getCurrentSelectedCustomerId() {
		 return currentSelectedCustomerId;
	}
		  
	public void setCurrentSelectedCustomerId(String currentSelectedCustomerId) {
		 this.currentSelectedCustomerId = currentSelectedCustomerId;
	}

	public Customer getCurrentSelectedCustomer() {
		 return currentSelectedCustomer;
	}
	
	public List<CustomerSales> retrieveCustomerSales(){
		return customerRepository.findCustomerSales();
	}
	
	public List<CustomerSales> retrieveTopCustomerSales(){
		return customerRepository.findTopCustomerSales();
	}
	
	
	
	private Customer currentNewCustomer = new Customer(); //getter/setter
	
	@Inject
	private CustomerService customerService;
	
	public void createNewCustomer() {
		try {
			customerService.createCustomer(currentNewCustomer);
			Messages.addGlobalInfo("Create new customer was successful.");
			
			currentNewCustomer = new Customer();
		} catch(Exception e) {
			Messages.addGlobalWarn("Create new customer was not successful");
		}
	}

	
	public Customer getCurrentNewCustomer() {
		return currentNewCustomer;
	}

	public void setCurrentNewCustomer(Customer currentNewCustomer) {
		this.currentNewCustomer = currentNewCustomer;
	}
	
	public void resetCustomer() {
		currentNewCustomer = new Customer(); //REMOVE
	}
	
	
}


