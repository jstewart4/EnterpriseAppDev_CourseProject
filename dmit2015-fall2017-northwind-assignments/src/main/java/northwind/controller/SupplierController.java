package northwind.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.omnifaces.util.Messages;

import northwind.data.SupplierRepository;
import northwind.model.Supplier;

@Model
public class SupplierController {
	private int currentSelectedSupplierId; //getter/setter
	private Supplier currentSelectedSupplier; //getter
	
	public void findSupplier() {
		if( !FacesContext.getCurrentInstance().isPostback()) {
			if( currentSelectedSupplierId > 0) {
				currentSelectedSupplier = supplierRepository.find(currentSelectedSupplierId);
				if( currentSelectedSupplier == null) {
					Messages.addGlobalInfo("There is no supplier with supplierID {0}", currentSelectedSupplierId);
				} else {
					Messages.addGlobalInfo("Successfully retrieved supplier info.");
				}
			} else {
				Messages.addGlobalError("Bad request. A valid supplierID is required.");
			}
		}
	}
	
	@Inject
	private SupplierRepository supplierRepository;
	
	private List<Supplier> suppliers;
	
	@PostConstruct
	void init() {
		suppliers = supplierRepository.findAll();
	}
	
	public List<Supplier> getSuppliers(){
		return suppliers;
	}

	public int getCurrentSelectedSupplierId() {
		return currentSelectedSupplierId;
	}

	public void setCurrentSelectedSupplierId(int currentSelectedSupplierId) {
		this.currentSelectedSupplierId = currentSelectedSupplierId;
	}

	public Supplier getCurrentSelectedSupplier() {
		return currentSelectedSupplier;
	}
	
	
	
}