package org.crossroad.db.util.db;

import org.crossroad.db.util.cfg.IConnDef;
import org.crossroad.db.util.sql.ISQLFactory;

public interface IDatabase {

	/**
	 * 
	 * @throws JDBCException
	 */
	boolean openConnection();

	boolean closeConnection();

	//Connection getConnection();
	
	ISQLFactory getSQLFactory();
	
	IConnDef getConfiguration();
	
	void commit() throws Exception;
	


}