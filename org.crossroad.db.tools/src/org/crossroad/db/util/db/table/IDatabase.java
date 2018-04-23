package org.crossroad.db.util.db.table;

import org.crossroad.db.util.connection.IConnDef;
import org.crossroad.db.util.sql.ISQLFactory;

public interface IDatabase {

	/**
	 * 
	 * @throws JDBCException
	 */
	boolean openConnection();

	boolean closeConnection();

	ISQLFactory getSQLFactory();
	
	IConnDef getConfiguration();
	
	void commit() throws Exception;
	

}