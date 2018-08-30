package org.crossroad.jdbc.dump;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.crossroad.jdbc.dump.error.CMDLineParserException;
import org.crossroad.jdbc.dump.error.ConfigurationException;
import org.crossroad.jdbc.dump.error.ExecutionException;
import org.crossroad.jdbc.dump.impl.AbstractJDBCLogger;
import org.crossroad.jdbc.dump.impl.JDBCExecutor;
import org.crossroad.jdbc.dump.impl.ReportManager;

public class JDBCDump extends AbstractJDBCLogger {

	private String report = null;
	private String params = null;
	private String outfile = null;
	private String confFile = "configuration.properties";

	public JDBCDump() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws CMDLineParserException, ConfigurationException, ExecutionException {

		JDBCDump dumper = new JDBCDump();
		/*
		 * Parse command line
		 */
		dumper.parseCMDLine(args);

		/*
		 * Execution
		 */
		dumper.execute();

	}

	protected void execute() throws ExecutionException {
		JDBCExecutor executor = new JDBCExecutor();
		executor.execute(report, params, outfile);
	}

	/**
	 * 
	 * @param args
	 * @throws CMDLineParserException
	 */
	protected void parseCMDLine(String[] args) throws CMDLineParserException {
		log.info("Parsing command line");
		try {
			CommandLineParser parser = new DefaultParser();

			Options options = new Options();

			options.addOption("d", "directory", true, "Specify Home directory.");
			options.addOption("c", "configuration file", true, "Specify the configuration.");
			options.addOption("p", "parameters", true, "Specify Home directory.");
			options.addOption("o", "output-file", true, "Complete path to the output file.");
			options.addOption("q", "query-id", true, "Query id defines within the configuration file.");
			options.addOption("h", "help", false, "Display current help");

			// Instantiate the helpformatter
			HelpFormatter formatter = new HelpFormatter();

			// Parse the command line
			CommandLine cmdLine = parser.parse(options, args);

			if (cmdLine.hasOption('h')) {
				formatter.printHelp("SQL Dump", options);
				System.exit(0);
			}
			if (cmdLine.hasOption('d')) {
				

				if (cmdLine.hasOption('q'))
					report = cmdLine.getOptionValue('q');

				if (cmdLine.hasOption('c'))
					confFile = cmdLine.getOptionValue('c');

				if (cmdLine.hasOption('o')) {
					outfile = cmdLine.getOptionValue('o');
				} 

				if (cmdLine.hasOption('p')) {
					params = cmdLine.getOptionValue('p');
				}

				
				/*
				 * Load configuration files
				 */
				ReportManager.getInstance().load(cmdLine.getOptionValue('d'), confFile);
				
			} else {
				throw new CMDLineParserException("Home directory should be set");
			}

		} catch (CMDLineParserException c) {
			log.error(c);
			throw c;
		} catch (Exception e) {
			log.error(e);
			throw new CMDLineParserException(e);
		}
	}

}
