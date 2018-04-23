/**
 * 
 */
package org.crossroad.db.util.db.table.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import org.crossroad.db.util.db.table.ITable;

/**
 * @author e.soden
 *
 */
public final class PrimaryKeyFactory extends AbstractSQLFactory {
	private static final PrimaryKeyFactory instance = new PrimaryKeyFactory();

	/**
	 * 
	 */
	private PrimaryKeyFactory() {
		// TODO Auto-generated constructor stub
	}

	public static PrimaryKeyFactory getInstance() {
		return instance;
	}

	/**
	 * Build primary key for the specified {@link ITable}
	 * 
	 * @param table
	 * @throws Exception
	 */
	public void create(Connection connection, ITable table) throws Exception {
		DatabaseMetaData meta = null;
		ResultSet rs = null;

		try {
			meta = connection.getMetaData();

			rs = meta.getPrimaryKeys(table.getCatalog(), table.getSchema(), table.getName());
			while (rs.next()) {
				log.debug(dumpSQL(rs));

				String pkName = rs.getString("PK_NAME");
				if (pkName != null) {
					PrimaryKey primaryKey = null;
					if (table.hasPrimaryKey(pkName)) {
						primaryKey = (PrimaryKey) table.getPrimaryKey(pkName);
					} else {
						primaryKey = new PrimaryKey();
						primaryKey.setName(pkName);
					}

					ColumnIndex cIndex = new ColumnIndex();
					cIndex.setName(rs.getString("COLUMN_NAME"));
					cIndex.setPosition(rs.getInt("KEY_SEQ"));

					primaryKey.addColumn(cIndex);

					table.addPrimaryKey(primaryKey);
				}
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

}
