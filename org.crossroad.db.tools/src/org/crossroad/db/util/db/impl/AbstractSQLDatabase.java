/**
 * 
 */
package org.crossroad.db.util.db.impl;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.util.Properties;

import org.crossroad.db.util.cfg.IConnDef;
import org.crossroad.db.util.cfg.IDrvDef;
import org.crossroad.db.util.cfg.impl.DriversDef;

/**
 * @author e.soden
 *
 */
public abstract class AbstractSQLDatabase extends AbstractDatabase {
	private Connection connection = null;

	/**
	 * @param connDef
	 */
	public AbstractSQLDatabase(IConnDef connDef) {
		super(connDef);
	}

	@Override
	protected boolean doOpenConnection(IConnDef connDef) {
		boolean bReturn = false;
		IDrvDef drvDef = DriversDef.getInstance().get(connDef.getDriverId());
		String jdbcUrl = DriversDef.getInstance().formatJDBCUrl(connDef);
		String jarFile = drvDef.getDriverJar();
		String schema = connDef.getSchemaName();
		String classname = drvDef.getClassName();

		try {
			if (connection == null) {
				log.info("Connection ID [" + connDef.getId() + "]");
				log.info("drvDef ID [" + drvDef.getID() + "]");
				log.info("JDBC Jar [" + jarFile + "]");
				log.info("JDBC Class [" + classname + "]");
				log.info("JDBC url [" + jdbcUrl + "]");
				log.info("Set schema to [" + schema + "]");

				try {
					File f = new File(jarFile);

					URL[] urls = { f.toURI().toURL() };
					URLClassLoader urlCl = new URLClassLoader(urls, System.class.getClassLoader());
					Class driverClass = urlCl.loadClass(classname);

					// the added code:
					Driver driver = (Driver) driverClass.newInstance();

					log.info("Connect to the remote database");
					Properties props = new Properties();
					props.setProperty("user", connDef.getUserName());
					props.setProperty("password", connDef.getPassword());
					connection = driver.connect(jdbcUrl, props);
					connection.setAutoCommit(false);

					log.info("Connected [" + connection.getMetaData().getDatabaseProductName() + "]");

					bReturn = true;
				} catch (Exception e) {
					log.error("Unable to open connetion", e);
				}
			} else {
				bReturn = true;
			}
		} finally {

		}
		return bReturn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.db.util.db.impl.IDatabase#closeConnection()
	 */
	@Override
	public boolean closeConnection() {
		boolean bReturn = false;

		if (this.connection != null) {
			log.info("Closing SQL connection");
			try {
				this.connection.close();

			} catch (Exception e) {
				log.error("Unable to close connection", e);
			}
		} else {
			bReturn = true;
		}

		return bReturn;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public void commit() throws Exception {
		if (connection != null) {
			connection.commit();
		}
	}
}
