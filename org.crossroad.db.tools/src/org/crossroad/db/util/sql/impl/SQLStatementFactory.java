/**
 * 
 */
package org.crossroad.db.util.sql.impl;

import org.crossroad.db.util.db.impl.AbstractSQLFactory;
import org.crossroad.db.util.sql.ISQLFactory;

/**
 * @author e.soden
 *
 */
public class SQLStatementFactory extends AbstractSQLFactory {

	private static final SQLStatementFactory instance = new SQLStatementFactory();
	/**
	 * 
	 */
	private SQLStatementFactory() {
	}
	
	public static SQLStatementFactory getInstance()
	{
		return instance;
	}
	
	public ISQLFactory create(String dbId)
	{
		ISQLFactory factory  = null;
		
		if("mssql".equalsIgnoreCase(dbId))
		{
			factory = new MSSSQLFactory();
		} else if ("hana".equalsIgnoreCase(dbId))
		{
			factory = new HANASQLFactory();
		} else {
			factory = new BasicSQLFactory();
		}
		return factory;
	}

}
