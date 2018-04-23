/**
 * 
 */
package org.crossroad.db.util.sql;

import org.crossroad.db.util.db.table.ITable;

/**
 * @author e.soden
 *
 */
public interface ISQLFactory {
	/**
	 * Generate insert statement
	 * @param table
	 * @return
	 */
	String createInsertStatement(ITable table);
	/**
	 * Generate the truncate statement
	 * @param table
	 * @return
	 */
	String createTruncateStatement(ITable table);
	/**
	 * 
	 * @param table
	 * @return
	 */
	String createSelectStatement(ITable table);
}
