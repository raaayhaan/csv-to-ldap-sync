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

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.rehan.csvtoldap.model.Employee;

/**
 * Provides LDAP operations related to organizations.
 *
 * <p>This service extracts unique organizations from
 * employee records and creates organization OUs in LDAP.
 *
 * @author Rehan I Baig
 * @since 1.0
 */
public class OrganizationService{
	private static final Logger logger = LogManager.getLogger(OrganizationService.class);
	
	private static final String BASE_DN = "dc=company,dc=com";
		
	private DirContext context;

	/** 
	* creates OrganizationService Object.
	*
	* @param context LDAP context 
	*/
	public OrganizationService(DirContext context){
		this.context = context;
	}
	
	/** 
	* Returns all unique organizations.
	*
	* @param employees employee list
	* @return unique organizations
	*/
	
	public Set<String> getUniqueOrganizations( List<Employee> employees) {
		Set<String> organizations = new HashSet<>();
		for( Employee employee : employees){
			String org = employee.getOrg();
			
			if(org != null && !org.trim().isEmpty()){
				organizations.add(org.trim());
			}
		}
		return organizations;
	}
	
	/**
	* Creates Organizations root ou
	*/
	public void createOrganizationsRootOu(){
		String dn = "ou=Organizations," + BASE_DN;
		try{
			Attributes attrs = new BasicAttributes(true);
			Attribute objectClass = new BasicAttribute("objectClass");
			objectClass.add("top");
			objectClass.add("organizationalUnit");
			
			attrs.put(objectClass);
			attrs.put("ou", "Organizations");
			
			context.createSubcontext(dn, attrs);
			
			logger.info("Organizations root OU created");
		}
		catch (NameAlreadyBoundException e) {
			logger.error("Organizations root Ou already exists");
		}
		catch (NamingException e){
			logger.error("Error creating Organizations OU", e);
		}	
	}
	
	
    	/**
     	* Creates organization OU.
     	*
     	* @param organization organization name
     	*/
	public void createOrganization(String organization){
		String sanitizedOrg = sanitizeValue(organization);
		String dn = "ou=" + sanitizedOrg +",ou=Organizations," + BASE_DN;
		
		try {
			Attributes attrs = new BasicAttributes(true);
			Attribute objectClass = new BasicAttribute("objectClass");
			objectClass.add("top");
			objectClass.add("organizationalUnit");
			
			attrs.put(objectClass);
			attrs.put("ou", sanitizedOrg);
			context.createSubcontext(dn, attrs);
	
			logger.info("Organization Created : {}", organization);
		}
		catch (NameAlreadyBoundException e) {
			logger.error("Organization already exists", e);
		}
		catch (NamingException e) {
            		logger.error("Unable to create organization : " + organization, e);
		}
	}

	/**
     	* Checks whether organization OU exists.
     	*
     	* @param organization organization name
     	* @return true if organization exists
     	*/
	public boolean organizationExists(String organization){
		String dn = 
			"ou=" 
			+ sanitizeValue(organization)
			+",ou=Organizations,"
			+BASE_DN;
		try{
			context.getAttributes(dn);
			return true;
		}
		catch (NameNotFoundException e){
			return false;
		}
		catch (NamingException e){
			logger.error("Error checking organization", e);
			return false;
		}
	}
	
	
	/**
     	* Converts LDAP unsafe characters.
     	*
     	* @param value input value
     	* @return sanitized value
     	*/
	private String sanitizeValue(String value){
		return value.trim().replace(" ", "_");
	}
}














