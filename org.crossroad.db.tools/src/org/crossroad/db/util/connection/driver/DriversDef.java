/**
 * 
 */
package org.crossroad.db.util.connection.driver;

import java.util.HashMap;
import java.util.Map;

import org.crossroad.db.util.connection.IConnDef;
import org.crossroad.db.util.connection.driver.impl.DrvDef;

/**
 * @author e.soden
 *
 */
public final class DriversDef {
	private static final DriversDef instance = new DriversDef();

	private Map<DriversType, DrvDef> db = new HashMap<DriversType, DrvDef>();

	private DriversDef()
	{
		
	}
	/**
	 * 
	 * @return
	 */
	public static DriversDef getInstance()
	{
		return instance;
	}
	/**
	 * 
	 * @param def
	 */
	public void add(DrvDef def)
	{
		this.db.put(def.getID(), def);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public DrvDef get(DriversType id)
	{
		return this.db.get(id);
	}

	public boolean containsDriver(DriversType id)
	{
		return this.db.containsKey(id);
	}
	
	public String formatJDBCUrl(IConnDef connDef) {
		String jdbcUrl = null;
		if (containsDriver(connDef.getDriverId()))
		{
			jdbcUrl = get(connDef.getDriverId()).getJdbcUrl();
			if (jdbcUrl != null) {

				jdbcUrl = jdbcUrl.replaceAll(IDrvDef.DBNAME, connDef.getDatabaseName());
				jdbcUrl = jdbcUrl.replaceAll(IDrvDef.HOST, connDef.getServerName());
				jdbcUrl = jdbcUrl.replaceAll(IDrvDef.PORT, Integer.valueOf(connDef.getServerPort()).toString());

			}
		}
		
		
		return jdbcUrl;
	}
		
}
