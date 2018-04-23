/**
 * 
 */
package org.crossroad.db.util.db.impl;

import org.crossroad.db.util.cfg.IConnDef;
import org.crossroad.db.util.db.IDatabase;
import org.crossroad.db.util.sql.ISQLFactory;
import org.crossroad.db.util.sql.impl.SQLStatementFactory;
import org.crossroad.util.log.AbstractLogger;

/**
 * @author e.soden
 *
 */
public abstract class AbstractDatabase extends AbstractLogger implements IDatabase {

	
	private IConnDef connDef = null;
	private ISQLFactory sqlFactory = null;

	/**
	 * 
	 */
	public AbstractDatabase(IConnDef connDef) {
		this.connDef = connDef;

		sqlFactory = SQLStatementFactory.getInstance().create(connDef.getDriverId());
	}
	
	public boolean openConnection() {
		return doOpenConnection(connDef);
	}
	
	protected abstract boolean doOpenConnection(IConnDef connDef);


	@Override
	public ISQLFactory getSQLFactory() {
		return sqlFactory;
	}
	
	@Override
	public IConnDef getConfiguration() {
		return this.connDef;
	}
}
