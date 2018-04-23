/**
 * 
 */
package org.crossroad.db.util.ope;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.crossroad.db.util.cfg.IMemberExport;
import org.crossroad.db.util.cfg.IMemberImport;
import org.crossroad.db.util.db.impl.AbstractSQLDatabase;
import org.crossroad.util.stat.RuntimeStat;
import org.crossroad.util.stat.RuntimeStatManager;

/**
 * @author e.soden
 *
 */
public class SQLImport extends AbstractOperation {

	/**
	 * @param source
	 * @param destination
	 */
	public SQLImport( IMemberExport source,   IMemberImport destination) {
		super(source, destination);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.db.util.ope.AbstractOperation#doOperate()
	 */
	@Override
	protected long doOperate() throws Exception {
		PreparedStatement srcPstmt = null;
		PreparedStatement dstPstmt = null;
		ResultSet rs = null;
		ResultSetMetaData meta = null;
		long totalCount = 0L;
		try {
			if (source.getDatabase().openConnection() && destination.getDatabase().openConnection()) {
				String sqlSource = source.getSQLExportStatement();
				String sqlTarget = destination.getSQLImportStatement();
				log.info("SQL Source [" + sqlSource + "]");
				log.info("SQL Target [" + sqlTarget + "]");

				RuntimeStat stat = new RuntimeStat();
				stat.markStart();
				srcPstmt = ((AbstractSQLDatabase)source.getDatabase()).getConnection().prepareStatement(sqlSource);
				rs = srcPstmt.executeQuery();
				meta = rs.getMetaData();
				stat.markEnd();
				RuntimeStatManager.getInstance().addSubStep("SQL_REMOTE", stat);

				dstPstmt = ((AbstractSQLDatabase)destination.getDatabase()).getConnection().prepareStatement(sqlTarget);

				stat = new RuntimeStat();
				stat.markStart();

				while (rs.next()) {
					totalCount++;
					for (int i = 0; i < meta.getColumnCount(); i++) {
						dstPstmt.setString(i + 1, rs.getString(i + 1));
					}
					dstPstmt.addBatch();

					if ((totalCount % destination.getCommitBlock()) == 0) {						
						int ret[] = dstPstmt.executeBatch();
						destination.getDatabase().commit();
						
						fireCommitListener(ret.length);
						
						dstPstmt.clearBatch();

						stat.markEnd();
						RuntimeStatManager.getInstance().addSubStep("DB_IMPORT", stat);
						stat = new RuntimeStat();
						stat.markStart();
					}
				}

				if ((totalCount % destination.getCommitBlock()) != 0) {
					int ret[] = dstPstmt.executeBatch();
					destination.getDatabase().commit();
					fireCommitListener(ret.length);

					dstPstmt.clearBatch();
					stat.markEnd();
					RuntimeStatManager.getInstance().addSubStep("DB_IMPORT", stat);
				}
			}
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (srcPstmt != null) {
				srcPstmt.close();
			}

			if (dstPstmt != null) {
				dstPstmt.close();
			}
			
			source.getDatabase().closeConnection();
			destination.getDatabase().closeConnection();
		}

		return totalCount;
	}

}
