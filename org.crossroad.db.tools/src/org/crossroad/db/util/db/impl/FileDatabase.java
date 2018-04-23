/**
 * 
 */
package org.crossroad.db.util.db.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import org.crossroad.db.util.db.connection.IConnDef;

/**
 * @author e.soden
 *
 */
public class FileDatabase extends AbstractDatabase {
	private boolean asReader = true;
	private BufferedReader reader = null;
	private BufferedWriter writer = null;
	/**
	 * 
	 */
	public FileDatabase(IConnDef definition, boolean asReader) {
		super(definition);
		this.asReader = asReader;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.db.util.db.table.impl.AbstractDatabase#doOpenConnection()
	 */
	@Override
	protected void doOpenConnection() throws Exception {
		if (asReader) {
			reader = new BufferedReader(new FileReader(definition.getDatabaseName()));
		} else {
			writer = new BufferedWriter(new FileWriter(definition.getDatabaseName()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.db.util.db.table.impl.AbstractDatabase#doCloseConnection()
	 */
	@Override
	protected void doCloseConnection() throws Exception {
		if (reader != null) {
			reader.close();
			reader = null;
		} else if (writer != null) {
			writer.flush();
			writer.close();
			writer = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.db.util.db.table.impl.AbstractDatabase#commit()
	 */
	@Override
	public void commit() throws Exception {
		if (writer != null) {
			writer.flush();
		}
	}

}
