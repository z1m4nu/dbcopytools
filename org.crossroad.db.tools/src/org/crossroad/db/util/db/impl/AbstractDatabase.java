/**
 * 
 */
package org.crossroad.db.util.db.impl;

import org.crossroad.db.util.db.connection.IConnDef;
import org.crossroad.db.util.db.table.ITable;
import org.crossroad.db.util.driver.DriversType;
import org.crossroad.db.util.sql.ISQLFactory;
import org.crossroad.db.util.sql.impl.SQLStatementFactory;
import org.crossroad.util.log.AbstractLogger;

/**
 * @author e.soden
 *
 */
public abstract class AbstractDatabase extends AbstractLogger {
	protected IConnDef definition = null;
	private ISQLFactory sqlFactory = null;

	/**
	 * 
	 */
	public AbstractDatabase(IConnDef definition) {
		this.definition = definition;
		sqlFactory = SQLStatementFactory.getInstance().create(definition.getDriverId());
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void openConnection() throws Exception {
		doOpenConnection();
	}

	protected abstract void doOpenConnection() throws Exception;

	public void closeConnection() throws Exception {
		doCloseConnection();
	}

	protected abstract void doCloseConnection() throws Exception;

	public abstract void commit() throws Exception;


	/**
	 * @return
	 * @see org.crossroad.db.util.db.connection.IConnDef#getDriverId()
	 */
	public DriversType getDriverId() {
		return definition.getDriverId();
	}

	/**
	 * @return
	 * @see org.crossroad.db.util.db.connection.IConnDef#getDatabaseName()
	 */
	public String getDatabaseName() {
		return definition.getDatabaseName();
	}

	/**
	 * @param table
	 * @return
	 * @see org.crossroad.db.util.sql.ISQLFactory#createInsertStatement(org.crossroad.db.util.db.table.ITable)
	 */
	public String createInsertStatement(ITable table) {
		return sqlFactory.createInsertStatement(table);
	}

	/**
	 * @param table
	 * @return
	 * @see org.crossroad.db.util.sql.ISQLFactory#createTruncateStatement(org.crossroad.db.util.db.table.ITable)
	 */
	public String createTruncateStatement(ITable table) {
		return sqlFactory.createTruncateStatement(table);
	}

	/**
	 * @param table
	 * @return
	 * @see org.crossroad.db.util.sql.ISQLFactory#createSelectStatement(org.crossroad.db.util.db.table.ITable)
	 */
	public String createSelectStatement(ITable table) {
		return sqlFactory.createSelectStatement(table);
	}


	
}
