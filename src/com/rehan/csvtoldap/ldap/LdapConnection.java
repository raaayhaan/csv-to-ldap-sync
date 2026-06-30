package com.rehan.csvtoldap.ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages LDAP server connectivity.
 *
 * <p>This class is responsible for establishing and
 * maintaining a connection with the OpenLDAP server.
 *
 * <p>The class provides methods to:
 * <ul>
 *     <li>Connect to LDAP</li>
 *     <li>Retrieve the LDAP context</li>
 *     <li>Close the LDAP connection</li>
 * </ul>
 *
 * @author Rehan I Baig
 * @since 1.0
 */


public class LdapConnection{
	private static final Logger logger = LogManager.getLogger(LdapConnection.class);

	private String host;
	private String port;
	private String bindDn;
	private String password;

	private DirContext context;
	


	/**
 	* Creates a new LDAP connection object.
 	*
 	* @param host LDAP server host name or IP address
 	* @param port LDAP server port number
 	* @param bindDn LDAP administrator distinguished name
 	* @param password LDAP administrator password
 	*/
	public LdapConnection(String host, 
			String port,
			String bindDn, 
			String password)
	{
		this.host = host;
		this.port = port;
		this.bindDn = bindDn;
		this.password = password;
	}


	/**
 	* Establishes a connection with the LDAP server.
 	*
 	* <p>Uses simple authentication to bind with the LDAP
 	* administrator account.
 	*
 	* @throws NamingException if connection or authentication fails
 	*/
	public void connect() throws NamingException {
		String ldapUrl = "ldap://" + host + ":" + port;
		Hashtable<String, String> env = new Hashtable<>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapUrl);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, bindDn);
		env.put(Context.SECURITY_CREDENTIALS, password);
		context = new InitialDirContext(env);
		logger.info("Ldap connection Successful");
	}


	/**
 	* Returns the active LDAP context.
 	*
 	* @return active LDAP directory context
 	*/
	public DirContext getContext(){
		return context;
	}
	

	/**
 	* Closes the LDAP connection.
 	*
 	* <p>If the connection is already closed, no action
 	* will be performed.
 	*/
	public void disconnect(){
		try{
			if(context != null){
				context.close();
				logger.info("Ldap connection closed");
			} 
		}
		
		catch (Exception e){
			logger.error("Error closing Ldap connection ", e);
		}
	}
}

			