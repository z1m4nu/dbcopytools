package org.crossroad.db.util.cfg.impl;

import org.crossroad.db.util.cfg.DriversType;
import org.crossroad.db.util.cfg.IConnDef;

public class ConnDef implements IConnDef {
    private DriversType driverId = null;
	private String databaseName = null;
	private String schemaName = null;
	private String serverName = null;
	private int serverPort = 0;
	private String userName = null;
	private String password = null;
	private String id = null;
	
	public ConnDef(String id) {
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see org.crossroad.hana.tools.cfg.IDatabase#getDatabaseName()
	 */
	@Override
	public String getDatabaseName() {
		return databaseName;
	}
	/**
	 * @param databaseName the databaseName to set
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	/* (non-Javadoc)
	 * @see org.crossroad.hana.tools.cfg.IDatabase#getSchemaName()
	 */
	@Override
	public String getSchemaName() {
		return schemaName;
	}
	/**
	 * @param schemaName the schemaName to set
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	/* (non-Javadoc)
	 * @see org.crossroad.hana.tools.cfg.IDatabase#getServerName()
	 */
	@Override
	public String getServerName() {
		return serverName;
	}
	/**
	 * @param serverName the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	/* (non-Javadoc)
	 * @see org.crossroad.hana.tools.cfg.IDatabase#getServerPort()
	 */
	@Override
	public int getServerPort() {
		return serverPort;
	}
	/**
	 * @param serverPort the serverPort to set
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	/* (non-Javadoc)
	 * @see org.crossroad.hana.tools.cfg.IDatabase#getUserName()
	 */
	@Override
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/* (non-Javadoc)
	 * @see org.crossroad.hana.tools.cfg.IDatabase#getPassword()
	 */
	@Override
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public DriversType getDriverId() {
		return this.driverId;
	}
	
	public void setDriverId(DriversType id)
	{
		this.driverId = id;
	}

	@Override
	public String getId() {
		return this.id;
	}
	

	

}
