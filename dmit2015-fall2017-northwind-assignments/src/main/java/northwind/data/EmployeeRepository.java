package northwind.data;


import java.util.List;

import northwind.model.Employee;
import northwind.report.EmployeeSales;

public class EmployeeRepository extends AbstractJpaRepository<Employee> {
	private static final long serialVersionUID = 1L;

	public EmployeeRepository() {
		super(Employee.class);
	}
	
	public Employee findOne(int employeeId){
		return getEntityManager().createQuery(
				"SELECT emp FROM Employee WHERE emp.employeeID = :idValue", Employee.class)
				.setParameter("idValue", employeeId)
				.getSingleResult();
	}
	
	public List<EmployeeSales> findEmployeeSales() {
		return getEntityManager().createQuery(
				" SELECT new northwind.report.EmployeeSales( CONCAT(e.firstName, ' ', e.lastName) AS name, SUM(od.unitPrice * od.quantity * (1 - od.discount)) AS sale ) "
				+ " FROM OrderDetail od, IN (od.order) o, IN (o.employee) e " 
				+ " WHERE YEAR(o.shippedDate) = 1997 "
				+ " GROUP BY e.firstName, e.lastName "
				+ " ORDER BY sale DESC",
				EmployeeSales.class)
				.getResultList();
	}

}
