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
				"SELECT new northwind.report.CategorySalesReport( c.categoryName, SUM( (o.unitPrice - (o.unitPrice * o.discount)) * o.quantity ) AS categoryRevenue ) "
				+ "FROM OrderDetail o, IN (o.product) p, IN (p.category) c "
				+ "WHERE YEAR(o.order.shippedDate) = :yearValue " 
				+ "GROUP BY c.categoryName "
				+ "ORDER BY c.categoryName "
				, CategorySalesReport.class)
				.setParameter("yearValue", year)
				.getResultList();
	}

}
