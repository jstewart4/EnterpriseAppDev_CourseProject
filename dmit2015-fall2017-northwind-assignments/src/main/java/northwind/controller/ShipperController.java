package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import org.hibernate.validator.constraints.NotBlank;
import org.omnifaces.util.Messages;

import northwind.data.ShipperRepository;
import northwind.model.Shipper;
import northwind.service.ShipperService;

@Model
public class ShipperController {
	
	@Inject
	private ShipperRepository shipperRepository;
	
	private List<Shipper> shippers;
	
	@PostConstruct
	void init() {
		shippers = shipperRepository.findAll();
	}
	
	public List<Shipper> getShippers(){
		return shippers;
	}
	
	@Inject
	private ShipperService shipperService;
	
	@NotBlank(message="Shipper Name value is required.")
	private String shipperName;	// +getter+setter
	private String phone; 
	public void createNewshipper() {
		try {
			shipperService.createShipper(shipperName, phone);
			Messages.addGlobalInfo("Create Shipper was successful.");
			shipperName = "";
			phone = "";
		} catch(Exception e) {
			Messages.addGlobalError("Error creating shipper with exception: {0}", 
					e.getMessage());
			//Messages.addGlobalWarn("Create shipper was not successful.");
		}
	}

	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
