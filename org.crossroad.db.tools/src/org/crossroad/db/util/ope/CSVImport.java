/**
 * 
 */
package org.crossroad.db.util.ope;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.PreparedStatement;

import org.crossroad.db.util.cfg.IMemberExport;
import org.crossroad.db.util.cfg.IMemberImport;
import org.crossroad.db.util.db.impl.AbstractSQLDatabase;
import org.crossroad.util.stat.RuntimeStat;
import org.crossroad.util.stat.RuntimeStatManager;

/**
 * @author e.soden
 *
 */
public class CSVImport extends AbstractOperation {

	/**
	 * @param source
	 * @param destination
	 */
	public CSVImport( IMemberExport source, IMemberImport destination) {
		super(source, destination);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.db.util.ope.AbstractOperation#doOperate()
	 */
	@Override
	protected long doOperate() throws Exception {
		PreparedStatement dstPstmt = null;
		BufferedReader reader = null;
		long totalCount = 0L;
		try {
			if (destination.getDatabase().openConnection()) {
				dstPstmt = ((AbstractSQLDatabase)destination.getDatabase()).getConnection().prepareStatement(destination.getSQLImportStatement());

				reader = new BufferedReader(new FileReader(source.getCSVFile()));
				String line = null;

				RuntimeStat stat = new RuntimeStat();
				stat.markStart();
				while ((line = reader.readLine()) != null) {
					if (totalCount > source.getCSVOffset()) {
						totalCount++;

						String[] fields = line.split(source.getCSVSeparator());
						for (int i = 0; i < fields.length; i++) {
							dstPstmt.setString(i + 1, fields[i]);
						}
						dstPstmt.addBatch();

						if ((totalCount % destination.getCommitBlock()) == 0) {
							System.out.println("Execute batch");
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

			if (dstPstmt != null) {
				dstPstmt.close();
			}

			if (reader != null) {
				reader.close();
			}

			destination.getDatabase().closeConnection();
		}

		return totalCount;
	}

}
