/**
 * 
 */
package org.crossroad.db.util.db.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.crossroad.db.util.db.ITable;
import org.crossroad.util.log.AbstractLogger;

/**
 * @author e.soden
 *
 */
public abstract class AbstractSQLFactory extends AbstractLogger {

	/**
	 * 
	 */
	public AbstractSQLFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public String dumpSQL(ResultSet rs) throws Exception
	{
		ResultSetMetaData meta = rs.getMetaData();
		StringBuffer buffer = new StringBuffer();
		
		
		for (int i = 0; i < meta.getColumnCount(); i++)
			buffer.append("COLUMN [" + meta.getColumnName(i + 1) + "] value [" + rs.getString(i + 1) + "]").append(lineSeparator);
		
		return buffer.toString();
	}
	
	public String getTableName(ITable table, String dbID)
	{
		String tableName = null;
		
		if ("mssql".equalsIgnoreCase(dbID))
		{
			tableName = (table.getSchema() != null) ? "["+table.getSchema() + "].[" + table.getName() + "]": "["+table.getName()+"]";
		} else if ("hana".equalsIgnoreCase(dbID)){
			tableName = (table.getSchema() != null) ? "\""+table.getSchema() + "\".\"" + table.getName() + "\"": "\""+table.getName()+"\"";
		} else {
			tableName = (table.getSchema() != null) ? table.getSchema() + "." + table.getName() : table.getName();
		}
		
		return tableName;
	}
	

}
