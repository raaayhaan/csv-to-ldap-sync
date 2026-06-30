package com.rehan.csvtoldap;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.rehan.csvtoldap.ldap.DepartmentService;
import com.rehan.csvtoldap.ldap.EmployeeService;
import com.rehan.csvtoldap.ldap.LdapConnection;
import com.rehan.csvtoldap.ldap.OrganizationService;
import com.rehan.csvtoldap.model.Employee;
import com.rehan.csvtoldap.model.OrgDepartment;
import com.rehan.csvtoldap.reader.CsvReader;

/**
* Entry point for CSV to LDAP synchronization
*
* <p>Reads employee data from CSV and imports
* organizations, department and employees
* into openLDAP
*
* @author Rehan I Baig
* @since 1.0
*/

public class Main{
	private static final Logger logger = LogManager.getLogger(Main.class);
	
	public static void main(String[] args){
		LdapConnection ldapConnection = null;
		try{
			logger.info("Loading LDAP configuration");

			Properties properties = new Properties();
			properties.load(new FileInputStream("ldap.properties"));
			String host = properties.getProperty("ldap.host");
			String port = properties.getProperty("ldap.port");
			String bindDn = properties.getProperty("ldap.binddn");
			String password = properties.getProperty("ldap.password");
			String csvPath = properties.getProperty("csv.path");
			
			ldapConnection = 
				new LdapConnection(
					host,
					port,
					bindDn,
					password);
			ldapConnection.connect();
		
			logger.info("Reading Employee Data");
			
			List<Employee> employees = CsvReader.readEmployees(csvPath);
			logger.info("Employee read : {}", employees.size());
			
			OrganizationService organizationService = new OrganizationService(ldapConnection.getContext());
			DepartmentService departmentService = new DepartmentService(ldapConnection.getContext());
			EmployeeService employeeService = new EmployeeService(ldapConnection.getContext());
			
			organizationService.createOrganizationsRootOu();
			
			Set<String> organizations = organizationService.getUniqueOrganizations(employees);
			
			for(String organization : organizations){
				organizationService.createOrganization(organization);
			}
			
			logger.info("Organizations Processes : {}", organizations.size());

			Set<OrgDepartment> departments = departmentService.getUniqueDepartments(employees);

			for(OrgDepartment department : departments){
				departmentService.createDepartment(department.getOrganization(), department.getDepartment());
			}
			logger.info("Department Processed : {}", departments.size());
			
			int employeeCount = 0;
			System.out.println("Starting Employee Creation");
			for(Employee employee : employees){
				employeeService.createOrUpdateEmployee(employee);
				employeeCount++;
			}
			logger.info("CSV to Ldap Synchronization Completed Successfully");
		}
		catch(Exception e) {
			logger.error("Application Failed", e);
		}
		finally{
			if(ldapConnection != null){
				ldapConnection.disconnect();
			}
		}
	}
}

			
			












