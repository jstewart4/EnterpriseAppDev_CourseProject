package northwind.data;

import java.util.List;

import northwind.model.Product;
import northwind.report.ExpensiveProductReport;

public class ProductRepository extends AbstractJpaRepository<Product> {
	private static final long serialVersionUID = 1L;
	
	public ProductRepository() {
		super(Product.class);
		
	}
	
	public List<Product> findAllByCategory(int categoryId) {
		return getEntityManager().createQuery(
				"SELECT p FROM Product p WHERE p.category.categoryID = :idValue", Product.class)
				.setParameter("idValue", categoryId)
				.getResultList();
	}
	
	public List<Product> findAllBySupplier(int supplierId) {
		return getEntityManager().createQuery(
				"SELECT p FROM Product p WHERE p.supplier.supplierID = :idValue", Product.class)
				.setParameter("idValue", supplierId)
				.getResultList();
	}
	
	public List<ExpensiveProductReport> findTenMostExpensive() {
		return getEntityManager().createQuery(
				"SELECT new northwind.report.ExpensiveProductReport(p.productName, p.unitPrice)"
				+ "FROM Product p "
				+ "ORDER BY p.unitPrice DESC", 
				ExpensiveProductReport.class).setMaxResults(10) .getResultList();
				
	}

}
