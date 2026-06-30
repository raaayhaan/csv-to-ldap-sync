\# CSV to LDAP Synchronization



A Java-based application that synchronizes employee records from CSV files into OpenLDAP.



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



\## LDAP Structure



dc=company,dc=com

└── ou=Organizations

&#x20;   └── ou=Organization

&#x20;       └── ou=Department

&#x20;           └── Employee



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



\## Run



Compile:



javac -cp ".;lib/\*" -d out src/com/rehan/csvtoldap/\*\*/\*.java



Run:



java -cp ".;out;lib/\*" com.rehan.csvtoldap.Main

