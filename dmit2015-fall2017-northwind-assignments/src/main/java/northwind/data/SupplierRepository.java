package northwind.data;


import northwind.model.Supplier;

public class SupplierRepository extends AbstractJpaRepository<Supplier>{
	private static final long serialVersionUID = 1L;
	
	public SupplierRepository() {
		super(Supplier.class);
	}
	
	public Supplier findSupplier(int supplierId) {
		return getEntityManager().createQuery("SELECT s FROM Supplier s WHERE s.supplierID = :idValue", Supplier.class).setParameter("idValue", supplierId).getSingleResult();
	}
}
