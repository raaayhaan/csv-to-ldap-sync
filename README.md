\# CSV to LDAP Synchronization



A Java-based application that synchronizes employee records from CSV files into OpenLDAP.



\## Project Overview



This project imports employee information from a CSV file into OpenLDAP.



The application automatically:



\- Creates Organizations

\- Creates Departments under Organizations

\- Creates Employee entries

\- Supports custom LDAP schema attributes



\## Project Flow



CSV File

&#x20;   ↓

Java Application

&#x20;   ↓

Organization Creation

&#x20;   ↓

Department Creation

&#x20;   ↓

Employee Creation

&#x20;   ↓

OpenLDAP



\## Features



\- Organization Creation

\- Department Creation

\- Employee Synchronization

\- Custom LDAP Schema Support

\- CSV Parsing

\- OpenLDAP Integration

\- Automatic LDAP Hierarchy Creation



\## Technologies Used



\- Java

\- JNDI

\- OpenLDAP

\- Log4j2



\## Architecture



\### Components



\- CsvReader

&#x20; - Reads employee data from CSV



\- OrganizationService

&#x20; - Creates and manages Organizations



\- DepartmentService

&#x20; - Creates and manages Departments



\- EmployeeService

&#x20; - Creates and updates Employee LDAP entries



\- EmployeeLdapMapper

&#x20; - Maps Employee objects to LDAP attributes



\## LDAP Structure



dc=company,dc=com

└── ou=Organizations

&#x20;   └── ou=Organization

&#x20;       └── ou=Department

&#x20;           └── Employee



\## Screenshots



\### LDAP Directory Structure



!\[LDAP Structure](screenshots/ldap-structure.png)



\## Results



The application successfully:



\- Creates Organizations in OpenLDAP

\- Creates Departments under Organizations

\- Creates Employee records

\- Supports custom LDAP attributes

\- Handles duplicate entries

\- Validates and updates





\## Custom LDAP Attributes



\- employeeSSN

\- departmentId

\- costCenterId

\- employeeSalary

\- dateOfJoining

\- locationId

\- officeCountry

\- dateOfBirth

\- gender



\## Configuration



Create a file named:



ldap.properties



Use ldap.properties.example as reference.



\## Setup



\### Prerequisites



\- Java 8+

\- OpenLDAP

\- Git



\### Configuration



Create a file named:



`ldap.properties`



Use `ldap.properties.example` as reference.



\### Compile



```bash

javac -cp ".;lib/\*" -d out src/com/rehan/csvtoldap/\*\*/\*.java

```



\### Run



```bash

java -cp ".;out;lib/\*" com.rehan.csvtoldap.Main

```



\## Learning Outcomes



\- Java JNDI

\- OpenLDAP

\- LDAP Schema Extension

\- CSV Processing

\- Logging with Log4j2

\- Directory Information Tree Design



\## Author



Rehan I Baig



Project – CSV to LDAP Synchronization using Java and OpenLDAP

