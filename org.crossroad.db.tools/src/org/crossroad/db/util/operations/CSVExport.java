/**
 * 
 */
package org.crossroad.db.util.operations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.crossroad.db.util.config.IMemberExport;
import org.crossroad.db.util.config.IMemberImport;
import org.crossroad.db.util.db.impl.Database;
import org.crossroad.util.cfg.DirHelper;
import org.crossroad.util.stat.RuntimeStat;
import org.crossroad.util.stat.RuntimeStatManager;

/**
 * @author e.soden
 *
 */
public class CSVExport extends AbstractOperation {
	private int outCount = 0;
	private static final String SEPARATOR = System.getProperty("line.separator");
	private static final String CSV_EXT = ".csv";

	/**
	 * @param source
	 * @param destination
	 */
	public CSVExport(IMemberExport source, IMemberImport destination) {
		super(source, destination);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.db.util.operations.AbstractOperation#doOperate()
	 */
	@Override
	protected long doOperate() throws Exception {
		PreparedStatement srcPstmt = null;
		ResultSet rs = null;
		ResultSetMetaData meta = null;
		long totalCount = 0L;
		StringBuffer headers = new StringBuffer();
		StringBuffer lineBuffer = new StringBuffer();
		try {
			source.getDatabase().openConnection();

			RuntimeStat stat = new RuntimeStat();
			stat.markStart();

			String sqlSource = source.getSQLExportStatement();
			log.info("SQL Source [" + sqlSource + "]");

			srcPstmt = ((Database) source.getDatabase()).getConnection().prepareStatement(sqlSource);
			rs = srcPstmt.executeQuery();
			meta = rs.getMetaData();
			stat.markEnd();
			RuntimeStatManager.getInstance().addSubStep("SQL_REMOTE", stat);

			int columns = meta.getColumnCount();

			if (destination.hasCSVHeader()) {
				for (int i = 0; i < columns; i++) {
					headers.append(meta.getColumnName(i + 1));
					if (i < (columns - 1))
						headers.append(destination.getCSVSeparator());
				}
				headers.append(SEPARATOR);
			}

			lineBuffer = new StringBuffer();

			stat = new RuntimeStat();
			stat.markStart();
			while (rs.next()) {
				totalCount++;
				for (int i = 0; i < columns; i++) {
					lineBuffer.append(rs.getString(i + 1));
					if (i < (columns - 1))
						lineBuffer.append(destination.getCSVSeparator());

				}
				lineBuffer.append(SEPARATOR);

				if ((totalCount % destination.getCommitBlock()) == 0) {
					writeToFile(destination.getCSVFile(), headers.toString(), lineBuffer.toString());

					fireCommitListener(destination.getCommitBlock());

					stat.markEnd();
					RuntimeStatManager.getInstance().addSubStep("CSV_EXPORT", stat);
					stat = new RuntimeStat();
					stat.markStart();

					lineBuffer = new StringBuffer();
				}
			}

			if ((totalCount % destination.getCommitBlock()) != 0) {
				writeToFile(destination.getCSVFile(), headers.toString(), lineBuffer.toString());
				fireCommitListener(destination.getCommitBlock());
				stat.markEnd();
				RuntimeStatManager.getInstance().addSubStep("CSV_EXPORT", stat);
			}

		} finally {
			if (rs != null) {
				rs.close();
			}

			if (srcPstmt != null) {
				srcPstmt.close();
			}

			source.getDatabase().closeConnection();

		}

		return totalCount;
	}

	/**
	 * 
	 * @param fileName
	 * @param headers
	 * @param content
	 * @throws Exception
	 */
	private void writeToFile(String fileName, String headers, String content) throws Exception {
		BufferedWriter writer = null;
		NumberFormat formatter = new DecimalFormat("00");
		RuntimeStat stat = new RuntimeStat();
		stat.markStart();

		try {

			String fname = null;

			if (outCount > 0) {
				fname = fileName + "-" + formatter.format(outCount) + CSV_EXT;
			} else {
				fname = fileName + CSV_EXT;
			}
			if (!fname.contains(File.separator)) {
				fname = DirHelper.getInstance().getOutPath(fname);
			}
			log.info("Create file [" + fname + "]");

			writer = new BufferedWriter(new FileWriter(fname));
			writer.write(headers);
			writer.write(content);
			writer.flush();

			fireExportListener(fname);
			outCount++;
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}

			stat.markEnd();
			RuntimeStatManager.getInstance().addSubStep("WRTIE_FILE", stat);
		}
	}

}
