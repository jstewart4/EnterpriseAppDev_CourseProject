package northwind.data;

import java.util.List;

import northwind.model.Category;
import northwind.report.CategorySalesReport;

public class CategoryRepository extends AbstractJpaRepository<Category> {
	private static final long serialVersionUID = 1L;

	public CategoryRepository() {
		super(Category.class);
	}
	
	public List<CategorySalesReport> findCategoryRevenue(int year) {
		return getEntityManager().createQuery(
				"SELECT new chinook.report.CategorySalesReport( c.categoryName, SUM( (od.unitPrice - (od.unitPrice * od.discount)) * od.quantity ) AS TotalSales )"
				+ "FROM OrderDetail od, IN (od.product) p, IN (p.category) c "
				+ "WHERE YEAR(od.order.shippedDate) = yearValue" 
				+ "GROUP BY c.name "
				+ "ORDER BY c.name"
				, CategorySalesReport.class)
				.setParameter("yearValue", year)
				.getResultList();
	}

}
