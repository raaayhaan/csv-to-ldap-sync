package com.rehan.csvtoldap.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.rehan.csvtoldap.model.Employee;

/**
* Utility class responsible for reading employee records
* from a CSV file and converting them into Employee objects.
*
* <p>The first row of the CSV file is treated as a header
* and is skipped during processing.
*
* <p>Expected CSV column count: 350
*
* @author Rehan I Baig
* @since 1.0
*/

public class CsvReader {
private static final Logger logger = LogManager.getLogger(CsvReader.class);


	
	/**
     	* Reads employee records from the specified CSV file.
     	*
     	* @param filePath path of the CSV file
     	* @return list of Employee objects
     	*/

	public static List<Employee> readEmployees(String filePath){
		List<Employee> employees = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(filePath))) { 
			String line;
			
			//Skip header row
			br.readLine();

			while((line = br.readLine()) != null){
				String [] data = line.split(",");
				Employee employee = new Employee();

				employee.setSsn(data[0]);
                		employee.setFirstName(data[1]);
                		employee.setMiddleName(data[2]);
                		employee.setLastName(data[3]);
                		employee.setAddress(data[4]);
                		employee.setCity(data[5]);
                		employee.setState(data[6]);
                		employee.setCountry(data[7]);
                		employee.setPincode(data[8]);
                		employee.setDeptId(data[9]);
                		employee.setDept(data[10]);
                		employee.setCostCenterId(data[11]);
                		employee.setSalary(data[12]);
                		employee.setDesgn(data[13]);
                		employee.setOrg(data[14]);
                		employee.setDoj(data[15]);
                		employee.setEmpId(data[16]);
                		employee.setLocationId(data[17]);
                		employee.setOfficeLoc(data[18]);
                		employee.setOfficeCountry(data[19]);
                		employee.setDob(data[20]);
                		employee.setGender(data[21]);
				
				employees.add(employee);

			}
		}

		catch(IOException e){
			logger.error("Error while reading file", e);
		}
		return employees;
	}
}
	