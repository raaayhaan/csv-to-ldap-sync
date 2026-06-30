package com.rehan.csvtoldap.ldap;

import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rehan.csvtoldap.model.Employee;
	
/**
* Provides Ldap operations related to employees.
* 
* <p> This service is responisble for creating, searching
* and updating employee records in LDAP
*
* @author Rehan I Baig
* @since 1.0
*/

public class EmployeeService{
	private static final Logger logger = LogManager.getLogger(EmployeeService.class);
	
	private static final String BASE_DN = "dc=company,dc=com";
	private DirContext context;
	private EmployeeLdapMapper mapper;
	
	/** 
	* Creates EmployeeService object
	* @param context LDAP context
	*/
	public EmployeeService(DirContext context){
		this.context = context;
		this.mapper = new EmployeeLdapMapper();
	}
	
	/** 
	* checks whether an employee already exists.
	*
	* @param employee employee object
	* @return true if employee exists
	*/
	
	public boolean employeeExists(Employee employee) {
		String employeeDn = buildEmployeeDn(employee);
		try{
			context.getAttributes(employeeDn);
			return true;
		}
		catch (NameNotFoundException e){
			return false;
		}
		catch(NamingException e){
			logger.error("Error checking employee existence", e);
			return false;
		}
	}

	/**
	* Create employee LDAP entry
	*
	* @param employee employee object
	*/

	public void createEmployee (Employee employee){
		String employeeDn = buildEmployeeDn(employee);
		try{
			Attributes attributes = mapper.mapEmployee(employee);
			context.createSubcontext(employeeDn, attributes);
			logger.info("Employee created {} ", employee.getEmpId());
		}
		catch (NamingException e){
			logger.error("Unable to create employee : " + employee.getEmpId(), e);
		}
	}

	/** 
	* updates employee record
	* 
	* <p>Currently logs employee existence 
	* Full LDAP modification can be added later
	*
	* @param employee employee object
	*/
	public void updateEmployee(Employee employee){
		logger.info("Employee already exits : {}", employee.getEmpId());

	}

	/**
	* Creates employee if not present
	* Updates employee if present
	*
	* @param employee employee object
	*/
	public void createOrUpdateEmployee(Employee employee){
		if(employeeExists(employee)){
			updateEmployee(employee);
		} else {
			createEmployee(employee);
		}
	}
	
	/** 
	* Builds employee distinguished name.
	* 
	* @param employee employee object 
	* @return employee DN
	*/
	private String buildEmployeeDn(Employee employee){
		String organization = sanitizeValue(employee.getOrg());
		String department = sanitizeValue(employee.getDept());
	
		return "uid="
			+ employee.getEmpId()
			+ ",ou="
			+ department
			+ ",ou="
			+ organization
			+ ",ou=Organizations,"
			+ BASE_DN;
	}

	/**
	* Sanitizes LDAP DN values.
	* 
	* @param value raw value
	* @return sanitized value
	*/
	private String sanitizeValue(String value){
		if(value == null){
			return "";
		}
		return value.trim().replace(" ", "_");
	}
}



