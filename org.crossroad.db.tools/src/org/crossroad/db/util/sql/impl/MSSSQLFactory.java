/**
 * 
 */
package org.crossroad.db.util.sql.impl;

import org.crossroad.db.util.db.ITable;

/**
 * @author e.soden
 *
 */
public class MSSSQLFactory extends AbstractSQLStatementFactory {

	/**
	 * 
	 */
	public MSSSQLFactory() {
	}

	@Override
	protected String getTableName(ITable table) {
		StringBuffer buffer = new StringBuffer();

		if (table.getSchema() != null) {
			buffer.append("[").append(table.getSchema()).append("].");
		}
		buffer.append("[").append(table.getName()).append("]");
		return buffer.toString();
	}


}
