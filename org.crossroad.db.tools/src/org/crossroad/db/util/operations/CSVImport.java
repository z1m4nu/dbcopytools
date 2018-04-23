/**
 * 
 */
package org.crossroad.db.util.operations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.PreparedStatement;

import org.crossroad.db.util.config.IMemberExport;
import org.crossroad.db.util.config.IMemberImport;
import org.crossroad.db.util.db.impl.Database;
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
	public CSVImport(IMemberExport source, IMemberImport destination) {
		super(source, destination);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.db.util.operations.AbstractOperation#doOperate()
	 */
	@Override
	protected long doOperate() throws Exception {
		PreparedStatement dstPstmt = null;
		BufferedReader reader = null;
		long totalCount = 0L;
		try {
			destination.getDatabase().openConnection();
			dstPstmt = ((Database) destination.getDatabase()).getConnection()
					.prepareStatement(destination.getSQLImportStatement());

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
