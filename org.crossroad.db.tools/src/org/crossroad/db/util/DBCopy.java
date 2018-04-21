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
import org.crossroad.db.util.ope.AbstractOperation;
import org.crossroad.db.util.ope.CSVExport;
import org.crossroad.db.util.ope.CSVImport;
import org.crossroad.db.util.ope.SQLImport;
import org.crossroad.util.log.AbstractLogger;
import org.crossroad.util.stat.RuntimeStatManager;

/**
 * @author e.soden
 *
 */
public class DBCopy extends AbstractLogger {

	/**
	 * 
	 */
	public DBCopy() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		DBCopy dbUtil = new DBCopy();
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

				AbstractOperation operation = null;

				if (source.isActionSQL() && target.isActionSQL()) {
					operation = new SQLImport(source, target);
				} else if (!source.isActionSQL() && target.isActionSQL()) {
					operation = new CSVImport(source, target);
				} else if (source.isActionSQL() && !target.isActionSQL()) {
					operation = new CSVExport(source, target);
				}
				
				if (operation != null)
				{
					RuntimeStatManager.getInstance().addCustomInfo("Commit block", String.valueOf(target.getCommitBlock()));
					RuntimeStatManager.getInstance().addCustomInfo("Fetch size", String.valueOf(source.getFetchsize()));

					
					long rows = operation.operate();
					RuntimeStatManager.getInstance().addCustomInfo("Rows retrieved", String.valueOf(rows));
				}

			} else {
				throw new RuntimeException("Home directory flags is mandatory");
			}

		} finally {
			RuntimeStatManager.getInstance().stopOverall();

			log.info(RuntimeStatManager.getInstance().displayStat());
		}

	}

}
