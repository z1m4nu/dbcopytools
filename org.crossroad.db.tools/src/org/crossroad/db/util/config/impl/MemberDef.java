/**
 * 
 */
package org.crossroad.db.util.config.impl;

import java.util.HashMap;
import java.util.Map;

import org.crossroad.db.util.config.IMemberDef;
import org.crossroad.db.util.config.IMemberExport;
import org.crossroad.db.util.config.IMemberImport;
import org.crossroad.db.util.db.impl.AbstractDatabase;
import org.crossroad.db.util.table.ITable;

/**
 * @author e.soden
 *
 */
public class MemberDef implements IMemberImport, IMemberExport {
	private String csvSeparator = null;
	private AbstractDatabase database = null;
	private int fetchsize = IMemberDef.DEFAULT_FETCHSIZE;
	private int commitBlock = IMemberDef.DEFAULT_COMMIT_BLOCK;
	private int csvOffset = IMemberDef.DEFAULT_CSV_OFFSET;
	private boolean csvHeader = IMemberDef.DEFAULT_CSV_HEADER;

	private ITable table = null;

	public static final String IMPORT_STATEMENT = "import";
	public static final String EXPORT_STATEMENT = "export";
	public static final String CLEAN_STATEMENT = "clean";

	private Map<String, String> map = new HashMap<String, String>();

	public MemberDef(AbstractDatabase database) {
		this.database = database;
	}


	@Override
	public String getCSVFile() {
		return this.database.getDatabaseName();
	}

	@Override
	public String getCSVSeparator() {
		return csvSeparator;
	}

	public void setCsvSeparator(String csvSeparator) {
		this.csvSeparator = csvSeparator;
	}

	public void setImportStatement(String sqlStatement) {
		map.put(IMPORT_STATEMENT, sqlStatement);
	}

	public void setExportStatement(String sqlStatement) {
		map.put(EXPORT_STATEMENT, sqlStatement);
	}

	public void setCleanStatement(String sqlStatement) {
		map.put(CLEAN_STATEMENT, sqlStatement);
	}

	@Override
	public AbstractDatabase getDatabase() {
		return database;
	}


	@Override
	public int getCommitBlock() {
		return this.commitBlock;
	}

	@Override
	public int getFetchsize() {
		return this.fetchsize;
	}

	@Override
	public int getCSVOffset() {
		return this.csvOffset;
	}

	public void setCommitBlock(int commitBlock) {
		this.commitBlock = commitBlock;
	}

	public void setCsvOffset(int csvOffset) {
		this.csvOffset = csvOffset;
	}

	public void setFetchsize(int fetchsize) {
		this.fetchsize = fetchsize;
	}

	public void useCSVHeader(boolean use) {
		this.csvHeader = use;
	}

	@Override
	public boolean hasCSVHeader() {
		return this.csvHeader;
	}

	@Override
	public String getSQLCleanStatement() {
		String statement = null;

		if (map.containsKey(CLEAN_STATEMENT)) {
			statement = map.get(CLEAN_STATEMENT);
		} else {
			statement = database.createTruncateStatement(this.table);
		}
		return statement;
	}

	@Override
	public String getSQLExportStatement() {
		String statement = null;

		if (map.containsKey(EXPORT_STATEMENT)) {
			statement = map.get(EXPORT_STATEMENT);
		} else {
			statement = database.createSelectStatement(this.table);
		}
		return statement;
	}

	@Override
	public String getSQLImportStatement() {
		String statement = null;

		if (map.containsKey(IMPORT_STATEMENT)) {
			statement = map.get(IMPORT_STATEMENT);
		} else {
			statement = database.createInsertStatement(this.table);
		}
		return statement;
	}

	@Override
	public ITable getTable() {
		return this.table;
	}

	public void setTable(ITable table) {
		this.table = table;
	}

}
