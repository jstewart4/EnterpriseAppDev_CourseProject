package northwind.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import northwind.data.ShipperRepository;
import northwind.model.Shipper;

@Stateless 
public class ShipperService {
	

	@Inject
	private Logger log;
	
	@Inject
	private ShipperRepository shipperRepository;
	
	public List<Shipper> findAll() {
		return shipperRepository.findAll();
	}
	
	public Shipper findOne(int shipperId) {
		Shipper currentShipper = null;
		try {
			currentShipper = shipperRepository.find(shipperId);
		} catch(NoResultException e) {
			log.fine(e.getMessage());
		}
		return currentShipper;
	}
	
	public Shipper updateShipper(Shipper currentShipper) {
		return shipperRepository.edit(currentShipper);
	}
	
	public void deleteShipper(int shipperId) {
		Shipper currentShipper = findOne(shipperId);
		if (currentShipper != null) {
			shipperRepository.remove(currentShipper);
		}
}
	
	
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