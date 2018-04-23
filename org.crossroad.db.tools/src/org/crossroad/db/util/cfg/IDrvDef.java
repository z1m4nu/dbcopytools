package org.crossroad.db.util.cfg;

public interface IDrvDef {
	
	public static final String HOST = "hostname";
	public static final String PORT = "port";
	public static final String DBNAME = "dbname";

	/**
	 * @return the className
	 */
	String getClassName();

	/**
	 * @return the driverJar
	 */
	String getDriverJar();

	/**
	 * @return the jdbcUrl
	 */
	String getJdbcUrl();

	DriversType getID();
	
	
}