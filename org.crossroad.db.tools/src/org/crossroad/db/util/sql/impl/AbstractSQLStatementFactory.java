/**
 * 
 */
package org.crossroad.db.util.sql.impl;

import org.crossroad.db.util.sql.ISQLFactory;
import org.crossroad.db.util.table.IColumn;
import org.crossroad.db.util.table.ITable;
import org.crossroad.db.util.table.impl.AbstractSQLFactory;

/**
 * @author e.soden
 *
 */
public abstract class AbstractSQLStatementFactory extends AbstractSQLFactory implements ISQLFactory {

	/**
	 * 
	 */
	public AbstractSQLStatementFactory() {

	}


	protected String buildPrepareColList(ITable table) {
		StringBuffer buffer = new StringBuffer();
		boolean first = true;
		for (int i=0;i<table.getColumns().size();i++) {
			if (!first) {
				buffer.append(",");
			} else {
				first = false;
			}

			buffer.append("?");
		}

		return buffer.toString();
	}

	protected String buildColumnList(ITable table) {
		StringBuffer buffer = new StringBuffer();
		boolean first = true;
		for (IColumn column : table.getColumns()) {
			if (!first) {
				buffer.append(",");
			} else {
				first = false;
			}

			buffer.append(column.getName());
		}

		return buffer.toString();
	}
	

	/* (non-Javadoc)
	 * @see org.crossroad.db.util.sql.ISQLFactory#createInsertStatement(org.crossroad.db.util.table.ITable)
	 */
	@Override
	public String createInsertStatement(ITable table) {
		StringBuffer buffer = new StringBuffer();

		buffer.append("INSERT INTO ").append(getTableName(table)).append(" (").append(buildColumnList(table)).append(")")
				.append(" VALUES(").append(buildPrepareColList(table)).append(")");

		return buffer.toString();
	}
	/*
	 * (non-Javadoc)
	 * @see org.crossroad.db.util.sql.ISQLFactory#createSelectStatement(org.crossroad.db.util.table.ITable)
	 */
	@Override
	public String createSelectStatement(ITable table) {
		StringBuffer buffer = new StringBuffer();

		buffer.append("SELECT ").append(buildColumnList(table)).append(" FROM ").append(getTableName(table));

		return buffer.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see org.crossroad.db.util.sql.ISQLFactory#createTruncateStatement(org.crossroad.db.util.table.ITable)
	 */
	@Override
	public String createTruncateStatement(ITable table) {
		StringBuffer buffer = new StringBuffer();

		buffer.append("TRUNCATE TABLE ").append(getTableName(table));

		return buffer.toString();
	}
	
	protected abstract String getTableName(ITable table);
	
}
