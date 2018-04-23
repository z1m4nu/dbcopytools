/**
 * 
 */
package org.crossroad.db.util.db.impl;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import org.crossroad.db.util.db.IColumn;
import org.crossroad.db.util.db.IColumnIndex;
import org.crossroad.db.util.db.IIndex;
import org.crossroad.db.util.db.IPrimaryKey;
import org.crossroad.db.util.db.ITable;

/**
 * @author e.soden
 *
 */
public final class TableFactory extends AbstractSQLFactory {
	private static final TableFactory instance = new TableFactory();

	/**
	 * 
	 */
	private TableFactory() {
		// TODO Auto-generated constructor stub
	}

	public static TableFactory getInstance() {
		return instance;
	}

	/**
	 * 
	 * @param db
	 * @param inTableName
	 * @return
	 * @throws Exception
	 */
	public Table create(Database db, String inTableName) throws Exception {
		DatabaseMetaData meta = null;
		ResultSet rs = null;
		Table table = new Table();

		

		try {
			db.openConnection();
			
			meta = db.getConnection().getMetaData();
			String schema = null;
			String tableName = null;
			String catalog = null;
			String[] s = inTableName.split("\\.");

			switch (s.length) {
			case 3:
				catalog = s[0];
				schema = s[1];
				tableName = s[2];
				break;
			case 2:
				catalog = null;
				schema = s[0];
				tableName = s[1];
				break;
			default:
				catalog = null;
				schema = null;
				tableName = s[0];
				break;
			}

			rs = meta.getTables(catalog, schema, tableName, new String[] { "TABLE" });

			while (rs.next()) {
				log.debug(dumpSQL(rs));
				table.setCatalog(rs.getString("TABLE_CAT"));
				table.setName(rs.getString("TABLE_NAME"));
				table.setSchema(rs.getString("TABLE_SCHEM"));

				/*
				 * Add primary keys
				 */
				PrimaryKeyFactory.getInstance().create(db.getConnection(), table);
				/*
				 * Add columns
				 */
				ColumnFactory.getInstance().create(db.getConnection(), table);
				/*
				 * Build index
				 */
				IndexFactory.getInstance().create(db.getConnection(), table);
			}

		} finally {
			if (meta != null) {
				meta = null;
			}

			if (rs != null) {
				rs.close();
			}
		}

		return table;
	}

	public static String dumpToString(ITable table) {
		StringBuffer buffer = new StringBuffer();
		String lineSeparator = System.getProperty("line.separator");
		buffer.append("General information").append(lineSeparator);
		buffer.append("Catalog [" + table.getCatalog() + "]").append(lineSeparator);
		buffer.append("Schema [" + table.getSchema() + "]").append(lineSeparator);
		buffer.append("Name [" + table.getName() + "]").append(lineSeparator);

		// Columns
		buffer.append("Columns:").append(lineSeparator);
		for (IColumn col : table.getColumns()) {
			buffer.append(
					"Name [" + col.getName() + "] Type[" + col.getJDBCTypeName() + "] Size[" + col.getSize() + "]")
					.append(lineSeparator);
		}

		buffer.append(lineSeparator).append("Primary Keys:").append(lineSeparator);

		// Primary key
		for (IPrimaryKey key : table.getPrimaryKeys()) {
			buffer.append("Name [" + key.getName() + "] Columns[");

			boolean first = true;
			for (IColumnIndex cIndex : key.getColumns()) {
				if (!first) {
					buffer.append(", ");
				} else {
					first = false;
				}
				buffer.append(cIndex.getName() + "@" + cIndex.ordinalPosition());
			}

			buffer.append("]").append(lineSeparator);
		}

		buffer.append(lineSeparator).append("Indexes:").append(lineSeparator);

		// Index
		for (IIndex key : table.getIndexes()) {
			buffer.append("Name [" + key.getName() + "] Columns[");

			boolean first = true;
			for (IColumnIndex cIndex : key.getColumns()) {
				if (!first) {
					buffer.append(", ");
				} else {
					first = false;
				}
				buffer.append(cIndex.getName() + "@" + cIndex.ordinalPosition());
			}

			buffer.append("] Sort [" + key.getColumnSort() + "] Unique[" + key.isUnique() + "]").append(lineSeparator);
		}

		return buffer.toString();
	}

}
