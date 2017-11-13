package northwind.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import northwind.data.ShipperRepository;
import northwind.model.Shipper;



@Stateless 
public class ShipperService {
	@Inject
	private ShipperRepository shipperRepository;
	
	public void createShipper(String ShipperName, String phone) {
		Shipper currentShipper = new Shipper();
		currentShipper.setCompanyName(ShipperName);
		currentShipper.setPhone(phone);
		createShipper(currentShipper);
	}
	
	public void createShipper(Shipper currentShipper) {
		shipperRepository.persist(currentShipper);
	}
}