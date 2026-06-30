package com.rehan.csvtoldap.ldap;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;

import com.rehan.csvtoldap.model.Employee;


/**
 * Maps Employee objects to LDAP Attributes.
 *
 * @author Rehan
 * @since 1.0
 */
public class EmployeeLdapMapper{
	
	/**
	* converts an employee object into LDAP attributes
	*
	* @param employee Employee object
	* @return LDAP attributes 
	*/

	public Attributes mapEmployee(Employee employee){
		Attributes attributes = new BasicAttributes(true);
		Attribute objectClass = new BasicAttribute("objectClass");
		objectClass.add("top");
		objectClass.add("person");
		objectClass.add("organizationalPerson");
		objectClass.add("inetOrgPerson");
		objectClass.add("employeeDetails");


		attributes.put(objectClass);
		attributes.put("uid", employee.getEmpId());
        	attributes.put("cn", employee.getFirstName() + " " + employee.getLastName());
		attributes.put("givenName", employee.getFirstName());
		attributes.put("sn", employee.getLastName());
		attributes.put("title", employee.getDesgn());
	        addAttribute(attributes, "street", employee.getAddress());
	        attributes.put("l", employee.getCity());
	        attributes.put("st", employee.getState());
		String countryCode = getCountryCode(employee.getCountry());
	        //attributes.put("c", countryCode);
	        addAttribute(attributes, "postalCode", employee.getPincode());
	        addAttribute(attributes, "physicalDeliveryOfficeName", employee.getOfficeLoc());
	        attributes.put("o", employee.getOrg());

        	/* Custom Attributes */

        	addAttribute(attributes, "employeeSSN", employee.getSsn());
	        addAttribute(attributes, "departmentId", employee.getDeptId());
	        addAttribute(attributes, "costCenterId", employee.getCostCenterId());
	        addAttribute(attributes, "employeeSalary", employee.getSalary());
	        addAttribute(attributes, "dateOfJoining", employee.getDoj());
	        addAttribute(attributes, "locationId", employee.getLocationId());
		addAttribute(attributes, "officeCountry", employee.getOfficeCountry());
        	addAttribute(attributes, "dateOfBirth", employee.getDob());
		addAttribute(attributes, "gender", employee.getGender());

		return attributes;
	}
	/**
 	* Converts country names to LDAP country codes.
 	*
 	* @param country country name
 	* @return ISO country code
 	*/
	private String getCountryCode(String country) {

    		if (country == null) {

        		return "IN";
    		}

    		switch (country.trim().toUpperCase()) {
			case "INDIA":
            		return "IN";

        		case "US":
        		case "USA":
        		case "UNITED STATES":
            		return "US";

        		default:
            		return "IN";
    		}
	}
	
	private void addAttribute(
        	Attributes attributes,
        	String attributeName,
        	String value){

    		if (value != null && !value.trim().isEmpty()) {	
			attributes.put(attributeName, value.trim());
		}
	}	

}



		









