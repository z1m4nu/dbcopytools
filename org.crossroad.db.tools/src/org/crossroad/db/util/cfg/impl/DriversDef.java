/**
 * 
 */
package org.crossroad.db.util.cfg.impl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author e.soden
 *
 */
public final class DriversDef {
	private static final DriversDef instance = new DriversDef();

	private Map<String, DrvDef> db = new HashMap<String, DrvDef>();

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
	public DrvDef get(String id)
	{
		return this.db.get(id);
	}

	public boolean containsDriver(String id)
	{
		return this.db.containsKey(id);
	}
}
