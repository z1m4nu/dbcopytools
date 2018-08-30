/**
 * 
 */
package org.crossroad.jdbc.dump.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.crossroad.jdbc.dump.error.PopertiesLoaderException;

/**
 * @author e.soden
 *
 */
public class ReportManager extends AbstractJDBCLogger {
	private final static ReportManager  instance = new ReportManager();
	private Properties properties = new Properties();
	private String homeDirectory = null;

	/**
	 * 
	 */
	private ReportManager() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @return
	 */
	public static ReportManager getInstance() {
		return instance;
	}

	/**
	 * 
	 * @param path
	 * @throws PopertiesLoaderException
	 */
	public void load(String path, String confiFile) throws PopertiesLoaderException {
		this.homeDirectory = path;
		try {
			log.info("Loading configuration....");
			if (!properties.isEmpty()) {
				properties.clear();
			}

			/*
			 * 
			 */
			importIntoProperties(getConfigurationDir() + File.separator + confiFile);
			/*
			 * Driver configuration
			 */
			importIntoProperties(getConfigurationDir() + File.separator + "driver.properties");
		} catch (Exception e) {
			log.error(e);
			throw new PopertiesLoaderException(e);
		}
	}

	private void importIntoProperties(String file) throws PopertiesLoaderException {
		FileInputStream stream = null;
		try {
			log.info("Configuration located in " + file);
			stream = new FileInputStream(file);
			properties.load(stream);
		} catch (Exception e) {
			log.error(e);
			throw new PopertiesLoaderException(e);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {

				}
				stream = null;
			}
		}
	}

	public String get(String entry) {
		return getValue(entry);
	}

	public String getReportConnection(String id) {
		return getValue(id + ".report.connection");
	}

	public String getReportQuery(String id) {
		return getValue(id + ".report.query.text");
	}

	public String getReportFile(String id) {
		return getValue(id + ".report.file");
	}

	public String getReportFileLocation(String id) {
		return getOutDir() + File.separator + getReportFile(id);
	}
	
	public String getDriverJar(String id) {
		return getLibDir() + File.separator + getValue(id + ".driver.jar");
	}

	public String getDriverClass(String id) {
		return getValue(id + ".driver.class");
	}

	public String getDriverURL(String id) {
		return getValue(id + ".driver.url");
	}

	public String getDriverData(String id) {
		return getValue(id + ".driver.url.data");
	}

	public String getConnectionDriver(String id) {
		return getValue(id + ".driver");
	}

	public String getConnectionHostname(String id) {
		return getValue(id + ".connect.hostname");
	}

	public String getConnectionPort(String id) {
		return getValue(id + ".connect.port");
	}

	public String getConnectionUsername(String id) {
		return getValue(id + ".connect.username");
	}

	public String getConnectionPassword(String id) {
		return getValue(id + ".connect.password");
	}

	public String getConnectionDatabase(String id) {
		return getValue(id + ".connect.database");
	}

	/**
	 * 
	 * @return
	 */
	public String getLibDir() {
		return homeDirectory + File.separator + "lib";
	}

	public String getOutDir() {
		return homeDirectory + File.separator + "out";
	}

	public String getSQLDir() {
		return homeDirectory + File.separator + "sql";
	}

	public String getConfigurationDir() {
		return this.homeDirectory + File.separator + "conf";
	}

	public String getReportSchema(String id) {
		return getValue(id + ".report.query.schema");
	}

	public String getReportQueryFile(String id) {
		return getValue(id + ".report.query.file");
	}

	private String getValue(String key) {
		return (this.properties.containsKey(key)) ? this.properties.getProperty(key).trim() : null;
	}

}
