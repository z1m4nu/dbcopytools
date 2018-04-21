package org.crossroad.db.util.db;

import java.sql.Connection;

import org.crossroad.db.util.sql.ISQLFactory;

public interface IDatabase {

	/**
	 * 
	 * @throws JDBCException
	 */
	boolean openConnection();

	boolean closeConnection();

	Connection getConnection();
	
	ISQLFactory getSQLFactory();
	


}