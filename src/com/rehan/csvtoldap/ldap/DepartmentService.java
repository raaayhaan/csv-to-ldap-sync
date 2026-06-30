package com.rehan.csvtoldap.ldap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rehan.csvtoldap.model.Employee;
import com.rehan.csvtoldap.model.OrgDepartment;

/**
* Provides LDAP operations related to department,
*
* <p>Departments are created under their
* respective organization OUs
*
* @author Rehan I Baig
* @since 1.0
*/
public class DepartmentService {
	private static final Logger logger = LogManager.getLogger(DepartmentService.class);
	
	private static final String BASE_DN = "dc=company,dc=com";
	private DirContext context;

	/** 
	* Creates DepartmentService Object.
	*
	* @param context LDAP context
	*/
	public DepartmentService(DirContext context){
		this.context = context;
	}
	
	/** 
	* Extracts unique organization-department pairs
	* 
	* @param employees employee list
	* @return unique department pairs
	*/
	public Set<OrgDepartment> getUniqueDepartments(List<Employee> employees){
		Set<OrgDepartment> departments = new HashSet<>();

		for(Employee employee : employees){
			String organization = employee.getOrg();
			String department = employee.getDept();

			if(organization != null && 
				department != null && 
				!organization.trim().isEmpty() && 
				!department.trim().isEmpty())
			{
				departments.add(new OrgDepartment(organization.trim(), department.trim()));	
			}
		}
		return departments;
	}
	
	/** 
	* Creates a department under an Organization.
	*
	* @param organization organization name
	* @param department department name
	*/
	
	public void createDepartment(String organization, String department) {
		String sanitizeOrg = sanitizeValue(organization);
		String sanitizeDept = sanitizeValue(department);
		String dn =
			"ou="
			+ sanitizeDept
			+",ou="
			+ sanitizeOrg
			+ ",ou=Organizations,"
			+ BASE_DN;

		try {
			Attributes attributes = new BasicAttributes(true);
			Attribute objectClass = new BasicAttribute("objectClass");
			
			objectClass.add("top");
			objectClass.add("organizationalUnit");

            		attributes.put(objectClass);
            		attributes.put("ou", sanitizeDept);
            		context.createSubcontext(dn, attributes);
			
			logger.info("Department created : {} --> {}", organization, department);
		}
		catch(NameAlreadyBoundException e) {
    			logger.info("Department Already Exists : {} --> {}", organization, department);
		}
		catch(NamingException e){
			logger.error("Unable to create department : " + department, e);
		}
	}

	/** 
	* Checks whether department exists.
	* 
	* @param organization organization name
	* @param department department name
	* @return true if exists
	*/
	public boolean departmentExists(String organization, String department){
		String dn = 
			"ou="
			+ sanitizeValue(department)
			+",ou="
			+ sanitizeValue(organization)
			+ ",ou=Organizations,"
			+ BASE_DN;
		try{
			context.getAttributes(dn);
			return true;	
		}
		catch(NameNotFoundException e){
			return false;
		}
		catch(NamingException e){
			logger.error("Error checking department", e);
			return false;	
		}
	}
	
	/**
	* Sanitizes LDAP DN Values
	* 
	* @param value raw value
	* @return sanitized value
	*/
	private String sanitizeValue(String value){
		return value.trim().replace(" ", "_");
	}
}