package org.crossroad.db.util.cfg.impl;

import org.crossroad.db.util.cfg.IDrvDef;
import org.crossroad.util.log.AbstractLogger;

public class DrvDef extends AbstractLogger implements IDrvDef {
	private String id = null;
	private String className = null;
	private String driverJar = null;
	private String jdbcUrl = null;
	
	

	public DrvDef(String id) {
		this.id = id;
	}
	

	/* (non-Javadoc)
	 * @see org.crossroad.hana.tools.cfg.IDriver#getClassName()
	 */
	@Override
	public String getClassName() {
		return className;
	}

	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/* (non-Javadoc)
	 * @see org.crossroad.hana.tools.cfg.IDriver#getDriverJar()
	 */
	@Override
	public String getDriverJar() {
		return driverJar;
	}

	/**
	 * @param driverJar the driverJar to set
	 */
	public void setDriverJar(String driverJar) {
		this.driverJar = driverJar;
	}

	/* (non-Javadoc)
	 * @see org.crossroad.hana.tools.cfg.IDriver#getJdbcUrl()
	 */
	@Override
	public String getJdbcUrl() {
		return jdbcUrl;
	}

	/**
	 * @param jdbcUrl the jdbcUrl to set
	 */
	public void setJdbcUrl(String jdbcUrl) {
		this.jdbcUrl = jdbcUrl;
	}

	@Override
	public String getID() {
		return this.id;
	}


	
}
