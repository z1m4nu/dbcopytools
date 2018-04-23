/**
 * 
 */
package org.crossroad.db.util.operations;

import java.util.ArrayList;
import java.util.List;

import org.crossroad.db.util.config.IMemberExport;
import org.crossroad.db.util.config.IMemberImport;
import org.crossroad.util.log.AbstractLogger;
import org.crossroad.util.stat.RuntimeStat;
import org.crossroad.util.stat.RuntimeStatManager;

/**
 * @author e.soden
 *
 */
public abstract class AbstractOperation extends AbstractLogger {
	public static final String OPERATE_STAT_ID = "OPERATE";
	protected IMemberExport source = null;
	protected IMemberImport destination = null;
	protected List<ICommitListener> commitListeners = new ArrayList<ICommitListener>();
	protected List<IExportListener> exportListeners = new ArrayList<IExportListener>();

	/**
	 * 
	 */
	public AbstractOperation(IMemberExport source, IMemberImport destination) {
		this.destination = destination;
		this.source = source;
	}

	public long operate() throws Exception {
		long lReturn = 0L;
		RuntimeStat stat = new RuntimeStat();
		stat.markStart();

		try {
			lReturn = doOperate();
		} finally {
			stat.markEnd();
			RuntimeStatManager.getInstance().addSubStep(OPERATE_STAT_ID, stat);
			
			/*
			 * Closing connection
			 */
			destination.getDatabase().closeConnection();
			source.getDatabase().closeConnection();
		}

		return lReturn;
	}

	protected abstract long doOperate() throws Exception;

	public void addCommitBlockListener(ICommitListener listener) {
		this.commitListeners.add(listener);
	}

	public void removeCommitBlockListener(ICommitListener listener) {
		this.commitListeners.remove(listener);
	}

	public void addExportListener(IExportListener listener) {
		this.exportListeners.add(listener);
	}

	public void removeExportListener(IExportListener listener) {
		this.exportListeners.remove(listener);
	}

	/**
	 * 
	 * @param commitBlock
	 */
	protected void fireCommitListener(int commitBlock) {
		if (!commitListeners.isEmpty()) {
			for (ICommitListener listener : commitListeners) {
				listener.onCommit(commitBlock);
			}
		}
	}

	/**
	 * 
	 * @param filename
	 */
	protected void fireExportListener(String filename) {
		if (!exportListeners.isEmpty()) {
			for (IExportListener listener : exportListeners) {
				listener.onExport(filename);
			}
		}
	}

	@Override
	protected void finalize() throws Throwable {
		destination.getDatabase().closeConnection();
		source.getDatabase().closeConnection();
		super.finalize();
	}
}