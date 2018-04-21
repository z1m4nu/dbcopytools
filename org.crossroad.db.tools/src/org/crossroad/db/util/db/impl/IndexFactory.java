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
public final class IndexFactory extends AbstractSQLFactory {
	private static final IndexFactory instance = new IndexFactory();

	/**
	 * 
	 */
	private IndexFactory() {
		// TODO Auto-generated constructor stub
	}

	public static IndexFactory getInstance() {
		return instance;
	}

	public void create(Connection connection, ITable table) throws Exception {
		DatabaseMetaData meta = null;
		ResultSet rs = null;
		try {
			meta = connection.getMetaData();

			rs = meta.getIndexInfo(table.getCatalog(), table.getSchema(), table.getName(), false, false);
			while (rs.next()) {

				log.debug(dumpSQL(rs));

				String indexName = rs.getString("INDEX_NAME");
				if (indexName != null) {
					Index index = null;
					if (table.hasIndex(indexName)) {
						index = (Index) table.getIndex(indexName);
					} else {
						index = new Index();
						index.setFilterCond(rs.getString("FILTER_CONDITION"));
						index.setName(indexName);
						index.setSort(rs.getString("ASC_OR_DESC"));
						index.setUnique(rs.getBoolean("NON_UNIQUE"));

					}

					ColumnIndex cIndex = new ColumnIndex();
					cIndex.setName(rs.getString("COLUMN_NAME"));
					cIndex.setPosition(rs.getInt("ORDINAL_POSITION"));

					index.addColumn(cIndex);

					table.addIndex(index);
				}

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
