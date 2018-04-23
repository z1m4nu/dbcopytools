/**
 * 
 */
package org.crossroad.db.util.cfg;

import java.io.FileInputStream;
import java.util.Properties;

import org.crossroad.db.util.cfg.impl.ConnDef;
import org.crossroad.db.util.cfg.impl.DriversDef;
import org.crossroad.db.util.cfg.impl.DrvDef;
import org.crossroad.db.util.cfg.impl.MemberDef;
import org.crossroad.db.util.db.impl.Database;
import org.crossroad.db.util.db.impl.TableFactory;
import org.crossroad.util.cfg.AbstractConfiguration;
import org.crossroad.util.cfg.DirHelper;

/**
 * @author e.soden
 *
 */
public class Configuration extends AbstractConfiguration {

	/**
	 * @param homeDir
	 */
	public Configuration(String homeDir) {
		super(homeDir);
	}

	/* (non-Javadoc)
	 * @see org.crossroad.util.cfg.AbstractConfiguration#customLoad()
	 */
	@Override
	protected void customLoad() throws Exception {
		loadDrivers();

	}
	/**
	 * 
	 * @throws Exception
	 */
	private void loadDrivers() throws Exception {
		Properties drv = new Properties();
		drv.load(new FileInputStream(DirHelper.getInstance().getConfPath("drivers.properties")));

		for (Object key : drv.keySet()) {
			String drvId = key.toString().split("\\.")[0];
			DriversType dType = DriversType.fromString(drvId);
			if (!DriversDef.getInstance().containsDriver(dType)) {
				DrvDef driver = new DrvDef(dType);
				driver.setClassName(drv.getProperty(drvId + ".driver.class"));
				driver.setDriverJar(DirHelper.getInstance().getLibPath(drv.getProperty(drvId + ".driver.jar")));
				driver.setJdbcUrl(drv.getProperty(drvId + ".driver.url"));

							
				DriversDef.getInstance().add(driver);
			}
		}
	}
	
	public String getDBValue(String dbId, String suffix) {
		return getProperties().getProperty(dbId + ".database." + suffix, null);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public IConnDef getConnectionId(String id) throws Exception
	{
		ConnDef connDef = null;
		if (getDBValue(id, "driver") != null)
		{
			connDef = new ConnDef(id);
			
			connDef.setDatabaseName(getDBValue(id, "dbname"));
			connDef.setDriverId(DriversType.fromString(getDBValue(id, "driver")));
			connDef.setServerName(getDBValue(id, "hostname"));
			connDef.setPassword(getDBValue(id, "password"));
			connDef.setSchemaName(getDBValue(id, "schema"));
			connDef.setServerPort(Integer.valueOf(getDBValue(id, "port")));
			connDef.setUserName(getDBValue(id, "username"));
		}

		return connDef;
	}
	
	public MemberDef getMember(String type) throws Exception
	{
		MemberDef member = null;
		Database database = null;

		log.info("Load member configuration for type ["+type+"]");

		IConnDef def = getConnectionId(type);
		if (def != null)
		{
			database = new Database(def);
		}
		
	
		member = new MemberDef(database);
		
		/*
		 * CSV data
		 */
		member.setCsvFile(getProperties().getProperty(type + ".csv.filename", null));
		member.setCsvSeparator(getProperties().getProperty(type + ".csv.separator", null));
		member.setCsvOffset(getProperties().containsKey(type + ".csv.offset")?Integer.valueOf(getProperties().getProperty(type + ".csv.offset")):IMemberDef.DEFAULT_CSV_OFFSET);
		member.useCSVHeader(getProperties().containsKey(type + ".csv.header")?Boolean.valueOf(getProperties().getProperty(type + ".csv.offset")):IMemberDef.DEFAULT_CSV_HEADER);

		/*
		 * SQL statement
		 */
		member.setImportStatement(getProperties().getProperty(type + ".sql.statement.import", null));
		member.setCleanStatement(getProperties().getProperty(type + ".sql.statement.clean", null));
		member.setExportStatement(getProperties().getProperty(type + ".sql.statement.export", null));

		/*
		 * SQL configuration
		 */
		member.setFetchsize(getProperties().containsKey(type + ".sql.fetchsize")?Integer.valueOf(getProperties().getProperty(type + ".sql.fetchsize")):IMemberDef.DEFAULT_FETCHSIZE);
		member.setCommitBlock(getProperties().containsKey(type + ".commitblock")?Integer.valueOf(getProperties().getProperty(type + ".commitblock")):IMemberDef.DEFAULT_COMMIT_BLOCK);
		
		/*
		 * Set the table definition the configuration will call to to
		 * open connection in order to create the table element
		 */
		String tableCfg = getProperties().getProperty(type + ".sql.table", null);
		log.info("Table name ["+tableCfg+"]");
		if (tableCfg != null)
		{
			
			member.setTable(TableFactory.getInstance().create(database, tableCfg));
		}
		
		return member;
	}

}
