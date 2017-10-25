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

import northwind.report.EmployeeSales;
import northwind.util.Resources;


@RunWith(Arquillian.class) 
public class EmployeeRepositoryTest {
	
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
	private EmployeeRepository employeeRepository;

	@Test
	public void testFindEmployeeSales() {
		List<EmployeeSales> queryResults = employeeRepository.findEmployeeSales();
		assertEquals(9, queryResults.size());
		
		// verify all results
		EmployeeSales result1 = queryResults.get(0);
		assertEquals("Margaret Peacock", result1.getEmployeeName());
		assertEquals(124655.57, result1.getTotalSales().doubleValue(), 0);
		
		EmployeeSales result2 = queryResults.get(1);
		assertEquals("Janet Leverling", result2.getEmployeeName());
		assertEquals(103719.09, result2.getTotalSales().doubleValue(), 0);
		
		EmployeeSales result3 = queryResults.get(2);
		assertEquals("Nancy Davolio", result3.getEmployeeName());
		assertEquals(95850.39, result3.getTotalSales().doubleValue(), 0);
		
		EmployeeSales result4 = queryResults.get(3);
		assertEquals("Andrew Fuller", result4.getEmployeeName());
		assertEquals(71168.14, result4.getTotalSales().doubleValue(), 0);
		
		EmployeeSales result5 = queryResults.get(4);
		assertEquals("Robert King", result5.getEmployeeName());
		assertEquals(59827.20, result5.getTotalSales().doubleValue(), 0);
		
		EmployeeSales result6 = queryResults.get(5);
		assertEquals("Laura Callahan", result6.getEmployeeName());
		assertEquals(56954.04, result6.getTotalSales().doubleValue(), 0);
		
		EmployeeSales result7 = queryResults.get(6);
		assertEquals("Michael Suyama", result7.getEmployeeName());
		assertEquals(40826.37, result7.getTotalSales().doubleValue(), 0);
		
		EmployeeSales result8 = queryResults.get(7);
		assertEquals("Steven Buchanan", result8.getEmployeeName());
		assertEquals(31433.19, result8.getTotalSales().doubleValue(), 0);

		EmployeeSales result9 = queryResults.get(8);
		assertEquals("Anne Dodsworth", result9.getEmployeeName());
		assertEquals(24412.89, result9.getTotalSales().doubleValue(), 0);
	}

}
