/**
 * 
 */
package org.crossroad.db.util.db.impl;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.util.Properties;

import org.crossroad.db.util.connection.IConnDef;
import org.crossroad.db.util.connection.driver.DriversDef;
import org.crossroad.db.util.connection.driver.IDrvDef;

/**
 * @author e.soden
 *
 */
public class Database extends AbstractDatabase {
	private Connection connection = null;

	/**
	 * 
	 */
	public Database(IConnDef definition) {
		super(definition);
	}

	@Override
	protected void doOpenConnection() throws Exception {
		IDrvDef drvDef = DriversDef.getInstance().get(definition.getDriverId());
		String jdbcUrl = DriversDef.getInstance().formatJDBCUrl(definition);
		String jarFile = drvDef.getDriverJar();
		String schema = definition.getSchemaName();
		String classname = drvDef.getClassName();

		if (connection == null) {
			log.info("Connection ID [" + definition.getId() + "]");
			log.info("drvDef ID [" + drvDef.getID() + "]");
			log.info("JDBC Jar [" + jarFile + "]");
			log.info("JDBC Class [" + classname + "]");
			log.info("JDBC url [" + jdbcUrl + "]");
			log.info("Set schema to [" + schema + "]");

			File f = new File(jarFile);

			URL[] urls = { f.toURI().toURL() };
			URLClassLoader urlCl = new URLClassLoader(urls, System.class.getClassLoader());
			Class driverClass = urlCl.loadClass(classname);

			// the added code:
			Driver driver = (Driver) driverClass.newInstance();

			log.info("Connect to the remote database");
			Properties props = new Properties();
			props.setProperty("user", definition.getUserName());
			props.setProperty("password", definition.getPassword());
			connection = driver.connect(jdbcUrl, props);
			connection.setAutoCommit(false);

			log.info("Connected [" + connection.getMetaData().getDatabaseProductName() + "]");

		}
	}

	@Override
	protected void doCloseConnection() throws Exception {
		if (this.connection != null) {
			this.connection.close();
		}

	}

	@Override
	public void commit() throws Exception {
		if (this.connection != null) {
			this.connection.commit();
		}
	}

	public Connection getConnection() {
		return this.connection;
	}
}
