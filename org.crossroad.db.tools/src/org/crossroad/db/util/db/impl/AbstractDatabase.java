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

import org.crossroad.db.util.cfg.IConnDef;
import org.crossroad.db.util.cfg.IDrvDef;
import org.crossroad.db.util.cfg.impl.DriversDef;
import org.crossroad.db.util.db.IDatabase;
import org.crossroad.db.util.sql.ISQLFactory;
import org.crossroad.db.util.sql.impl.SQLStatementFactory;
import org.crossroad.util.log.AbstractLogger;

/**
 * @author e.soden
 *
 */
public abstract class AbstractDatabase extends AbstractLogger implements IDatabase {

	private Connection connection = null;
	private IConnDef connDef = null;
	private ISQLFactory sqlFactory = null;

	/**
	 * 
	 */
	public AbstractDatabase(IConnDef connDef) {
		this.connDef = connDef;

		sqlFactory = SQLStatementFactory.getInstance().create(connDef.getDriverId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.db.util.db.impl.IDatabase#openConnection()
	 */
	@Override
	public boolean openConnection() {
		boolean bReturn = false;
		IDrvDef drvDef = DriversDef.getInstance().get(connDef.getDriverId());
		String jdbcUrl = formatJDBCUrl(drvDef);
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

	/**
	 * 
	 * @param drvDef
	 * @return
	 */
	private String formatJDBCUrl(IDrvDef drvDef) {

		String jdbcUrl = null;
		if (drvDef != null) {

			jdbcUrl = drvDef.getJdbcUrl();
			jdbcUrl = jdbcUrl.replaceAll(IDrvDef.DBNAME, connDef.getDatabaseName());
			jdbcUrl = jdbcUrl.replaceAll(IDrvDef.HOST, connDef.getServerName());
			jdbcUrl = jdbcUrl.replaceAll(IDrvDef.PORT, Integer.valueOf(connDef.getServerPort()).toString());

		}

		return jdbcUrl;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.db.util.db.impl.IDatabase#getConnection()
	 */
	@Override
	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public ISQLFactory getSQLFactory() {
		return sqlFactory;
	}
}
