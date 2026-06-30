package com.rehan.csvtoldap.model;

import java.util.Objects;

/** 
* Represents an Organization-Department pair.
* 
* @author Rehan I Baig
* @since 1.0
*/
public class OrgDepartment { 
	private String organization;
	private String department;
	
	public OrgDepartment(String organization, String department){
		this.organization = organization;
		this.department = department;
	}
	public String getOrganization() {
		return organization;
	}
	public String getDepartment() {
		return department;
	}

	@Override
	public boolean equals(Object obj){
		if(this == obj)
			return true;	

		if(!(obj instanceof OrgDepartment)) {
			return false;
		}
		OrgDepartment other = (OrgDepartment) obj;
		
		return Objects.equals(
			organization, 
			other.organization) 
			&&  
			Objects.equals(
				department, 
				other.department);
		
	}
	
	@Override
	public int hashCode(){
		return Objects.hash(
			organization, 
			department);
	}
}