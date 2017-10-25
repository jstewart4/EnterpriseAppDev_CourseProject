package northwind.data;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import northwind.report.ProductSalesByYearReport;
import northwind.util.Resources;

@RunWith(Arquillian.class)
public class ProductRepositoryTest {
	
	@Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
        		.addPackage("northwind.model")
        		.addPackage("northwind.report")
        		.addPackage("northwind.data")
                .addClasses(Resources.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                // Deploy our test datasource
                .addAsWebInfResource("test-ds.xml");
    }
	
	@Inject
	private ProductRepository productRepository;

	@Test
	public void shouldFindProductSalesByYearReport() {
		List<ProductSalesByYearReport> queryResults = productRepository.findProductSales();
		assertEquals(77, queryResults.size());
		
		// verify the first, middle, and last result 
		ProductSalesByYearReport firstResult = queryResults.get(0);
		assertEquals("Beverages", firstResult.getCategoryName());
		assertEquals("Cte de Blaye", firstResult.getProductName());
		assertEquals(46563.09, firstResult.getTotalSales().doubleValue(), 0);
		
		ProductSalesByYearReport middleResult = queryResults.get(queryResults.size() / 2 - 1);
		assertEquals("Beverages", middleResult.getCategoryName());
		assertEquals("Steeleye Stout", middleResult.getProductName());
		assertEquals(5706.90, middleResult.getTotalSales().doubleValue(), 0);		
		
		ProductSalesByYearReport lastResult = queryResults.get(queryResults.size() - 1);
		assertEquals("Condiments", lastResult.getCategoryName());
		assertEquals("Chef Anton's Gumbo Mix", lastResult.getProductName());
		assertEquals(373.63, lastResult.getTotalSales().doubleValue(), 0);
	}
}
