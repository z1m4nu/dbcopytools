package org.crossroad.db.util.cfg;

import org.crossroad.db.util.db.ITable;
import org.crossroad.db.util.db.impl.AbstractDatabase;

public interface IMemberDef {
	public final static String ACTION_CSV = "CSV";
	public final static String ACTION_SQL = "SQL";
	public final static int DEFAULT_COMMIT_BLOCK = 10000;
	public final static int DEFAULT_FETCHSIZE = 10000;
	public final static int DEFAULT_CSV_OFFSET = 0;
	public final static boolean DEFAULT_CSV_HEADER = true;
	
	/*
	 * CSV
	 */
	String getCSVFile();
	String getCSVSeparator();
	int getCSVOffset();
	
	/*
	 * SQL
	 */
	AbstractDatabase getDatabase();
	int getCommitBlock();
	int getFetchsize();
	ITable getTable();
	
	
	boolean hasCSVHeader();
}
