/**
 * 
 */
package org.crossroad.db.util.db.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import org.crossroad.db.util.db.ITable;

/**
 * @author e.soden
 *
 */
public final class ColumnFactory extends AbstractSQLFactory{
private static final ColumnFactory instance = new ColumnFactory();
	/**
	 * 
	 */
	private ColumnFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static ColumnFactory getInstance()
	{
		return instance;
	}


	/**
	 * Build the column list for the specified {@link ITable}
	 * 
	 * @param table
	 * @throws Exception
	 */
	public void create(Connection connection,ITable table) throws Exception {
		DatabaseMetaData meta = null;
		ResultSet rs = null;
		try {
			meta = connection.getMetaData();

			rs = meta.getColumns(table.getCatalog(), table.getSchema(), table.getName(), null);

			while (rs.next()) {
				log.debug(dumpSQL(rs));
				Column column = new Column();
				column.setName(rs.getString("COLUMN_NAME"));
				column.setJDBCType(rs.getInt("DATA_TYPE"));
				column.setJDBCTypeName(rs.getString("TYPE_NAME"));
				column.setSize(rs.getInt("COLUMN_SIZE"));
				column.setNullable((rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable));
				column.setDescription(rs.getString("REMARKS"));
				column.setScale(rs.getInt("DECIMAL_DIGITS"));
				table.addColumn(column);
			}
		} finally {
			if (meta != null) {
				meta = null;
			}

			if (rs != null) {
				rs.close();
			}
		}
	}
}
