/**
 * 
 */
package org.crossroad.db.util;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.crossroad.db.util.cfg.Configuration;
import org.crossroad.db.util.cfg.IMemberExport;
import org.crossroad.db.util.cfg.IMemberImport;
import org.crossroad.db.util.db.ITable;
import org.crossroad.db.util.db.impl.TableFactory;
import org.crossroad.db.util.sql.impl.SQLStatementFactory;
import org.crossroad.util.log.AbstractLogger;
import org.crossroad.util.stat.RuntimeStatManager;

/**
 * @author e.soden
 *
 */
public class DBSample extends AbstractLogger {

	/**
	 * 
	 */
	public DBSample() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		DBSample dbUtil = new DBSample();
		dbUtil.parseCommand(args);
	}

	protected void parseCommand(String[] args) throws Exception {
		try {
			RuntimeStatManager.getInstance().startOverall();

			CommandLineParser parser = new BasicParser();

			// create the command line Options
			Options options = new Options();

			options.addOption("h", "help", false, "Display current help");

			options.addOption("H", "home-directory", true, "Home directory");

			// Instantiate the helpformatter
			HelpFormatter formatter = new HelpFormatter();

			CommandLine cmdLine = parser.parse(options, args);

			if (cmdLine.hasOption('h')) {
				formatter.printHelp("DBCopy", options);
				System.exit(0);
			} else if (cmdLine.hasOption('H')) {

				Configuration configuration = new Configuration(cmdLine.getOptionValue('H'));
				configuration.loadConfiguration();

				IMemberExport source = configuration.getMember("source");
				IMemberImport target = configuration.getMember("target");


				log.info(TableFactory.dumpToString(source.getTable()));
				log.info(TableFactory.dumpToString(target.getTable()));

				
				log.info("Source insert ["+source.getSQLExportStatement()+"]");
				log.info("Source select ["+source.getDatabase().getSqlFactory().createSelectStatement(source.getTable())+"]");
				log.info("Source trunc  ["+source.getDatabase().getSqlFactory().createTruncateStatement(source.getTable())+"]");

				log.info("Target insert ["+target.getSQLImportStatement()+"]");
				log.info("Target select ["+target.getDatabase().getSqlFactory().createSelectStatement(target.getTable())+"]");
				log.info("Target trunc  ["+target.getSQLCleanStatement()+"]");

			} else {
				throw new RuntimeException("Home directory flags is mandatory");
			}

		} finally {
			RuntimeStatManager.getInstance().stopOverall();

			log.info(RuntimeStatManager.getInstance().displayStat());
		}

	}

}
