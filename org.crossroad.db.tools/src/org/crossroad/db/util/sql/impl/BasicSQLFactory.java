/**
 * 
 */
package org.crossroad.db.util.sql.impl;

import org.crossroad.db.util.db.table.ITable;

/**
 * @author e.soden
 *
 */
public class BasicSQLFactory extends AbstractSQLStatementFactory {

	/**
	 * 
	 */
	public BasicSQLFactory() {
	}

	@Override
	protected String getTableName(ITable table) {
		StringBuffer buffer = new StringBuffer();

		if (table.getSchema() != null) {
			buffer.append(table.getSchema());
		}
		buffer.append(table.getName());
		return buffer.toString(); 
	}

}
