/**
 * 
 */
package org.crossroad.db.util.db.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;

import org.crossroad.db.util.cfg.IConnDef;

/**
 * @author e.soden
 *
 */
public abstract class AbstractFileDatabase extends AbstractDatabase {
    private BufferedReader reader = null;
    private BufferedWriter writer = null;
    private boolean asReader = true;
	/**
	 * @param connDef
	 */
	public AbstractFileDatabase(IConnDef connDef, boolean asReader) {
		super(connDef);
		this.asReader = asReader;
	}

	/* (non-Javadoc)
	 * @see org.crossroad.db.util.db.IDatabase#closeConnection()
	 */
	@Override
	public boolean closeConnection() {
		boolean bReturn = false;
		try {
			if (reader != null)
			{
				reader.close();
				reader = null;
			} else if (writer != null){
				writer.flush();
				writer.close();
				writer = null;
			}
			
			
			bReturn = true;
		} catch(Exception e)
		{
			log.error("Unable to open connection");
		}
		return bReturn;
	}

	/* (non-Javadoc)
	 * @see org.crossroad.db.util.db.IDatabase#getConnection()
	 */
	//@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.crossroad.db.util.db.impl.AbstractDatabase#doOpenConnection(org.crossroad.db.util.cfg.IConnDef)
	 */
	@Override
	protected boolean doOpenConnection(IConnDef connDef) {
		boolean bReturn = false;
		try {
			if (asReader)
			{
				reader = new BufferedReader(new FileReader(connDef.getDatabaseName()));
			} else {
				writer = new BufferedWriter(new FileWriter(connDef.getDatabaseName()));
			}
			bReturn = true;
		} catch(Exception e)
		{
			log.error("Unable to open connection");
		}
		return bReturn;
	}
	
	@Override
	public void commit() throws Exception {
		if (writer != null)
		{
			writer.flush();
		}
	}

}
