package northwind.data;


import northwind.model.Employee;

public class EmployeeRepository extends AbstractJpaRepository<Employee> {
	private static final long serialVersionUID = 1L;

	public EmployeeRepository() {
		super(Employee.class);
	}
	
	public Employee findOne(int employeeId){
		return getEntityManager().createQuery(
				"SELECT emp FROM Employee WHERE emp.employeeId = :idValue", Employee.class)
				.setParameter("idValue", employeeId)
				.getSingleResult();
	}

}
