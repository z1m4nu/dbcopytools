/**
 * 
 */
package org.crossroad.jdbc.dump.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Properties;

import org.crossroad.jdbc.dump.error.ExecutionException;
import org.crossroad.jdbc.dump.error.QueryLoadException;

/**
 * @author e.soden
 *
 */
public class JDBCExecutor extends AbstractJDBCLogger {
	private CSVContainer csvContainer = new CSVContainer();

	/**
	 * 
	 */
	public JDBCExecutor() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param cfg
	 * @throws ExecutionException
	 */
	public void execute(String reportID, String params, String outfile) throws ExecutionException {

		String connectionList = ReportManager.getInstance().getReportConnection(reportID);
		Formatter fmt = null;
		log.info("Report ID [" + reportID + "]");

		try {
			String connectionIDS[] = connectionList.split(",");

			for (String connectionID : connectionIDS) {
				String driverId = ReportManager.getInstance().getConnectionDriver(connectionID);

				log.info("Connection ID [" + connectionID + "]");
				log.info("Driver ID [" + driverId + "]");

				String jdbcUrl = null;
				String jarFile = null;
				fmt = new Formatter();

				List<Object> data = new ArrayList<Object>();
				for (String elem : ReportManager.getInstance().getDriverData(driverId).split(";")) {
					data.add(ReportManager.getInstance().get(connectionID + ".connect." + elem));

				}

				fmt = fmt.format(ReportManager.getInstance().getDriverURL(driverId),
						data.toArray(new Object[data.size()]));
				jdbcUrl = fmt.toString();

				jarFile = ReportManager.getInstance().getDriverJar(driverId);

				String schema = ReportManager.getInstance().getReportSchema(reportID);

				String output = (outfile != null) ? outfile : ReportManager.getInstance().getReportFile(reportID);
				if (output.contains("1%s")) {
					fmt = new Formatter();
					fmt = fmt.format(output, connectionID);
					output = fmt.toString();

				}
				
				csvContainer.open(output);
				
				executeJDBC(jarFile, ReportManager.getInstance().getDriverClass(driverId), jdbcUrl, schema,
							ReportManager.getInstance().getConnectionUsername(connectionID),
							ReportManager.getInstance().getConnectionPassword(connectionID), loadQuery(reportID),
							params);

				
			}

		} catch (Exception e) {
			log.error(e);
			throw new ExecutionException(e);
		} finally {
			csvContainer.close();
			fmt.close();
		}

	}

	/**
	 * 
	 * @param driver
	 * @param url
	 * @param query
	 * @return
	 * @throws MalformedURLException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws FileNotFoundException
	 */
	private void executeJDBC(String jarFile, String classname, String url, String schema, String username,
			String password, String query, String parameters) throws Exception {
		ResultSet resultSet = null;
		ResultSetMetaData data = null;
		StringBuffer buffer = new StringBuffer();

		log.info("JDBC Jar [" + jarFile + "]");
		log.info("JDBC Class [" + classname + "]");
		log.info("JDBC url [" + url + "]");
		log.info("JDBC Query [" + query + "]");
		log.info("Set schema to [" + schema + "]");
		/*
		 * Loading the driver
		 */
		String jarURLStr = "jar:file://" + jarFile.replace(File.separatorChar, '/') + "!/";
		log.info("JDBC Jar url [" + jarURLStr + "]");

		URL[] urls = { new URL(jarURLStr) };
		URLClassLoader jarClassLoader = new URLClassLoader(urls);
		Class driverClass = jarClassLoader.loadClass(classname);
		// the added code:
		Driver driver = (Driver) driverClass.newInstance();

		Connection connection = null;
		PreparedStatement pStmt = null;
		try {
			log.info("Connect to the remote database");
			Properties props = new Properties();
			props.setProperty("user", username);
			props.setProperty("password", password);
			connection = driver.connect(url, props);
			if (schema != null) {
				connection.setSchema(schema);
			}

			log.info("Connected [" + connection.getMetaData().getDatabaseProductName() + "]");

			pStmt = connection.prepareStatement(query);
			/*
			 * Set parameters if any
			 */
			if (parameters != null) {
				String[] params = parameters.split(":");
				if (params != null) {
					int index = 0;
					log.info("Adding parameters to the preparestatement");
					for (String p : params) {
						pStmt.setString(++index, p);
					}
				}

			}

			/*
			 * Execute the statement
			 */
			resultSet = pStmt.executeQuery();

			data = resultSet.getMetaData();

			for (int i = 0; i < data.getColumnCount(); i++) {
				if (buffer.length() > 0) {
					buffer.append(';');
				}
				buffer.append(data.getColumnName(i + 1));
			}
			csvContainer.writeData(buffer.toString());

			while (resultSet.next()) {
				buffer = new StringBuffer();
				for (int i = 0; i < data.getColumnCount(); i++) {
					if (buffer.length() > 0) {
						buffer.append(';');
					}
					String cValue = resultSet.getString(i + 1);
					if (cValue == null) {
						cValue = "";
					}
					buffer.append(cValue);
				}
				csvContainer.writeData(buffer.toString());
			}

		} finally {
			if (pStmt != null) {
				log.info("Closing the prepare statement");
				try {
					pStmt.close();
				} catch (Exception e) {

				}
				pStmt = null;
			}
			
			if (resultSet != null)
			{
				resultSet.close();
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) {
					log.info("Closing the connection");
				}
			}
		}

		
	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws QueryLoadException
	 */
	private String loadQuery(String id) throws QueryLoadException {
		String query = null;
		String queryFile = ReportManager.getInstance().getReportQueryFile(id);

		if (queryFile != null) {
			log.info("Load query from file [" + queryFile + "]");

			try {
				query = new String(Files.readAllBytes(Paths.get(ReportManager.getInstance().getSQLDir(), queryFile)));
			} catch (IOException e) {
				throw new QueryLoadException(e);
			}

		} else {
			throw new QueryLoadException("Query file is define for id [" + id + "]");
		}

		return query;
	}

}
