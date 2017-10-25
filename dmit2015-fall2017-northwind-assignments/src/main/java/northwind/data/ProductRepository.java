package northwind.data;

import java.util.List;

import northwind.model.Product;
import northwind.report.ExpensiveProductReport;
import northwind.report.ProductSalesByYearReport;

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
	
	public List<ProductSalesByYearReport> findProductSales() {
		return getEntityManager().createQuery(
				" SELECT new northwind.report.ProductSalesByYearReport (c.categoryName, p.productName, SUM(od.unitPrice * od.quantity * (1 - od.discount)) AS sale )"
				+ " FROM Product p, IN (p.category) c, IN (p.orderDetails) od, IN (od.order) o "
				+ " WHERE YEAR(o.shippedDate) = 1997 "
				+ " GROUP BY c.categoryName, p.productName "
				+ " ORDER BY sale DESC", 
				ProductSalesByYearReport.class)
				.getResultList();
	}

}
