package org.crossroad.util.cfg;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DirHelper implements IDirHelper {

	private static final String BIN_DIRNAME = "bin";
	private static final String CONF_DIRNAME = "conf";
	private static final String LIB_DIRNAME = "lib";
	private static final String LOG_DIRNAME = "log";
	private static final String OUT_DIRNAME = "out";
	private static final String TMP_DIRNAME = "tmp";

	private static final DirHelper instance = new DirHelper();
	private Date executionDate = new Date();
	private String homeDir = null;

	private DirHelper() {
	}

	protected static void putHomeDir(String homeDir) {
		instance.setHomeDir(homeDir);
	}

	public static IDirHelper getInstance() {

		return instance;
	}

	protected void setHomeDir(String homeDir) {
		this.homeDir = homeDir;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.hana.tools.cfg.IDirHelper#getTempDir()
	 */
	@Override
	public String getTempDir() {
		return this.getHomeDir() + File.separatorChar + TMP_DIRNAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.crossroad.hana.tools.cfg.IDirHelper#getTempPath(java.lang.String)
	 */
	@Override
	public String getTempPath(String file) {
		return getTempDir() + File.separatorChar + file;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.hana.tools.cfg.IDirHelper#getConfDir()
	 */
	@Override
	public String getConfDir() {
		return this.getHomeDir() + File.separatorChar + CONF_DIRNAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.crossroad.hana.tools.cfg.IDirHelper#getConfPath(java.lang.String)
	 */
	@Override
	public String getConfPath(String file) {
		return getConfDir() + File.separatorChar + file;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.hana.tools.cfg.IDirHelper#getLogDir()
	 */
	@Override
	public String getLogDir() {
		return this.getHomeDir() + File.separatorChar + LOG_DIRNAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.hana.tools.cfg.IDirHelper#getLogPath(java.lang.String)
	 */
	@Override
	public String getLogPath(String file) {
		return this.getLogDir() + File.separatorChar + file;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.hana.tools.cfg.IDirHelper#getHomeDir()
	 */
	@Override
	public String getHomeDir() {
		return this.homeDir;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.hana.tools.cfg.IDirHelper#getErrorFile()
	 */
	@Override
	public String getErrorFile() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String logFile = fmt.format(executionDate) + "-error.log";
		return this.getLogPath(logFile);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.crossroad.hana.tools.cfg.IDirHelper#getLogOutFile()
	 */
	@Override
	public String getLogOutFile() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd-HHmmss");
		String logFile = fmt.format(executionDate) + "-out.log";
		return this.getLogPath(logFile);
	}

	@Override
	public String getLibDir() {
		return this.getHomeDir() + File.separatorChar + LIB_DIRNAME;
	}

	@Override
	public String getLibPath(String file) {
		return getLibDir() + File.separatorChar + file;
	}

	@Override
	public String getOutfDir() {
		return this.getHomeDir() + File.separatorChar + OUT_DIRNAME;
	}

	@Override
	public String getOutPath(String file) {
		return getOutfDir() + File.separatorChar + file;
	}

	@Override
	public String getBinfDir() {
		return this.getHomeDir() + File.separatorChar + BIN_DIRNAME;
	}

	@Override
	public String getBinPath(String file) {
		return getBinfDir() + File.separatorChar + file;
	}
}
