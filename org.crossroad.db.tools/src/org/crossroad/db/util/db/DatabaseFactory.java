/**
 * 
 */
package org.crossroad.db.util.db;

import org.crossroad.db.util.connection.IConnDef;
import org.crossroad.db.util.db.impl.AbstractDatabase;
import org.crossroad.db.util.db.impl.Database;
import org.crossroad.db.util.db.impl.FileDatabase;
import org.crossroad.db.util.driver.DriversType;

/**
 * @author e.soden
 *
 */
public final class DatabaseFactory {

	/**
	 * 
	 */
	private DatabaseFactory() {
	}
	
	public static AbstractDatabase create(String type, IConnDef definition)
	{
		AbstractDatabase database = null;
		boolean fReader = false;
		if("source".equalsIgnoreCase(type))
		{
			fReader = true;
		} else {
			fReader = false;
		}
		
		if (DriversType.CSV.equals(definition.getDriverId()))
		{
			database = new FileDatabase(definition, fReader);
		} else {
			database = new Database(definition);
		}
		return database;
	}

}
