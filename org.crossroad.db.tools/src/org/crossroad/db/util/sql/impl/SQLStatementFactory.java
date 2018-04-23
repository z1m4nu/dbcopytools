/**
 * 
 */
package org.crossroad.db.util.sql.impl;

import org.crossroad.db.util.cfg.DriversType;
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
	
	public ISQLFactory create(DriversType dbId)
	{
		ISQLFactory factory  = null;
		
		if(DriversType.MSS.equals(dbId))
		{
			factory = new MSSSQLFactory();
		} else if (DriversType.HDB.equals(dbId))
		{
			factory = new HANASQLFactory();
		} else {
			factory = new BasicSQLFactory();
		}
		return factory;
	}

}
