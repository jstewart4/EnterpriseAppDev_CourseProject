package northwind.data;

import java.util.List;

import northwind.model.Shipper;

public class ShipperRepository extends AbstractJpaRepository<Shipper>{
	private static final long serialVersionUID = 1L;
	
	public ShipperRepository() {
		super(Shipper.class);
	}
	
}
