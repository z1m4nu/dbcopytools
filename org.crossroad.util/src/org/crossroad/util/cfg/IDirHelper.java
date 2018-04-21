package org.crossroad.util.cfg;

public interface IDirHelper {

	/**
	 * 
	 * @return
	 */
	String getTempDir();

	/**
	 * 
	 * @param file
	 * @return
	 */
	String getTempPath(String file);

	/**
	 * 
	 * @return
	 */
	String getConfDir();

	/**
	 * 
	 * @param file
	 * @return
	 */
	String getConfPath(String file);

	/**
	 * 
	 * @return
	 */
	String getLogDir();
	/**
	 * 
	 * @return
	 */
	String getLibDir();
	
	/**
	 * 
	 * @return
	 */
	String getLibPath(String file);
	/**
	 * 
	 * @param file
	 * @return
	 */
	String getLogPath(String file);

	/**
	 * 
	 * @return
	 */
	String getHomeDir();

	/**
	 * 
	 * @return
	 */
	String getErrorFile();

	/**
	 * 
	 * @return
	 */
	String getLogOutFile();
	
	/**
	 * 
	 * @return
	 */
	String getOutfDir();

	/**
	 * 
	 * @param file
	 * @return
	 */
	String getOutPath(String file);

	/**
	 * 
	 * @return
	 */
	String getBinfDir();

	/**
	 * 
	 * @param file
	 * @return
	 */
	String getBinPath(String file);


}