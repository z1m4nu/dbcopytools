package org.crossroad.util.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public final class SQLUtil {

	private SQLUtil() {
	}

	/**
	 * 
	 * @param connection
	 * @param table
	 * @return
	 * @throws Exception
	 */
	public static String buildTableSelect(Connection connection, String table) throws Exception {
		StringBuffer buffer = new StringBuffer();
		DatabaseMetaData meta = null;
		ResultSet rs = null;

		try {
			if (!connection.isClosed()) {
				meta = connection.getMetaData();
				rs = meta.getColumns(null, null, "users", null);
				while (rs.next()) {
					buffer.append(rs.getString("COLUMN_NAME"));
				}

				buffer.insert(0, "SELECT ");
				buffer.append(" FROM ");
				buffer.append(table);
			} else {
				throw new Exception("JDBC Connection closed.");
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}

		return buffer.toString();
	}

}
