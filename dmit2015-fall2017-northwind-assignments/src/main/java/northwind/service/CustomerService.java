package northwind.service;

import javax.ejb.Stateless;

import northwind.data.CustomerRepository;
import northwind.model.Customer;

@Stateless
public class CustomerService {

	private CustomerRepository customerRepository;
	
	public void createCustomer(Customer currentCustomer) {
		customerRepository.persist(currentCustomer);
	}
}
