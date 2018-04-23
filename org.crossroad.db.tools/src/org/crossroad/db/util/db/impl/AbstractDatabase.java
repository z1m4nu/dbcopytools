/**
 * 
 */
package org.crossroad.db.util.db.impl;

import org.crossroad.db.util.cfg.IConnDef;
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

	public ISQLFactory getSqlFactory() {
		return sqlFactory;
	}

	public IConnDef getDefinition() {
		return this.definition;
	}
}
